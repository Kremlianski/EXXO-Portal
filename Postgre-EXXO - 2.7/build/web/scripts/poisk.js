AUI().ready( 'aui-button-item', function(A) {
new A.ButtonItem({icon: 'search',label:'Искать'}).render(A.one("#b"));
A.one("#b button").setAttribute('type', 'submit');
A.one('#content-wrapper').setStyle('minHeight',A.DOM.winHeight()+'px');
});