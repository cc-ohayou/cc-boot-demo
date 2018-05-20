$(function() {
    var _data = {}

    $('#doc-form-file').on('change', function() {
        var file = this.files[0];
        if (window.FileReader) {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            //监听文件读取结束后事件
            reader.onloadend = function (e) {
                _data.filePath = e.target.result;
                $("#file").val(e.target.result);
            };
        }
        var fileNames = '';
        $.each(this.files, function() {
            fileNames += '<span class="am-badge">' + this.name + '</span> ';
        });
        $('#file-list').html(fileNames);
    });

    var progress = $.AMUI.progress;

    $('#np-set').on('click', function() {
        progress.set(0.4);
    });

    $('#np-inc').on('click', function() {
        progress.inc();
    });

    $('#np-fd').on('click', function() {
        progress.done(true);
    });

    $('#np-status').on('click', function() {
        $(this).text('Status: ' + progress.status);
    });
});