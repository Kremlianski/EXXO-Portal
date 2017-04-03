AUI().ready('aui-node', 'event-custom', 'aui-form-validator', function(A){
A.publish('validated',{broadcast:2});
new A.FormValidator({
boundingBox: 'form#emp',
validateOnInput: true,
extractRules: true,
on:{submit: function(event){A.fire('validated',{t:1});}},
strings: { required: 'Поле нужно заполнить!', email:'Задайте правильный E-mail'},
fieldStrings: {
units: {digits: 'Неправильное число',	min:'Задайте подразделение'}},
rules: {units:{digits: true, min: 1}}});
});