AUI().ready('aui-node', 'anim', function(A) {
var fix=false;
var node = A.Node.create("<div id=\"exxo-top-menu-fixed\" class=\"exxo-shadow1\"></div>");
var but = A.Node.create("<div id='exxo-but-up' class=\"exxo-shadow1\"></div>");
A.one('ul.topMenu').clone().appendTo(node);
node.appendTo('body');
var anim = new A.Anim({duration: 0.5,node: 'win',easing: 'easeBoth',to: {scroll: [0, 0]}});
but.prependTo(node.one('ul'));
but.on('click', function(){anim.run();});
A.one('document').on('scroll',function(){if(A.one('document').get('docScrollY')>100&&!fix) {
node.setStyle('display','block'); 
fix=true;
} else if(A.one('document').get('docScrollY')<80&&fix) {
node.setStyle('display','none'); fix=false;
} });});