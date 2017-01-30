url = "/employee/";

function updateTable() {
    $.get(url + "/all?admin=true", function (data) {
        updateTableByData(data);
    });
}

$(document).ready(function () {
    datatableApi = $("#dataTable").DataTable({
        "paging": false,
        "info": false,
        "ajax": {
            "url": url + 'all?admin=true',
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