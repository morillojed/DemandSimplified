package com.delivery.system.delivery.controller;

import com.delivery.system.delivery.model.*;
import com.delivery.system.delivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomePageController {

  @Autowired
  DriverRepository driverRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  SupplierRepository supplierRepository;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  SalesRepository salesRepository;

  @Autowired
  ItemSalesRepository itemSalesRepository;

  @GetMapping(value = "/homePage")
  public String homePage(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Driver> drivers = driverRepository.findAll();
    final Map<Integer, String> driversMap = drivers.stream().map(Driver::getDriverEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Customer> customers = customerRepository.findAll();
    final Map<Integer, String> customersMap = customers.stream().map(Customer::getCustomerEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Supplier> suppliers = supplierRepository.findAll();
    final Map<Integer, String> suppliersMap = suppliers.stream().map(Supplier::getSupplierEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Item> items = itemRepository.findAll();
    final Map<Integer, String> itemsMap = items.stream().map(Item::getItemEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<ItemDTO> itemsDTO = items.stream().map(this::toItemDTO).collect(Collectors.toList());
    final List<Sales> salesList = salesRepository.findAll();
    final List<SalesDTO> salesDTOList = salesList.stream().map(this::toSalesDTO).collect(Collectors.toList());

    model.addAttribute("drivers", drivers);
    model.addAttribute("driversMap", driversMap);
    model.addAttribute("customers", customers);
    model.addAttribute("customersMap", customersMap);
    model.addAttribute("suppliers", suppliers);
    model.addAttribute("suppliersMap", suppliersMap);
    model.addAttribute("itemsDTO", itemsDTO);
    model.addAttribute("itemsMap", itemsMap);
    model.addAttribute("salesDTOList", salesDTOList);
    model.addAttribute("name", name);
    return "homePage";
  }

  @GetMapping(value = "/")
  public String home(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    return "index";
  }

  private ItemDTO toItemDTO(final Item item) {
    String supplierName = supplierRepository.findById(item.getSupplierId()).get().getName();
    ItemDTO itemDTO = new ItemDTO(item.getId(), supplierName, item.getName(), item.getDetails(), item.getCost(), item.getSrp(), item.getStock());

    return itemDTO;
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

  private String salesItemToHtml(List<ItemSalesDTO> itemSalesDTOList) {
    String toHtml = "<ul>";
    for (ItemSalesDTO item : itemSalesDTOList) {
      toHtml += "<li><input value=" + item.getId() + " hidden/>" + item.getItemName() + " : " + item.getQuantity() + "</li>";
    }

    return toHtml;
  }

  private ItemSalesDTO toItemSalesDTO(final ItemSales itemSales) {
    String itemName = itemRepository.findById(itemSales.getItemId()).get().getName();

    ItemSalesDTO itemSalesDTO = new ItemSalesDTO(itemSales.getId(), itemSales.getTransactionId(), itemName, itemSales.getQuantity());

    return itemSalesDTO;
  }

  @GetMapping(value = "/transaction")
  public String transaction(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Driver> drivers = driverRepository.findAll();
    final Map<Integer, String> driversMap = drivers.stream().map(Driver::getDriverEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Customer> customers = customerRepository.findAll();
    final Map<Integer, String> customersMap = customers.stream().map(Customer::getCustomerEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Item> items = itemRepository.findAll();
    final Map<Integer, String> itemsMap = items.stream().map(Item::getItemEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Sales> salesList = salesRepository.findAll();
    final List<SalesDTO> salesDTOList = salesList.stream().map(this::toSalesDTO).collect(Collectors.toList());
    final List<Integer> salesIdList = salesList.stream().map(Sales::getId).collect(Collectors.toList());
    final List<Supplier> suppliers = supplierRepository.findAll();
    final Map<Integer, String> suppliersMap = suppliers.stream().map(Supplier::getSupplierEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));

    model.addAttribute("driversMap", driversMap);
    model.addAttribute("customersMap", customersMap);
    model.addAttribute("itemsMap", itemsMap);
    model.addAttribute("suppliersMap", suppliersMap);
    model.addAttribute("salesDTOList", salesDTOList);
    model.addAttribute("salesIdList", salesIdList);
    return "salesHome";
  }

  @GetMapping(value = "/salesReport")
  public String salesReport(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Supplier> suppliers = supplierRepository.findAll();
    final Map<Integer, String> suppliersMap = suppliers.stream().map(Supplier::getSupplierEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));

    model.addAttribute("suppliersMap", suppliersMap);
    return "salesReport";
  }

  @GetMapping(value = "/item")
  public String item(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Supplier> suppliers = supplierRepository.findAll();
    final Map<Integer, String> suppliersMap = suppliers.stream().map(Supplier::getSupplierEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));
    final List<Item> items = itemRepository.findAll();
    final Map<Integer, String> itemsMap = items.stream().map(Item::getItemEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));

    model.addAttribute("itemsMap", itemsMap);
    model.addAttribute("suppliersMap", suppliersMap);
    return "item";
  }

  @GetMapping(value = "/customer")
  public String customer(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Customer> customers = customerRepository.findAll();
    final Map<Integer, String> customersMap = customers.stream().map(Customer::getCustomerEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));

    model.addAttribute("customersMap", customersMap);
    return "customer";
  }

  @GetMapping(value = "/driver")
  public String driver(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Driver> drivers = driverRepository.findAll();
    final Map<Integer, String> driversMap = drivers.stream().map(Driver::getDriverEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));

    model.addAttribute("driversMap", driversMap);
    return "driver";
  }

  @GetMapping(value = "/supplier")
  public String supplier(@RequestParam(name="name", required = false, defaultValue = "World") String name, Model model) {
    final List<Supplier> suppliers = supplierRepository.findAll();
    final Map<Integer, String> suppliersMap = suppliers.stream().map(Supplier::getSupplierEntry).collect(Collectors.toMap(p->p.getKey(), p->p.getValue()));

    model.addAttribute("suppliersMap", suppliersMap);
    return "supplier";
  }
}
