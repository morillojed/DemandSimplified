function addItems() {
    var that = this;

    var id = null;
    var supplierId = $('select[id="select-supplier"]').val();
    var name = $('input[name="name"]').val();
    var details = $('input[name="details"]').val();
    var cost = $('input[name="cost"]').val();
    var srp = $('input[name="srp"]').val();

    if (that.selectedItem) {
        id = that.selectedItem.id;
        supplierId = parseInt(that.selectedItem.supplierId);
        name = $('input[id="edit-name"]').val();
        details = $('input[id="edit-details"]').val();
        cost = $('input[id="edit-cost"]').val();
        srp = $('input[id="edit-srp"]').val();
    }

    var item = {
        supplierId: supplierId,
        name: name,
        details: details,
        cost: cost,
        srp: srp
    };

    if (id) {
        item = $.extend(item, {id, id});
    }

    $.ajax({
        url: '/rest/items/add',
        data: JSON.stringify(item),
        contentType: 'application/json',
        type: 'POST'
    }).done(function (data) {
        if (that.selectedItem) {
            $('#editItem').modal('hide');
            that.alertSuccess("Successfully updated Item id: " + data.id);
        } else {
            $('#addNewItem').modal('hide');
            that.alertSuccess("Successfully created Item id: " + data.id);
        }
        that.resetFieldValues();
        $('#items-table').dataTable().fnDestroy();
        that.getItemData();
    }).fail(function () {
        alert("fail");
    });
}

function resetFieldValues() {
    var that = this;
    $('select[id="select-supplier"]').val("");
    $('select[id="select-item"]').val("");
    $('input[name="name"]').val("");
    $('input[name="details"]').val("");
    $('input[name="cost"]').val("");
    $('input[name="srp"]').val("");
    that.itemData = null;
    that.selectedItem = null;
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

function showItemData() {
    var that = this;
    var ids = $('select[id="select-item"]').val();

    var item = that.itemData.find(({id}) => parseInt(ids) === id);
    var suppliersId;
    for (const [key, value] of Object.entries(that.suppliersMap)) {
        if (value === item.supplier) {
            suppliersId = key;
        }
    }
    item = $.extend(item, {supplierId : suppliersId});
    that.selectedItem = item;

    $('input[name="name"]').val(item.name);
    $('input[name="details"]').val(item.details);
    $('input[name="cost"]').val(item.cost);
    $('input[name="srp"]').val(item.srp);

}

function getItemData() {
    var that = this;
    $.ajax({
        url: '/rest/items/data',
        contentType: 'application/json',
        type: 'GET'
    }).done( function(data) {
        that.itemData = data;
        $('#items-table').dataTable( {
            "aaData": data,
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
            "columns": [
                { "data": "id"},
                { "data": "supplier" },
                { "data": "name" },
                { "data": "details" },
                { "data": "cost" },
                { "data": "srp" }

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

function getSuppliersMap() {
    var that = this;
    $.ajax({
        url: '/rest/suppliers/suppliersMap',
        contentType: 'application/json',
        type: 'GET'
    }).done( function(data) {
        that.suppliersMap = data;
    }).fail(function () {
        alert("fail");
    });
}

$(document).ready(function() {
    var itemData;
    var suppliersMap;
    var selectedItem;
    getItemData();
    getSuppliersMap();

    $('select[id="select-item"]').selectpicker();
} );