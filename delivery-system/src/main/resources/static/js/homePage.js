function addDriver() {
    var that = this;
    var name = $('.new-driver').closest('tr').find('input[name="name"]').val();
    var number = $('.new-driver').closest('tr').find('input[name="number"]').val();
    var email = $('.new-driver').closest('tr').find('input[name="email"]').val();
    var verification = $('.new-driver').closest('tr').find('input[name="verification"]').val();

    var driver = {
        name: name,
        number: number,
        email: email,
        verification: verification
    };

    $.ajax({
        url: '/rest/drivers/add',
        data: JSON.stringify(driver),
        contentType: 'application/json',
        type: 'POST'
    }).done(function () {
        alert("done")
    }).fail(function () {
        alert("fail")
    });
}

function addCustomer() {
    var that = this;
    var name = $('.new-customer').closest('tr').find('input[name="name"]').val();
    var number = $('.new-customer').closest('tr').find('input[name="number"]').val();
    var email = $('.new-customer').closest('tr').find('input[name="email"]').val();
    var address = $('.new-customer').closest('tr').find('input[name="address"]').val();
    var verification = $('.new-customer').closest('tr').find('input[name="verification"]').val();

    var customer = {
        name: name,
        number: number,
        email: email,
        address: address,
        verification: verification
    };

    $.ajax({
        url: '/rest/customers/add',
        data: JSON.stringify(customer),
        contentType: 'application/json',
        type: 'POST'
    }).done(function () {
        alert("done")
    }).fail(function () {
        alert("fail")
    });
}

function addSupplier() {
    var that = this;
    var name = $('.new-supplier').closest('tr').find('input[name="name"]').val();
    var details = $('.new-supplier').closest('tr').find('input[name="details"]').val();
    var address = $('.new-supplier').closest('tr').find('input[name="address"]').val();

    var supplier = {
        name: name,
        details: details,
        address: address
    };

    $.ajax({
        url: '/rest/suppliers/add',
        data: JSON.stringify(supplier),
        contentType: 'application/json',
        type: 'POST'
    }).done(function () {
        alert("done")
    }).fail(function () {
        alert("fail")
    });
}

function addItem() {
    var that = this;
    var supplierId = $('.new-item').closest('tr').find('select[id="select-supplier"]').val();
    var name = $('.new-item').closest('tr').find('input[name="name"]').val();
    var details = $('.new-item').closest('tr').find('input[name="details"]').val();
    var price = $('.new-item').closest('tr').find('input[name="price"]').val();
    var stock = $('.new-item').closest('tr').find('input[name="stock"]').val();

    var item = {
        supplierId: supplierId,
        name: name,
        details: details,
        price: price,
        stock: stock
    };

    $.ajax({
        url: '/rest/items/add',
        data: JSON.stringify(item),
        contentType: 'application/json',
        type: 'POST'
    }).done(function () {
        alert("done")
    }).fail(function () {
        alert("fail")
    });
}

function addSales() {
    var that = this;

    var id;
    if (that.selectedSale) {
        id = that.selectedSalesId;
    }

    var customerId = $('select[id="select-customer"]').val();
    var itemId = $('select[id="select-item"]').val();
    var driverId = $('select[id="select-driver"]').val();
    var deliveryAddress = $('input[name="deliveryAddress"]').val();
    var totalItemPrice = $('input[name="totalItemPrice"]').val();
    var deliveryFee = $('input[name="deliveryFee"]').val();
    var quantity = $('input[name="quantity"]').val();
    var acceptedByDriverDate = $('input[name="acceptedByDriverDate"]').val();
    var receivedDate = $('input[name="receivedDate"]').val();
    var createdDate = $('input[name="createdDate"]').val();
    var done = $('input[name="done"]').val();

    var transaction;

    var sales = {
        customerId: customerId,
        driverId: driverId,
        deliveryAddress: deliveryAddress,
        totalItemPrice: totalItemPrice,
        deliveryFee: deliveryFee,
    };

    if (that.selectedSale) {
        if (that.markAsDone) {
            sales = {
                id: id,
                done: true
            }
        } else {
            sales = {
                id: id,
                driverId: $('select[id="select-choosen-driver"]').val()
            }
        }
    }

    $.ajax({
        url: '/rest/sales/add',
        data: JSON.stringify(sales),
        contentType: 'application/json',
        type: 'POST'
    }).done(function (data) {
        transaction = data.id;
        if (that.selectedSale) {
            if (that.markAsDone) {
                $('#markAsDone').modal('hide');
            } else {
                $('#assignDriver').modal('hide');
            }
            that.alertSuccess("Successfully updated Sales id: " + transaction);
        } else {

            var list = document.getElementById("itemSalesList");
            var items = list.getElementsByTagName("li");
            Array.from(items).forEach(function(item) {
                var i = $(item).find('input[name="item"]').val();
                var qty = $(item).find('input[name="qty"]').val();

                var items = {
                    transactionId: transaction,
                    itemId: i,
                    quantity: qty
                };

                $.ajax({
                    url: '/rest/itemsales/add',
                    data: JSON.stringify(items),
                    contentType: 'application/json',
                    type: 'POST'
                }).done(function() {
                }).fail(function() {
                    alert("fail");
                });
            });

            $('#addNewSales').modal('hide');
            that.alertSuccess("Successfully created Sales id: " + transaction);
        }

        that.resetFieldValues();
        $('#sales-table').dataTable().fnDestroy();
        that.getSalesData();
        that.selectedSale = null;
    }).fail(function () {
        alert("fail");
    });
}

