function renderEditBtn(data, type, row) {
    if (type === "display") {
        return '<a class="ui mini primary button" onclick="updateRow(' + row.id + ');">Edit</a>';
    }
    return data;
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return '<a class="ui mini red button" onclick="deleteRow(' + row.id + ');">Delete</a>';
    }
    return data;
}

function updateTableByData(data) {
    datatableApi.clear();
    $.each(data, function (key, item) {
        datatableApi.row.add(item).draw();
    });
}

function updateTable() {
    $.get(url+"/all", function (data) {
        updateTableByData(data);
    });
}

// function save() {
//     $.ajax({
//         type: "POST",
//         url: url,
//         data: form.serialize(),
//         success: function () {
//             $("#editModal").modal("hide");
//             updateTable();
//             successNoty("Saved");
//         }
//     });
// }

function deleteRow(id) {
    $.ajax({
        url: url + id,
        type: "DELETE",
        success: function () {
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
        $("#editModal").modal();
    });
    datatableApi.draw();
}
//
// function enable(chkbox, id) {
//     var enabled = chkbox.is(":checked");
//     chkbox.closest('tr').css("text-decoration", enabled ? "none" : "line-through");
//     $.ajax({
//         url: url + id,
//         type: "POST",
//         data: "enabled=" + enabled,
//         success: function () {
//             successNoty(enabled ? "Enabled" : "Disabled");
//         }
//     });
// }
//
// function makeEditable() {
//     form = $("#detailsForm");
//
//     $("#add").click(function () {
//         form.find(":input").val("");
//         $("#id").val(0);
//         $("#editModal").modal();
//     });
//
//     form.submit(function () {
//         save();
//         return false;
//     });
// }
