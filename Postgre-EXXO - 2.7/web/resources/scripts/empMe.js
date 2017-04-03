AUI().ready('aui-node', 'aui-autosize', 'aui-loading-mask',function(A){
A.all('textarea').each(function(){this.plug(A.Plugin.Autosize, {
minHeight: '100px',
maxHeight: '400px'
});});
var node=A.one('#exxo-start-ins');
node.plug(A.LoadingMask,{background: '#000'});
A.one('#emp1 input[type=submit]').after('click',node.loadingmask.toggle, node.loadingmask);
});