function getTransaction() {
    var that = this;
    var ids = $('select[id="select-sales"]').val();
    that.markAsDone = false;

    if (!ids) {
        ids = $('select[id="select-done-sales"]').val();
        that.markAsDone = true;
    }

    that.selectedSalesId = ids;

    var sale = that.salesData.find(({id}) => parseInt(ids) === id);
    that.selectedSale = sale;
}

function alertSuccess(msg) {
    toastr["success"](msg);
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
}

function resetFieldValues() {
    $('select[id="select-sales"]').val("");
    $('select[id="select-done-sales"]').val("");
    $('select[id="select-choosen-driver"]').val("")
    $('select[id="select-customer"]').val("");
    $('select[id="select-driver"]').val("");
    $('input[name="deliveryAddress"]').val("");
    $('input[name="totalItemPrice"]').val("");
    $('input[name="deliveryFee"]').val("");
    $('#itemSalesList').empty()
}

function addItemSales() {
    var item = $('#select-item option:selected').text();
    var val = $('#select-item option:selected').val();
    var list = document.getElementById("itemSalesList");

    if (item !== "")
        $('<li class = "list-group-item" id = "list-item-'+val+'"><input type="text" name="item" value="'+val+'" hidden/>'+item+'<input type="text" name="qty" /><button type="button" class="remove-item" onclick="removeSalesItem('+val+')">x</button></li>').appendTo(list);

}

function removeSalesItem(val) {
    var id = "#list-item-" + val;
    $(id).remove();
}

function getSalesData() {
    var that = this;
    $.ajax({
        url: '/rest/sales/data',
        contentType: 'application/json',
        type: 'GET'
    }).done( function(data) {
        that.salesData = data;
        $('#sales-report-table').dataTable().fnDestroy();
        $('#sales-table').dataTable( {
            "aaData": data,
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
            "columns": [
                { "data": "id"},
                { "data": "customerName" },
                { "data": "salesItemHtml" },
                { "data": "driverName" },
                { "data": "deliveryAddress" },
                { "data": "totalItemPrice" },
                { "data": "deliveryFee" },
                { "data": "acceptedByDriverDate" },
                { "data": "receivedDate" },
                { "data": "createdDate" },
                { "data": "done" },

            ],
            "columnDefs": [
                {
                    "searchable": true,
                    "visible": true,
                    "targets": [ 0 ]
                },
            ],
            "drawCallback": function( settings ) {
                var h = (window.outerHeight - (window.outerHeight - $('#header').height())) *  ($('#sales-table').height()+settings._iDisplayLength) / 1000
                if (window.outerHeight > 700)
                    h = h-10

                // $('#hero').css("margin-top","calc(" + h +"vh)");
            }
        });
    }).fail( function() {
       alert("fail");
    });
}

function calculateTotalPrice() {
    var list = document.getElementById("itemSalesList");
    var items = list.getElementsByTagName("li");
    var itemsprocessed = 0;
    var total = 0;
    Array.from(items).forEach(function (item) {
        itemsprocessed++;
        var i = $(item).find('input[name="item"]').val();
        var qty = $(item).find('input[name="qty"]').val();

        var id = {
            id: i
        };


        $.ajax({
            url: '/rest/items/getItemSRP',
            data: id,
            contentType: 'application/json',
            type: 'GET'
        }).done(function (data) {
            total = total + (data * qty);

            if (itemsprocessed === items.length)
                $('#totalItemPrice').val(total);
        }).fail(function () {
            alert("fail");
        });
    })
}

$(document).ready(function() {
    var salesData;
    var selectedSale;
    var markAsDone = false;
    var selectedSalesId;
    getSalesData();
    $('#select-sales').selectpicker();
    $('#select-choosen-driver').selectpicker();
    $('#select-done-sales').selectpicker();
    $('#select-customer').selectpicker();
    $('#select-item').selectpicker();
    $('#select-driver').selectpicker();
    $('#select-dateFrom').datepicker({
        format: 'dd-M-yyyy'
    });
    $('#select-dateTo').datepicker({
        format: 'dd-M-yyyy'
    });
    $('#sales-report-table').style.visibility = "hidden";


} );