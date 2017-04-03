AUI().ready('anim', 'aui-io', 'aui-resize', 'imageloader', 'aui-overlay-mask', function (A) {
    var data;
    var myEditor;
    var of = 0;
    var ROWS = A.one('#maxRows').get('value') / 1;
    var title = '';
    var text = '';
    var id = '';
    A.one('#imges').setStyle('opacity', '0');
    editorPut();
    var overlayMask = new A.OverlayMask().render();
    function editorPut() {
        myEditor = new YAHOO.widget.Editor('form:msgpost', {
            height: '275px', width: '700px', dompath: false, animate: true,
            css: 'html { height: 95%; } body { padding: 7px; box-sizing: border-box; -moz-box-sizing: border-box; background-color: #fff; font: 14px arial,helvetica,clean,sans-serif;*font-size:small;*font:x-small;line-height: 2; } a, a:visited, a:hover { color: blue !important; text-decoration: underline !important; cursor: text !important; } .warning-localfile { border-bottom: 1px dashed red !important; } .yui-busy { cursor: wait !important; } img.selected { border: 2px dotted #808080; } img { cursor: pointer !important; border: none; } body.ptags.webkit div.yui-wk-p { margin: 11px 0; } body.ptags.webkit div.yui-wk-div { margin: 0; } img {max-width: 100%}'});
        myEditor.on('toolbarLoaded', function () {
            this.toolbar.set('titlebar', title);
            var flickrConfig = {type: 'push', label: 'Вставить фото сотрудника', value: 'imgEmp'}
            myEditor.toolbar.addButtonToGroup(flickrConfig, 'insertitem');
            this.toolbar.on('imgEmpClick', function () {
                var _sel = this._getSelectedElement();
                if (_sel && _sel.tagName && (_sel.tagName.toLowerCase() == 'img')) {
                } else {
                    openAnim.run();
                    inserter("emp", myEditor);
                    return false;
                }
            }, this, true);
            var imgMyConfig = {type: 'push', label: 'Вставить фото из моей галереи', value: 'imgMy'}
            myEditor.toolbar.addButtonToGroup(imgMyConfig, 'insertitem');
            this.toolbar.on('imgMyClick', function () {
                var _sel = this._getSelectedElement();
                if (_sel && _sel.tagName && (_sel.tagName.toLowerCase() == 'img')) {
                } else {
                    openAnim.run();
                    of = 0;
                    Reload("1", null, null);
                    return false;
                }
            }, this, true);
            var imgMyKConfig = {type: 'push', label: 'Вставить фото из общей галереи', value: 'imgMyK'}
            myEditor.toolbar.addButtonToGroup(imgMyKConfig, 'insertitem');
            this.toolbar.on('imgMyKClick', function () {
                var _sel = this._getSelectedElement();
                if (_sel && _sel.tagName && (_sel.tagName.toLowerCase() == 'img')) {
                } else {
                    of = 0;
                    openAnim.run();
                    Reload("1", null, "-100");
                    return false;
                }
            }, this, true);
        });
        myEditor.render();
        myEditor.on('windowRender', function () {
            new A.Resize({node: '.yui-editor-editable-container', handles: 'b'}).render();
        });
    }
    A.one('#submitB [type=submit]').on('click', function (e) {
        YAHOO.widget.EditorInfo.saveAll();
    });
    function closer(node) {
        closeAnim.run();
    }
    function inserter(type, myEditor) {
        var Ser = "";
        var Data;
        if (type == "emp") {
            Ser = 'imgesOut';
            Data = {}
        } else if (type == "my") {
            Ser = 'galClassic';
            Data = {owner: '10', superior: '1'}
        }
        var io = A.io.request(Ser, {dataType: 'text', cache: false, data: Data, method: 'post',
            on: {
                success: function (event, id, xhr) {
                    var Datas = this.get('responseData');
                    A.one('#imges #inserter').html(Datas);
                    A.all('img.photo').each(function () {
                        this.on("click", function () {
                            myEditor.execCommand('insertimage', this.getAttribute('src'));
                            closer(A.one("#imges"));
                        })
                    });
                }}});
    }
    function Reload(superior, id, owner) {
        A.one("#inserter").unplug(A.Plugin.IO);
        var data = {superior: superior, of: of};
        if (owner != null)
            data = {superior: superior, owner: owner, of: of};
        if (id != null && owner != null)
            data = {id: id, owner: owner, of: of};
        else if (id != null && owner == null)
            data = {id: id, of: of};
        A.one("#inserter").plug(A.Plugin.IO, {uri: 'galClassic', method: 'POST', data: data, on: {'end': function (event) {
                    A.all(".cat").each(function () {
                        this.on("mouseover", function () {
                            this.addClass("file-hover");
                        })
                    });
                    A.all(".fil").each(function () {
                        this.on("mouseover", function () {
                            this.addClass("file-hover");
                        })
                    });
                    A.all(".item").each(function () {
                        this.on("mouseout", function () {
                            this.removeClass("file-hover");
                        })
                    });
                    new A.ImgLoadGroup({foldDistance: 25, className: 'exxo-loading'});
                    A.all(".fil a").each(function () {
                        var href = this.getAttribute('href');
                        this.setAttribute('href', 'javascript:')
                        this.on("click", function () {
                            var html = '<a href="' + href + '" class="imger"><img src="' + href + '&icon=1" title="' + this.getAttribute('title') + '"></a>';
                            myEditor.execCommand('inserthtml', html);
//myEditor.execCommand('insertimage', href);
                            closer(A.one("#imges"));
                            return false;
                        })
                    });
                    A.all(".cat").each(function () {
                        this.on("click", function () {
                            of = 0;
                            if (!this.hasClass("up"))
                                Reload(this.get("id"), null, owner);
                            else
                                Reload("1", null, owner);
                        })
                    });
                    A.all(".cat1").each(function () {
                        this.on("click", function () {
                            of = 0;
                            if (!this.hasClass("up"))
                                Reload(this.get("id"), null, owner);
                            else
                                Reload("1", null, owner);
                        })
                    });
                    if (A.one("#forward") != null)
                        A.one("#forward").on("click", function () {
                            of = of + ROWS;
                            Reload(superior, id, owner);
                            A.one("#imges #close").scrollIntoView( );
                        });
                    if (A.one("#backward") != null)
                        A.one("#backward").on("click", function () {
                            of = of - ROWS;
                            if (of < 0)
                                of = 0;
                            Reload(superior, id, owner);
                            A.one("#imges #close").scrollIntoView( );
                        });
                }}})
    }
    A.one('#close span').on('click', function (event) {
        closer(A.one("#imges"));
    });
    var myHeight = A.DOM.winHeight() - 50;
    if (myHeight < 250)
        myHeight = 250;
    var node = A.one(A.one("#imges"));
    node.setStyle('opacity', '0');
    var openAnim = new A.Anim({node: node, to: {opacity: 1}, on: {'start': function () {
                A.one(node).setStyle('display', 'block');
                node.one('#close span').scrollIntoView( );
                overlayMask.set('target', document).show();
            }}});
    var closeAnim = new A.Anim({node: node, to: {opacity: 0}, on: {'end': function () {
                A.one(node).setStyle('display', 'none');
                overlayMask.set('target', document).hide();
            }}});
    node.one('#close span').on('click', function () {
        closeAnim.run();
    });
    A.one('#imgesInner').setStyle("minHeight", A.DOM.winHeight() - 10 + "px");
});