AUI({lang: 'ru'}).ready('aui-calendar', function(A) {
A.DataType.Date.Locale['ru'] = A.merge(A.DataType.Date.Locale, {
"a":["вс","пн","вт","ср","чт","пт","сб"],
"A":["Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"],
"b":["января","февраля","марта","апреля","мая","июня","июля","августа","сентября","октября","ноября","декабря"],
"B":["январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"],
"c":"%a, %d %b %Y %k:%M:%S %Z","p":["AM","PM"],
"P":["am","pm"],
"x":"%d.%m.%y",
"X":"%k:%M:%S"
    });
var c=new A.Calendar({
locale: 'ru',
visible: false,
hideOnDocumentClick: true,
firstDayOfWeek: 1,
on: {select: function(event) {
var Year= event.date.detailed[0].year;
var Month= event.date.detailed[0].month;
var Day= event.date.detailed[0].day;
this.hide();
location="calendarCompare.xhtml?date="+Year+"-"+(Month+1)+"-"+Day;
}},
after:{render:function(){
var trigger=A.one('#date');
trigger.on('click', function(){
c.set('visible',!c.get('visible'));
if(c.get('visible')) A.one("#calendar").addClass('exxo-shadow1');
else A.one("#calendar").removeClass('exxo-shadow1');
});
}}

}).render('#calendar');

});