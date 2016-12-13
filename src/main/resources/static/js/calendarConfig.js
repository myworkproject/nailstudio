$(document).ready(function () {
    $("#start").datepicker({
        timepicker: true,
        minDate: new Date(),
        minHours: 9,
        dateFormat: 'yyyy-mm-dd',
    });
    $("#end").datepicker({
        timepicker: true,
        minDate: new Date(),
        minHours: 9,
        dateFormat: 'yyyy-mm-dd',
    });

    calendar = $('#calendar').fullCalendar({
        events: "/event/all",
        header: {
            left: 'agendaWeek,month',
            center: 'title',
            right: 'today,prev,next'
        },
        buttonText: {
            today: 'Сегодня',
            month: 'Месяц',
            week: 'Неделя',
            day: 'День',
            list: 'Список'
        },
        allDaySlot: false,
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль',
            'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['Янв.', 'Фев.', 'Мрт', 'Апр.', 'Май', 'Июн.',
            'Июл.', 'Авг.', 'Сен.', 'Окт.', 'Нбр', 'Дек.'],
        dayNames: ['Воскресенье', 'Понедельник', 'Вторник', 'Среда', 'Четверг',
            'Пятница', 'Суббота'],
        dayNamesShort: ['Вс.', 'Пн.', 'Вт.', 'Ср.', 'Чт.', 'Пт.', 'Сб.'],
        firstDay: 1,
        weekNumbers: true,
        weekNumberTitle: 'Н',
        timeFormat: 'H(:mm)',
        slotLabelFormat: 'H(:mm)',
        minTime: "9:00:00",
        height: 600,
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
            $(".ui.modal").modal({
                blurring: true
            }).modal("show");
        },
        eventRender: function( event, element, view ) {
        }
    });

    $.ajax({
        url: '/client/all',
        success: function (data) {
            $.each(data, function (i, client) {
                $("#clients").append('<option value="' + client.id + '">' + client.name + '</option>');
            });
        }
    });
});