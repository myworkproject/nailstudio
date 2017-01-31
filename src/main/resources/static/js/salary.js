url = "/salary/all/";
var text = "Зарплата за ";

function updateTable(number) {
    $.get(url + number, function (data) {
        updateTableByData(data);
    });
}

function getSalaryForMonth(number, ref) {
    var head = $("#text");
    head.text("");
    head.append(text + ref.textContent);
    updateTable(number)
}

$(document).ready(function () {
    datatableApi = $("#dataTable").DataTable({
        "paging": false,
        "info": false,
        "columns": [
            {"data": "name"},
            {"data": "total"},
            {"data": "salary"}
        ]
    });
});