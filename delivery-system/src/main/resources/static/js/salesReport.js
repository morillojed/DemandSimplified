function getTotal() {
    var dateFrom = $('#select-dateFrom').val();
    var dateTo = $('#select-dateTo').val();
    var supplier = $('#select-supplier').val();

    var dateChoose = {
        dateFrom: dateFrom,
        dateTo: dateTo,
        supplier: supplier
    };

    $.ajax({
        url: '/rest/sales/getTotal',
        contentType: 'application/json',
        data: dateChoose,
        type: 'GET'
    }).done(function (data){
        $('#totalPayable').val(data);
    });
}

function searchSalesReport() {
    var that = this;
    var dateFrom = $('#select-dateFrom').val();
    var dateTo = $('#select-dateTo').val();
    var supplier = $('#select-supplier').val();

    var dateChoose = {
        dateFrom: dateFrom,
        dateTo: dateTo,
        supplier: supplier
    };

    $.ajax({
        url: '/rest/sales/getSales',
        contentType: 'application/json',
        data: dateChoose,
        type: 'GET'
    }).done(function (data){
        $('#getSalesReport').modal('hide');
        $('#sales-report-table').dataTable( {
            "aaData": data,
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
            "columns": [
                { "data": "0"},
                { "data": "1" },
                { "data": "2" },
                { "data": "3" },
                { "data": "4" },
                { "data": "5" },
                { "data": "6" },
                { "data": "7" },

            ]
        });

        that.getTotal();
    }).fail( function() {
        alert("fail");
    });
}

$(document).ready(function() {
    $('#select-dateFrom').datepicker({
        format: 'dd-M-yyyy'
    });
    $('#select-dateTo').datepicker({
        format: 'dd-M-yyyy'
    });
} );