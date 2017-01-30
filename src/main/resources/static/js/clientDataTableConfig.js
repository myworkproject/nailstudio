url = "/client/";

function updateTable() {
    $.get(url + "/all", function (data) {
        updateTableByData(data);
    });
}

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