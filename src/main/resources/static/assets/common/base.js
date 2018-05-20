/**
 * Created by Garry King on 2016/9/25.
 */

//===============================
// 公共方法
//===============================
function commAjaxByGET(url, param, callback, errorCallBack, headerParam) {
    jQuery.ajax({
        url: url,
        dataType: 'json',
        type: "GET",
        data: param,
        headers: headerParam,
        success: callback,
        error: function (data) {
            console.log("执行 " + url + " 出错了...");
            if (errorCallBack) {
                errorCallBack(data);
            }
        }
    });
}

function commAjaxByPost(url, param, callback, errorCallBack, headerParam) {
    jQuery.ajax({
        url: url,
        dataType: 'json',
        type: "POST",
        data: param,
        headers: headerParam,
        success: callback,
        error: function (data) {
            console.log("执行 " + url + " 出错了...");
            if (errorCallBack) {
                errorCallBack(data);
            }
        }
    });
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数

    // 解码返回
    if (r != null) return decodeURI(r[2]);
    return null; //返回参数值
}

function loadURL(url) {
    var iFrame;
    iFrame = document.createElement("iframe");
    iFrame.setAttribute("src", url);
    iFrame.setAttribute("style", "display:none;");
    iFrame.setAttribute("height", "0px");
    iFrame.setAttribute("width", "0px");
    iFrame.setAttribute("frameborder", "0");
    document.body.appendChild(iFrame);
    iFrame.parentNode.removeChild(iFrame);
    iFrame = null;
}

function checkMobile() {
    var flag = false;
    var agent = navigator.userAgent.toLowerCase();
    var keywords = ["android", "iphone", "ipod", "ipad", "windows phone", "mqqbrowser", "KEDIRF-APP"];

    //排除 Windows 桌面系统
    if (!(agent.indexOf("windows nt") > -1) || (agent.indexOf("windows nt") > -1 && agent.indexOf("compatible; msie 9.0;") > -1)) {
        //排除苹果桌面系统
        if (!(agent.indexOf("windows nt") > -1) && !agent.indexOf("macintosh") > -1) {
            for (var item in keywords) {
                if (agent.indexOf(item) > -1) {
                    flag = true;
                    break;
                }
            }
        }
    }
    return flag;
}

function checkMobile(phone) {
    var reg = /^1[0-9]{10}$/;
    if (!(reg.test(phone))) {
        return false;
    }
    return true;
}

function isStringNull(str) {
    if (str == undefined || str == "") return true;
    return false;
}

function getSafetyString(content) {
    if (content == null || content == undefined) return "";
    return content;
}
function showPopover(target, msg) {
    target.attr("data-original-title", msg);
    $('[data-toggle="tooltip"]').tooltip();
    target.tooltip('show');
    target.focus();

    //2秒后消失提示框
    var id = setTimeout(
        function () {
            target.attr("data-original-title", "");
            target.tooltip('hide');
        }, 2000
    );
}
// showPopover($("#tableName"),"表名已存在");
