url = "/employee/";

$(document).ready(function () {
    datatableApi = $("#dataTable").DataTable({
        "paging": false,
        "info": false,
        "ajax": {
            "url": url + 'all',
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {"data": "name"},
            {"data": "phone"},
            {"data": "percent"},
            {"data": "salary"},
            {
                "defaultContent": "",
                "sortable": false,
                "render": renderBtn
            }
        ],
        "initComplete": function () {
            makeEditable();
        }
    });
});