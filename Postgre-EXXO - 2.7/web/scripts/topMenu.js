AUI().ready('aui-node','aui-overlay-context', function(A) {
A.all('.menuB').each(function(){var menu=this;
var instance = new A.OverlayContext({boundingBox: menu.one('.innerMenu'),showDelay: 0,hideDelay: 100,hideOn: 'mouseleave',showOn: 'mouseenter',trigger: menu,zIndex: 1002});
if(menu.hasClass('right'))
instance.align(menu,[A.WidgetPositionAlign.TL,A.WidgetPositionAlign.BL]);
else if(menu.hasClass('center'))
instance.align(menu,[A.WidgetPositionAlign.TC,A.WidgetPositionAlign.BC]);
else instance.align(menu,[A.WidgetPositionAlign.TR,A.WidgetPositionAlign.BR]);
instance.render();
});});