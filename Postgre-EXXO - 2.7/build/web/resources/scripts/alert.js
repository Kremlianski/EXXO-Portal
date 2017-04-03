AUI().ready( 'aui-node', function(A) {
var myHeight=A.DOM.winHeight()-300;
if(myHeight<300) myHeight=300;
A.one("#main").setStyle("height",myHeight+"px");
});