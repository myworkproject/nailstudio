$(document).ready(function () {
    calendar = $('#calendar').fullCalendar({
        navLinks: true,
        eventLimit: true,
        header: {
            left: 'agendaWeek,month',
            center: 'title',
            right: 'today,prev,next'
        },
        buttonText: {today: 'Сегодня', month: 'Месяц', week: 'Неделя', day: 'День', list: 'Список'},
        allDaySlot: false,
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['Янв.', 'Фев.', 'Мрт', 'Апр.', 'Май', 'Июн.', 'Июл.', 'Авг.', 'Сен.', 'Окт.', 'Нбр', 'Дек.'],
        dayNames: ['Воскресенье', 'Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота'],
        dayNamesShort: ['Вс.', 'Пн.', 'Вт.', 'Ср.', 'Чт.', 'Пт.', 'Сб.'],
        firstDay: 1,
        weekNumbers: true,
        weekNumberTitle: 'Н',
        timeFormat: 'H(:mm)',
        slotLabelFormat: 'H(:mm)',
        minTime: "9:00:00",
        height: 540,
        editable: true,
        eventDrop: function (event, delta, revertFunc) {
            updateEvent(event);
        },
        eventResize: function (event, delta, revertFunc) {
            updateEvent(event);
        },
        eventClick: function (calEvent, jsEvent, view) {
            $("#eventId").val(calEvent.id);
            var sum = calEvent.sum;
            $("#sum").val("");
            if (sum != 0) {
                $("#sum").val(sum);
            }
            $(".ui.modal").modal("show");
        },
        eventRender: function (event, element, view) {
            $.get("/client/" + event.clientId, function (data) {
                var elemText = element.text();
                var index = elemText.indexOf(" ");
                var s1 = elemText.substring(0, index);
                var s2 = elemText.substring(index);
                element.empty();
                element.append(s1 + " " + data.firstName + " " + s2);
            });

            if (event.src === "viber") {
                element.css({'background-color': '#8f5db7'})
            }
        }
    });
    $.ajax({
        url: '/client/all', success: function (data) {
            $.each(data, function (i, client) {
                $("#clients").append('<option value="' + client.id + '">' + client.firstName +
                    (client.lastName === null ? " " : " " + client.lastName) + '</option>');
            });
        }
    });
    $.ajax({
        url: '/employee/all', success: function (data) {
            $("#employees").append('<div class="divider"> / </div>');
            $.each(data, function (i, employee) {
                $("#employees").append('<a class="section" onclick="getEvents(' + employee.id + ')">' + employee.name + '</a>' + '<div class="divider"> / </div>');
            });
        }
    });
});