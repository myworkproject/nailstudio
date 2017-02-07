var url;
var form;
var datatableApi;
function renderBtn(data, type, row) {
    if (type === "display") {
        return '<div class="ui mini buttons">' + '<button class="ui button" onclick="updateRow(' + row.id + ');">Изменить</button>' + '<div class="or" data-text="или"></div>' + '<button class="ui button" onclick="deleteRow(' + row.id + ');">Удалить</button></div>';
    }
    return data;
}
function updateTableByData(data) {
    datatableApi.clear();
    $.each(data, function (key, item) {
        datatableApi.row.add(item).draw();
    });
}
function save() {
    $.ajax({
        type: "POST", url: url, data: form.serialize(), success: function () {
            $(".ui.modal").modal("hide");
            updateTable();
        }
    });
}
function deleteRow(id) {
    $.ajax({
        url: url + id, type: "DELETE", success: function () {
            updateTable();
            return false;
        }
    });
}
function updateRow(id) {
    $.get(url + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $(".ui.modal").modal("show");
        $("#admin").show();
    });
    datatableApi.draw();
}
function makeEditable() {
    form = $("#form");
    $("#add").click(function () {
        form.find(":input").val("");
        $("#id").val(0);
        $("#admin").hide();
        $(".ui.modal").modal("show");
    });
    form.submit(function () {
        save();
        return false;
    });
}