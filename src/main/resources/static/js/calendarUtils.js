var url = "/event/";
var calendar;

function saveEvent() {
    $.ajax({
        type: "POST",
        url: url,
        data: {
            clientId: $("#clients").val(),
            employeeId: $("#employeeId").val(),
            title: $("#title").val(),
            start: $("#start").val().replace(" ", "T"),
            end: $("#end").val().replace(" ", "T")
        },
        success: function () {
            clearFormFields();
        }
    });
}

function eventPay() {
    $.ajax({
        type: "POST",
        url: url + "/pay/" + $("#eventId").val(),
        data: {
            sum: $("#sum").val()
        },
        success: function () {
            calendar.fullCalendar('refetchEvents');
        }
    });
    $(".ui.modal").modal("hide");
}

function deleteEvent() {
    var eventId = $("#eventId").val();
    $.ajax({
        type: "DELETE",
        url: url + eventId,
        success: function () {
            calendar.fullCalendar('removeEvents', eventId);
            $(".ui.modal").modal("hide");
        }
    });
}

function clearFormFields() {
    $("#title").val("");
    $("#start").val("");
    $("#end").val("");
}

function updateEvent(event) {
    $.ajax({
        type: "POST",
        url: url,
        data: {
            id: event.id,
            title: event.title,
            start: event.start.format(),
            end: event.end.format()
        }
    });
}

function getEvents(id) {
    $("#employeeId").val(id);
    calendar.fullCalendar('removeEventSources');
    calendar.fullCalendar('addEventSource', '/event/all/' + id);
}
