AUI().ready('aui-image-cropper', function (A) {
    var imageNode = A.one('#image');
    var imageCropper;
    if (imageNode != null) {
        imageNode.setStyle('position', 'relative');
        var h = imageNode.get("height");
        var w = imageNode.get("width");

        var ch = 0;
        var cw = 0;
        var x = 0;
        var y = 0;
        var cropX = 110;
        var cropY = 147;
        var album = false;
        var normal = false;

        if (h < w)
            album = true;
        if (h > 50 && w > 50)
            if (!album) {
                if (Math.round(w * 100 / h) == 75)
                    normal = true;

                if (!normal) {
                    if (Math.round(w * 100 / h) > 75) {
                        //h<
                        ch = h;
                        cw = Math.round(ch * 3 / 4);
                        x = Math.round(w - cw) / 2;
                        y = 0;
                        cropX = cw;
                        cropY = ch;

                    } else {
                        cw = w;
                        // h> 
                        ch = Math.round(cw * 4 / 3);
                        x = 0;
                        y = Math.round(h - ch) / 2;
                        cropX = cw;
                        cropY = ch;
                    }
                } else {
                    x = 0;
                    y = 0;
                    cropX = w;
                    cropY = h;
                }

            } else {
                if (w > 110) {
                    ch = h;
                    cw = Math.round(ch * 0.75);
                    x = Math.round(w - cw) / 2;
                    y = 0;
                    cropX = cw;
                    cropY = ch;
                    //   if(h<147) mode=false;
                }
            }
        A.one('#width').val(cropX);
        A.one('#height').val(cropY);
        A.one('#x').val(Math.floor(x));
        A.one('#y').val(Math.floor(y));
        imageCropper = new A.ImageCropper({
            srcNode: imageNode,
            cropHeight: cropY - 4,
            cropWidth: cropX - 3,
            minWidth: 110,
            minHeight: 147,
            movable: true,
            preserveRatio: true,
            resizeable: true,
            x: x,
            y: y
        }).render();

        imageCropper.after('crop', function (event) {
            var cropRegion = imageCropper.get('region');
            A.one('#width').val(cropRegion.width);
            A.one('#height').val(cropRegion.height);
            A.one('#x').val(cropRegion.x);
            A.one('#y').val(cropRegion.y);
        });
    }
});