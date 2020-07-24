
function addSupplier() {
    var that = this;
    var id;
    var name = $('input[name="name"]').val();
    var address = $('input[name="address"]').val();
    var details = $('input[name="details"]').val();

    if (that.selectedSupplier) {
        id = that.selectedSupplier.id;
        name = $('input[id="edit-name"]').val();
        address = $('input[id="edit-address"]').val();
        details = $('input[id="edit-details"]').val();
    }

    var supplier = {
        name: name,
        address: address,
        details: details
    };

    if (that.selectedSupplier) {
        supplier = $.extend(supplier, {id, id});
    }

    $.ajax({
        url: '/rest/suppliers/add',
        data: JSON.stringify(supplier),
        contentType: 'application/json',
        type: 'POST'
    }).done(function (data) {
        if (that.selectedSupplier) {
            $('#editSupplier').modal('hide');
            that.alertSuccess("Successfully updated Supplier id: " + data.id);
        } else {
            $('#addNewSupplier').modal('hide');
            that.alertSuccess("Successfully created Supplier id: " + data.id);
        }
        that.resetFieldValues();
        $('#suppliers-table').dataTable().fnDestroy();
        that.getSupplierData();
    }).fail(function () {
        alert("fail")
    });
}

function resetFieldValues() {
    var that = this;
    $('input[name="name"]').val("");
    $('input[name="address"]').val("");
    $('input[name="details"]').val("");
    that.suppliersData = null;
    that.selectedSupplier = null;
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

function showSupplierData() {
    var that = this;
    var ids = $('select[id="select-supplier"]').val();

    var supplier = that.suppliersData.find(({id}) => parseInt(ids) === id);
    that.selectedSupplier = supplier;

    $('input[name="name"]').val(supplier.name);
    $('input[name="address"]').val(supplier.address);
    $('input[name="details"]').val(supplier.details);

}

function getSupplierData() {
    var that = this;
    $.ajax({
        url: '/rest/suppliers/all',
        contentType: 'application/json',
        type: 'GET'
    }).done( function(data) {
        that.suppliersData = data;
        $('#suppliers-table').dataTable( {
            "aaData": data,
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
            "columns": [
                { "data": "id"},
                { "data": "name" },
                { "data": "details" },
                { "data": "address" }

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
    var suppliersData;
    var selectedSupplier;
    getSupplierData();

    $('select[id="select-supplier"]').selectpicker();


} );