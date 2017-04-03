AUI().ready('aui-io', function(A) {
A.all("#comp .item").each(function(){
this.addClass("exxo-shadow1");
this.on("click",function(){location="empListComp.jsp?uid="+this.getAttribute("id");
})})                
;
});