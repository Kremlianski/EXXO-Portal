AUI().ready( 'aui-node', function(A) { 
A.Node.all("#menus a").each(function(){var home=this.getAttribute("href");
if (home.indexOf("?id=")==0) this.setAttribute("href", "index.jsp"+home);});
A.one('#menus').setStyle('display', 'block').prependTo(A.one('#menu'));
});
YUI().ready( 'node-menunav', function(A) {  
var menu = A.one("#menus");
menu.plug(A.Plugin.NodeMenuNav, { submenuHideDelay: 1000, mouseOutHideDelay: 50, submenuShowDelay: 50 });
menu.get("ownerDocument").get("documentElement").removeClass("aui-loading");
 });