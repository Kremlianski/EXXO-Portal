AUI({lang: 'ru'}).ready('aui-node', 'aui-datepicker', 'datatype-date-format', 'aui-io', function (A) {
    var now;
    var startStr = A.one("#date input").get("value");
    var arStr = startStr.split("-");
    var startY = arStr[0];
    var startM = arStr[1] - 1;
    var startD = arStr[2];
    if (!startStr)
        now = new Date();
    else
        now = new Date(startY, startM, startD);
    var DateSelected = now;

    A.DataType.Date.Locale['ru'] = A.merge(A.DataType.Date.Locale, {"a": ["вс", "пн", "вт", "ср", "чт", "пт", "сб"], "A": ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"],
        "b": ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"],
        "B": ["январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"],
        "c": "%a, %d %b %Y %k:%M:%S %Z", "p": ["AM", "PM"], "P": ["am", "pm"], "x": "%d.%m.%y", "X": "%k:%M:%S"});

    var Year = now.getFullYear();
    var Month = now.getMonth();
    var Day = now.getDate();
    var StrDate = Year + "-" + (Month + 1) + "-" + Day;
    A.one("#dateSelected").text(A.DataType.Date.format(now, {format: '%A,  %e %b %Y', locale: 'ru'}));

    var datepicker = new A.DatePickerSelect({
        locale: 'ru',
        appendOrder: ['d', 'm', 'y'],
        calendar: {dates: now, selectedDates: now,
            dateFormat: '%y-%m-%d',
            setValue: false},
        yearRange: [Year - 4, Year + 4],
        on: {
            'calendar:selectionChange': function (event) {
                var newdate = event.newSelection[0];
                if (newdate) {
                    var Year = newdate.getFullYear();
                    var Month = newdate.getMonth();
                    var Day = newdate.getDate();
                }
                StrDate = Year + "-" + (Month + 1) + "-" + Day;
                DateSelected = new Date(Year, Month, Day);
                A.one("#date input").set("value", StrDate);
                A.one("#dateSelected").text(A.DataType.Date.format(DateSelected, {format: '%A,  %e %b %Y', locale: 'ru'}));
                getTime();
                getInfo();
            }}}).render('#startMain');

    datepicker.after('render', function () {
        A.one("#date input").set("value", StrDate);
    });



    getTime();
    var timer;
    var timer1;
    function getTime() {
        timer = ['8:00', '8:15', '8:30', '8:45', '9:00', '9:15', '9:30', '9:45', '10:00', '10:15', '10:30', '10:45', '11:00',
            '11:15', '11:30', '11:45', '12:00', '12:15', '12:30', '12:45', '13:00', '13:15', '13:30', '13:45', '14:00', '14:15', '14:30', '14:45',
            '15:00', '15:15', '15:30', '15:45', '16:00', '16:15', '16:30', '16:45', '17:00', '17:15', '17:30', '17:45', '18:00', '18:15', '18:30', '18:45',
            '19:00', '19:15', '19:30', '19:45', '20:00', '20:15', '20:30', '20:45', '21:00', '21:15', '21:30', '21:45',
            '22:00', '22:15', '22:30', '22:45', '23:00', '23:15', '23:30', '23:45'];
        timer1 = ['8:00', '8:15', '8:30', '8:45', '9:00', '9:15', '9:30', '9:45', '10:00', '10:15', '10:30', '10:45', '11:00',
            '11:15', '11:30', '11:45', '12:00', '12:15', '12:30', '12:45', '13:00', '13:15', '13:30', '13:45', '14:00', '14:15', '14:30', '14:45',
            '15:00', '15:15', '15:30', '15:45', '16:00', '16:15', '16:30', '16:45', '17:00', '17:15', '17:30', '17:45', '18:00', '18:15', '18:30', '18:45',
            '19:00', '19:15', '19:30', '19:45', '20:00', '20:15', '20:30', '20:45', '21:00', '21:15', '21:30', '21:45',
            '22:00', '22:15', '22:30', '22:45', '23:00', '23:15', '23:30', '23:45', '24:00'];
        var minus;


        minus = "";
        var ev_id = A.one("#ev_id input").get("value");
        if (ev_id == null)
            ev_id = "";
        A.io.request('eventsArr', {
            method: 'post',
            dataType: 'text',
            data: {date: StrDate, id: ev_id},
            on: {success: function (event, id, xhr) {
                    var str = this.get('responseData');
                    if (str != null) {
                        minus = str.substring(0, str.length - 1).split(' ');
                        for (var i = 0; i < minus.length; i++) {
                            timer[minus[i]] = "";
                        }
                    }
                    var op = "";
                    for (var i = 0; i < timer.length; i++) {
                        if (timer[i] != "") {
                            op += "<option value='" + i + "'>" + timer[i] + "</option>";

                        }
                    }
                    A.one("#time").empty().append(op);
                    var times = A.one("#ar input").get("value");
                    if (times != null && times != "") {
                        A.one("#time option").removeAttribute('selected');
                        if (A.one("#time [value=" + times + "]") != null)
                            A.one("#time [value=" + times + "]").setAttribute('selected', 'true');
                        A.one("#time").set("value", times);

                    }
                    getLong();
                }}
        });
    }
    A.one("#time").on("change", function () {
        getLong();
    });
    A.one("#long").on("change", function () {
        var l = this.get("value");
        A.one("#lon input").set("value", l);
    })
    function getLong() {
        var t = A.one("#time").get('value');
        var ii = 1;
        var op1 = "";
        var s = t / 1 + 1;
        for (var i = s; i <= timer.length; i++) {
            op1 += "<option value='" + ii * 15 + "'>" + timer1[i] + "</option>";
            ii++;
            if (i != timer.length && timer[i] == "") {
                break;
            }
        }
        A.one("#long").empty().append(op1);
        A.one("#ar input").set("value", t);
        var lon = A.one("#lon input").get("value");
        if (lon != null && lon != "") {
            A.one("#long option").removeAttribute('selected');
            if (A.one("#long [value=" + lon + "]"))
                A.one("#long [value=" + lon + "]").setAttribute('selected', 'true');
            A.one("#long").set("value", lon);
        }
        var l = A.one("#long").get("value");
        A.one("#lon input").set("value", l);
    }
    A.all('button').each(function () {
        this.setAttribute('type', 'button')
    });
    function getInfo() {
        var ev_id = A.one("#ev_id input").get("value");
        if (ev_id == null)
            ev_id = "";
        A.one("#right").unplug(A.Plugin.IO);
        A.one("#right").plug(A.Plugin.IO, {uri: 'eventsInfo',
            method: 'POST',
            data: {date: StrDate, id: ev_id},
            on: {success: function () {
                    A.one('#content').setStyle('minHeight', A.DOM.winHeight() - 50 + 'px');
                }}});
    }
    getInfo();
});