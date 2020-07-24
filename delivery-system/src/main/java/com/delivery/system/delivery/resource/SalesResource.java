package com.delivery.system.delivery.resource;

import com.delivery.system.delivery.model.*;
import com.delivery.system.delivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/sales")
public class SalesResource
{

  @Autowired
  SalesRepository salesRepository;
  @Autowired
  DriverRepository driverRepository;
  @Autowired
  ItemRepository itemRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  ItemSalesRepository itemSalesRepository;
  @Autowired
  SalesReportRepository salesReportRepository;
  @Autowired
  EntityManager entityManager;

  private static final String DATE_TIME_FORMAT = "dd-M-yyyy hh:mm:ss a";
  private static final String DATE_FORMAT = "dd-MMM-yyyy";

  @GetMapping(value = "/all")
  public List<Sales> getAll() {
    return salesRepository.findAll();
  }

  @GetMapping(value = "/data")
  public List<SalesDTO> getData() {
    return salesRepository.findAll().stream().map(this::toSalesDTO).collect(Collectors.toList());
  }

  private SalesDTO toSalesDTO(final Sales sales) {
    Optional<Driver> driver = driverRepository.findById(sales.getDriverId());
    String driverName = "";
    if (driver.isPresent()) {
      driverName = driver.get().getName();
    }
    Optional<Customer> customer = customerRepository.findById(sales.getCustomerId());
    String customerName = "";
    if (customer.isPresent()) {
      customerName = customer.get().getName();
    }
    SalesDTO salesDTO = new SalesDTO(sales.getId(), customerName, driverName, sales.getDeliveryAddress(), sales.getTotalItemPrice(), sales.getDeliveryFee(), sales.getAcceptedByDriverDate(), sales.getReceivedDate(), sales.getCreatedDate(), sales.isDone());

    final List<ItemSales> itemSales = itemSalesRepository.findByTransactionId(salesDTO.getId());
    final List<ItemSalesDTO> itemSalesDTOList = itemSales.stream().map(this::toItemSalesDTO).collect(Collectors.toList());
    salesDTO.setSalesItem(itemSalesDTOList);
    salesDTO.setSalesItemHtml(salesItemToHtml(itemSalesDTOList));
    return salesDTO;
  }

  private ItemSalesDTO toItemSalesDTO(final ItemSales itemSales) {
    String itemName = itemRepository.findById(itemSales.getItemId()).get().getName();

    ItemSalesDTO itemSalesDTO = new ItemSalesDTO(itemSales.getId(), itemSales.getTransactionId(), itemName, itemSales.getQuantity());

    return itemSalesDTO;
  }

  private String salesItemToHtml(List<ItemSalesDTO> itemSalesDTOList) {
    String toHtml = "<ul>";
    for (ItemSalesDTO item : itemSalesDTOList) {
      toHtml += "<li><input value=" + item.getId() + " hidden/>" + item.getItemName() + " : " + item.getQuantity() + "</li>";
    }

    return toHtml;
  }

  @PostMapping(value = "/add")
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public Sales add(@RequestBody final Sales sales) {
    LocalDateTime ldt = LocalDateTime.parse(new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date()), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    ZoneId singaporeZoneId = ZoneId.of("Asia/Singapore");
    ZonedDateTime asiaZonedDateTime = ldt.atZone(singaporeZoneId);
    final Optional<Sales> optional = salesRepository.findById(sales.getId());
    Sales sale = optional.isPresent() ? optional.get() : null;

    if (sale == null) {
      sale = sales;
      sales.setCreatedDate(asiaZonedDateTime.toLocalDateTime());
    }

    if (driverRepository.findById(sales.getDriverId()).isPresent()) {
      sale.setDriverId(sales.getDriverId());
      sale.setAcceptedByDriverDate(asiaZonedDateTime.toLocalDateTime());
    }

    if (sales.isDone()) {
      sale.setDone(sales.isDone());
      sale.setReceivedDate(asiaZonedDateTime.toLocalDateTime());
    }

    salesRepository.save(sale);
    return sale;
  }

  @GetMapping("/getSales")
  public List<SalesReport> getSales(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String supplier) throws ParseException {
    String parsedDateFrom = new SimpleDateFormat(DATE_TIME_FORMAT).format(new SimpleDateFormat(DATE_FORMAT).parse(dateFrom));
    String parsedDateTo = new SimpleDateFormat(DATE_TIME_FORMAT).format(new SimpleDateFormat(DATE_FORMAT).parse(dateTo));
    LocalDateTime dF = LocalDateTime.parse(parsedDateFrom, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    LocalDateTime dT = LocalDateTime.parse(parsedDateTo, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    Query query = entityManager.createNativeQuery("SELECT s.id as sales_id, supp.name as supplier_name, item.name as item_name, i.quantity, item.cost, item.srp, CAST((i.quantity * CAST(item.cost as int)) as float) as total_cost, CAST((i.quantity * CAST(item.srp as int)) as float) as total_srp " +
      "FROM sales s JOIN item_sales i ON i.transaction_id = s.id JOIN item item ON item.id = i.item_id JOIN supplier supp ON supp.id = item.supplier_id " +
      "WHERE s.created_date > ?1 AND s.created_date <= ?2 AND supp.name = ?3");

    query.setParameter(1, dF);
    query.setParameter(2, dT);
    query.setParameter(3, supplier);

    return query.getResultList();
  }

  @GetMapping("/getTotal")
  public float getTotal(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String supplier) throws ParseException {
    String parsedDateFrom = new SimpleDateFormat(DATE_TIME_FORMAT).format(new SimpleDateFormat(DATE_FORMAT).parse(dateFrom));
    String parsedDateTo = new SimpleDateFormat(DATE_TIME_FORMAT).format(new SimpleDateFormat(DATE_FORMAT).parse(dateTo));
    LocalDateTime dF = LocalDateTime.parse(parsedDateFrom, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    LocalDateTime dT = LocalDateTime.parse(parsedDateTo, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

    Query query = entityManager.createNativeQuery("SELECT SUM(CAST((i.quantity * CAST(item.cost as int)) as float)) " +
      "FROM sales s JOIN item_sales i ON i.transaction_id = s.id JOIN item item ON item.id = i.item_id JOIN supplier supp ON supp.id = item.supplier_id " +
      "WHERE s.created_date > ?1 AND s.created_date <= ?2 AND supp.name = ?3");

    query.setParameter(1, dF);
    query.setParameter(2, dT);
    query.setParameter(3, supplier);

    return Float.parseFloat(query.getSingleResult().toString());
  }



  @PostMapping
  public String driverUpdate(@RequestParam final int salesId, final int driverId) {
    final Sales sales = salesRepository.findById(salesId).get();

    if (null == sales) {
      return "Sales not found";
    }

    final Driver driver = driverRepository.findById(driverId).get();

    if (null == driver) {
      return "Driver not found";
    }

    sales.setDriverId(driverId);
    salesRepository.save(sales);
    return "Driver " + driver.getName() + " accepted Sales " + sales.getId();
  }

  public class DateChoose {
    private String dateFrom;
    private String dateTo;

    public DateChoose() {
    }

    public String getDateFrom() {
      return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
      this.dateFrom = dateFrom;
    }

    public String getDateTo() {
      return dateTo;
    }

    public void setDateTo(String dateTo) {
      this.dateTo = dateTo;
    }
  }
}
