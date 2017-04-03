YUI({filter:"raw"}).use("uploader", function(Y) {
var myHeight=Y.DOM.winHeight()-300;
if(myHeight<300) myHeight=300;    
Y.one("#main").setStyle("minHeight",myHeight+"px");
Y.one("#overallProgress").set("text", "");
if (Y.Uploader.TYPE == "html5" && !Y.UA.ios) {
var uploader = new Y.Uploader({width: "180px",height: "35px",multipleFiles: true,uploadURL: "addFiles",
simLimit: 1,withCredentials: false,selectButtonLabel:'Выберите файлы'});
var uploadDone = false;
if (Y.Uploader.TYPE == "html5") {
uploader.set("dragAndDropArea", "body");
Y.one("#ddmessage").setHTML("<strong>...или перетащите файлы в это окно</strong>");
uploader.on(["dragenter", "dragover"], function (event) {
var ddmessage = Y.one("#ddmessage");
if (ddmessage) {
ddmessage.setHTML("<strong>Файлы обнаружны, перетащите их сюда!</strong>");
ddmessage.addClass("yellowBackground");
}
});
uploader.on(["dragleave", "drop"], function (event) {
var ddmessage = Y.one("#ddmessage");
if (ddmessage) {
ddmessage.setHTML("<strong>Перетащите файлы сюда.</strong>");
ddmessage.removeClass("yellowBackground");
}
});
}
uploader.render("#selectFilesButtonContainer");
uploader.after("fileselect", function (event) {
var fileList = event.fileList;
var fileTable = Y.one("#filenames tbody");
if (fileList.length > 0 && Y.one("#nofiles")) {
Y.one("#nofiles").remove();
Y.one("#headers").removeClass('disabled');
Y.one("#uploadFilesButton").removeClass('disabled');
}
if (uploadDone) {
uploadDone = false;
fileTable.setHTML("");
}
var perFileVars = {};
Y.each(fileList, function (fileInstance) {
fileTable.append("<tr id='" + fileInstance.get("id") + "_row" + "'>" +
"<td class='filename'>" + fileInstance.get("name") + "</td>" +
"<td class='filesize'>" + fileInstance.get("size") + "</td>" +
"<td class='percentdone'>Загрузка не началась</td>" +
"<td class='serverdata'>&nbsp;</td>");
if(Y.one('#own').get('text')!='')
perFileVars[fileInstance.get("id")] = {filename: fileInstance.get("name"), superior:Y.one('#gal').get('text'), owner:Y.one('#own').get('text')};
else perFileVars[fileInstance.get("id")] = {filename: fileInstance.get("name"), superior:Y.one('#gal').get('text')}
});
uploader.set("postVarsPerFile", Y.merge(uploader.get("postVarsPerFile"), perFileVars));
});
uploader.on("uploadprogress", function (event) {
var fileRow = Y.one("#" + event.file.get("id") + "_row");
fileRow.one(".percentdone").set("text", event.percentLoaded + "%");
});
uploader.on("uploadstart", function (event) {
uploader.set("enabled", false);
Y.one("#uploadFilesButton").addClass("yui3-button-disabled");
Y.one("#uploadFilesButton").detach("click");
});
uploader.on("uploadcomplete", function (event) {
var fileRow = Y.one("#" + event.file.get("id") + "_row");
fileRow.one(".percentdone").setHTML("<div class='check'></div>");
fileRow.one(".serverdata").setHTML(event.data);
});

uploader.on("totaluploadprogress", function (event) {
Y.one("#overallProgress").setHTML("Всего загружено: <strong>" + event.percentLoaded + "%" + "</strong>");
});

uploader.on("alluploadscomplete", function (event) {
uploader.set("enabled", true);
uploader.set("fileList", []);
Y.one("#uploadFilesButton").removeClass("yui3-button-disabled");
Y.one("#uploadFilesButton").on("click", function () {
if (!uploadDone && uploader.get("fileList").length > 0) {
uploader.uploadAll();
}
});
Y.one("#overallProgress").set("text", "Загрузка завершена!");
uploadDone = true;
location=Y.one('#back').getAttribute('href');
});
Y.one("#uploadFilesButton").on("click", function () {
if (!uploadDone && uploader.get("fileList").length > 0) {
uploader.uploadAll();
}
});
}
else {
Y.one("#uploaderContainer").set("text", "К сожалению, данная функция не работает в вашем браузере!");
}
Y.publish('formClose', { broadcast: 2 });
Y.one("#form [type=submit]").after("click", function(){Y.fire('formClose', {});});
});