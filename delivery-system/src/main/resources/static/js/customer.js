
function addCustomer() {
    var that = this;
    var id;
    var name = $('input[name="name"]').val();
    var number = $('input[name="number"]').val();
    var email = $('input[name="email"]').val();
    var address = $('input[name="address"]').val();
    var verification = $('input[name="verification"]').val();

    if (that.selectedCustomer) {
        id = that.selectedCustomer.id;
        name = $('input[id="edit-name"]').val();
        number = $('input[id="edit-number"]').val();
        email = $('input[id="edit-email"]').val();
        address = $('input[id="edit-address"]').val();
        verification = $('input[id="edit-verification"]').val();
    }

    var customer = {
        name: name,
        number: number,
        email: email,
        address: address,
        verification: verification
    };

    if (that.selectedCustomer) {
        customer = $.extend(customer, {id, id});
    }

    $.ajax({
        url: '/rest/customers/add',
        data: JSON.stringify(customer),
        contentType: 'application/json',
        type: 'POST'
    }).done(function (data) {
        if (that.selectedCustomer) {
            $('#editCustomer').modal('hide');
            that.alertSuccess("Successfully updated Customer id: " + data.id);
        } else {
            $('#addNewCustomer').modal('hide');
            that.alertSuccess("Successfully created Customer id: " + data.id);
        }
        that.resetFieldValues();
        $('#customers-table').dataTable().fnDestroy();
        that.getCusData();
    }).fail(function () {
        alert("fail")
    });
}

function resetFieldValues() {
    var that = this;
    $('input[name="name"]').val("");
    $('input[name="number"]').val("");
    $('input[name="email"]').val("");
    $('input[name="address"]').val("");
    $('input[name="verification"]').val("");
    that.cusData = null;
    that.selectedCustomer = null;
}

function alertSuccess(transaction) {
    toastr["success"](transaction);

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

function showCusData() {
    var that = this;
    var ids = $('select[id="select-customer"]').val();

    var customer = that.cusData.find(({id}) => parseInt(ids) === id);
    that.selectedCustomer = customer;

    $('input[name="name"]').val(customer.name);
    $('input[name="number"]').val(customer.number);
    $('input[name="address"]').val(customer.address);
    $('input[name="email"]').val(customer.email);
    $('input[name="verification"]').val(customer.verification);

}

function getCusData() {
    var that = this;
    $.ajax({
        url: '/rest/customers/all',
        contentType: 'application/json',
        type: 'GET'
    }).done( function(data) {
        that.cusData = data;
        $('#customers-table').dataTable( {
            "aaData": data,
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
            "columns": [
                { "data": "id"},
                { "data": "name" },
                { "data": "number" },
                { "data": "address" },
                { "data": "email" },
                { "data": "verification" }

            ],
            "buttons": [
                "edit"
            ],
            "columnDefs": [
                {
                    "searchable": true,
                    "visible": true,
                    "targets": [ 0 ]
                },
            ]
        });
    }).fail( function() {
        alert("fail");
    });
}

$(document).ready(function() {
    var cusData;
    var suppliersMap;
    var selectedCustomer;
    getCusData();

    $('select[id="select-customer"]').selectpicker();


} );