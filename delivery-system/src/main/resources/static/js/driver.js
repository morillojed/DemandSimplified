
function addDriver() {
    var that = this;
    var id;
    var name = $('input[name="name"]').val();
    var number = $('input[name="number"]').val();
    var email = $('input[name="email"]').val();
    var address = $('input[name="address"]').val();
    var verification = $('input[name="verification"]').val();

    if (that.selectedDriver) {
        id = that.selectedDriver.id;
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

    if (that.selectedDriver) {
        customer = $.extend(customer, {id, id});
    }

    $.ajax({
        url: '/rest/drivers/add',
        data: JSON.stringify(customer),
        contentType: 'application/json',
        type: 'POST'
    }).done(function (data) {
        if (that.selectedDriver) {
            $('#editDriver').modal('hide');
            that.alertSuccess("Successfully updated Driver id: " + data.id);
        } else {
            $('#addNewDriver').modal('hide');
            that.alertSuccess("Successfully created Driver id: " + data.id);
        }
        that.resetFieldValues();
        $('#drivers-table').dataTable().fnDestroy();
        that.getDriverData();
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
    that.driversData = null;
    that.selectedDriver = null;
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

function showDriverData() {
    var that = this;
    var ids = $('select[id="select-driver"]').val();

    var driver = that.driversData.find(({id}) => parseInt(ids) === id);
    that.selectedDriver = driver;

    $('input[name="name"]').val(driver.name);
    $('input[name="number"]').val(driver.number);
    $('input[name="address"]').val(driver.address);
    $('input[name="email"]').val(driver.email);
    $('input[name="verification"]').val(driver.verification);

}

function getDriverData() {
    var that = this;
    $.ajax({
        url: '/rest/drivers/all',
        contentType: 'application/json',
        type: 'GET'
    }).done( function(data) {
        that.driversData = data;
        $('#drivers-table').dataTable( {
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
    var driversData;
    var suppliersMap;
    var selectedDriver;
    getDriverData();

    $('select[id="select-driver"]').selectpicker();


} );