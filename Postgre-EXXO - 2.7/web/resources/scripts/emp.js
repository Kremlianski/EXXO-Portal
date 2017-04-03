AUI().ready('aui-node', 'event-custom', 'aui-autosize', 'aui-loading-mask',function(A){
A.publish('closeEvent',{broadcast:2});
var nodlist=A.all("#moveMain .title1,#moveMain .title2,#moveMain .title3,#moveMain .title4,#moveMain .title5,#moveMain .title6,#moveMain .title7,#moveMain .title8");
nodlist.on("mouseenter",function(event){event.target.addClass("hov");});
nodlist.on("mouseleave",function(event){event.target.removeClass("hov");});
nodlist.on("click",function(event){
var id = event.target.get("id");
var text=event.target.text();
A.one("span#unit").text(text);
A.one("[for=units]").removeClass("str");
A.one("#emp [name=supervizor]").set("value",text);
A.one("#emp [name=units]").set("value",id);
var N = A.one('#unitBlock div.aui-form-validator-message');
if(N!=null) N.remove(true);   
A.fire('closeEvent', {close: '1'});
});
A.all('textarea').each(function(){this.plug(A.Plugin.Autosize, {
minHeight: '100px',
maxHeight: '400px'
});});
var node=A.one('#exxo-start-ins');
node.plug(A.LoadingMask,{background: '#000'});
A.Global.after('validated',node.loadingmask.toggle, node.loadingmask);
});