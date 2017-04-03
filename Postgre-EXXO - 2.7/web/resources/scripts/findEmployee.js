AUI().ready('aui-live-search', function(A) {

var liveSearch = new A.LiveSearch({
input: '#query1',
nodes: '.emps',

data: function(node) {
var res="";
node.all('a').each(function(){res+=this.text();});
node.all('span').each(function(){res+=this.text();});
return res;
},

show: function(node) {
if (!node.hasClass('excluded')) {
var data = node._OLD_DATA;

if (!data) {
data = node.html();
node._OLD_DATA = data;
}

var search = this.get('searchValue');

if (search&&search!=' ') {
data = data.replace(new RegExp(search, 'gi'), '<b>' + search + '</b>');
}

node.html(data);

node.show();
node.setAttribute('nodeStatus', 'show');
node.addClass('matched');
}

return node;
},

hide: function(node) {
if (!node.hasClass('excluded')) {
node.hide();
node.setAttribute('nodeStatus', 'hide');
node.removeClass('matched');
}
},

on: {
search: function(event) {
}
},

after: {
search: function(event) {
if (!this.get('searchValue')) {
this.get('nodes').removeClass('matched');
}
}
}
});
});
