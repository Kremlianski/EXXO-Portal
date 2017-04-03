AUI({lang: 'ru'}).ready('aui-node', 'aui-toolbar', 'aui-datepicker', 'datatype-date-format', function (A) {
    A.all(".answerDiv").each(function () {
        setTools(this);
    });
    A.one('#answersMain').setStyle('display', 'block');
    new A.Toolbar({
        activeState: true,
        children: [
            {icon: 'plus', label: 'Добавить вариант ответа', handler: {fn: function (e) {
                        var D = new Date();
                        var num = D.getTime();
                        A.one('#answersMain').append('<div id="item' + num + '" class="answerDiv"><span class="itemB"></span><span class="itemText">' + window.prompt('Введите вариант ответа', '') +
                                '</span></div>');
                        var node = A.one('#item' + num);
                        setTools(node);
                        A.all('button').each(function () {
                            this.setAttribute('type', 'button')
                        });
                        reg();
                    }}}
        ]}).render('#answersTop');
    A.all('button').each(function () {
        this.setAttribute('type', 'button')
    });
    function reg() {
        var str = '<vote>';
        A.all('.answerDiv').each(function () {
            var id = this.getAttribute('id');
            var text = this.one('.itemText').text();
            str += '<item>';
            str += '<id>' + id + '</id>';
            str += '<text>' + text + '</text>';
            str += '</item>';
        });
        str += '</vote>';
        A.one('#answers input').set('value', str);
    }
    var startStr = A.one("#startMain input").get("value");
    var ar0 = startStr.split("-");
    var startY = ar0[0];
    var startM = ar0[1] - 1;
    var startD = ar0[2];
    var stopStr = A.one("#stopMain input").get("value");
    var ar1 = stopStr.split("-");
    var stopY = ar1[0]
    var stopM = ar1[1] - 1;
    var stopD = ar1[2];
    var now;
    if (!startStr)
        now = new Date();
    else
        now = new Date(startY, startM, startD);
    var stopDate;
    if (!stopStr) {
        stopDate = new Date();
        stopDate.setDate(stopDate.getDate() + 14);
    } else
        stopDate = new Date(stopY, stopM, stopD);

    A.DataType.Date.Locale['ru'] = A.merge(A.DataType.Date.Locale, {"a": ["вс", "пн", "вт", "ср", "чт", "пт", "сб"], "A": ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"],
        "b": ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"],
        "B": ["январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"],
        "c": "%a, %d %b %Y %k:%M:%S %Z", "p": ["AM", "PM"], "P": ["am", "pm"], "x": "%d.%m.%y", "X": "%k:%M:%S"});

    var Year = now.getFullYear();
    var Month = now.getMonth();
    var Day = now.getDate();
    var StrDate = Year + "-" + (Month + 1) + "-" + Day;
    A.one("#startMain input").set("value", StrDate);

    var Year1 = stopDate.getFullYear();
    var Month1 = stopDate.getMonth();
    var Day1 = stopDate.getDate();
    var StrDate1 = Year1 + "-" + (Month1 + 1) + "-" + Day1;
    A.one("#stopMain input").set("value", StrDate1);

    A.one("#dateSelected").text(A.DataType.Date.format(now, {format: '%A,  %e %b %Y', locale: 'ru'}));
    A.one("#dateSelected1").text(A.DataType.Date.format(stopDate, {format: '%A,  %e %b %Y', locale: 'ru'}));
    new A.DatePickerSelect({
        locale: 'ru',
        appendOrder: ['d', 'm', 'y'],
        calendar: {dates: now, selectedDates: now,
            dateFormat: '%y-%m-%d',
            setValue: false},
        yearRange: [Year - 4, Year + 4],
        on: {'calendar:selectionChange': function (event) {
                var Year;
                var Month;
                var Day;
                var newdate = event.newSelection[0];
                if (newdate) {
                    Year = newdate.getFullYear();
                    Month = newdate.getMonth();
                    Day = newdate.getDate();
                }
                StrDate = Year + "-" + (Month + 1) + "-" + Day;
                var DateSelected = new Date(Year, Month, Day);
                A.one("#startMain input").set("value", StrDate);
                A.one("#dateSelected").text(A.DataType.Date.format(DateSelected, {format: '%A,  %e %b %Y', locale: 'ru'}));
            }}}).render('#startMain');
    new A.DatePickerSelect({
        locale: 'ru',
        appendOrder: ['d', 'm', 'y'],
        calendar: {dates: stopDate, selectedDates: stopDate,
            dateFormat: '%y-%m-%d',
            setValue: false},
        yearRange: [Year - 4, Year + 4],
        on: {'calendar:selectionChange': function (event) {
                var Year;
                var Month;
                var Day;
                var newdate = event.newSelection[0];
                if (newdate) {
                    Year = newdate.getFullYear();
                    Month = newdate.getMonth();
                    Day = newdate.getDate();
                }
                StrDate = Year + "-" + (Month + 1) + "-" + Day;
                var DateSelected = new Date(Year, Month, Day);
                A.one("#stopMain input").set("value", StrDate);
                A.one("#dateSelected1").text(A.DataType.Date.format(DateSelected, {format: '%A,  %e %b %Y', locale: 'ru'}));
            }}}).render('#stopMain');



    A.all('button').each(function () {
        this.setAttribute('type', 'button')
    });
    function setTools(node) {
        new A.Toolbar({
            children: [
                {icon: 'pencil', handler: {fn: function () {
                            node.one('.itemText').text(window.prompt('Исправьте текст', node.one('.itemText').text()));
                            reg();
                        }}}, {icon: 'close', handler: {fn: function () {
                            node.remove();
                            reg();
                        }}}]}).render(node.one('.itemB'));
    }
}
);