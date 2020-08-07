
$(function() {
	validateKickout();
    validateRule();

    debugger
    if(getParam("msg") != null){
        $.modal.msg(getParam("msg"));
    }

	$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});

    $("#phoneLogin").click(function () {
        $("#passwordForm").css("display","none");
        $("#phoneForm").css("display","block");
    });

    $("#passwordLogin").click(function () {
        $("#phoneForm").css("display","none");
        $("#passwordForm").css("display","block");
    });

    /* 获取验证码 */
    $("#phoneCodeBtn").click(function () {
        var phone = $.common.trim($("input[name='phone']").val());
        var pattern = /^1[34578]\d{9}$/;
        if(pattern.test(phone)){
            resetCode(); //倒计时
            $.ajax({
                type: "post",
                url: ctx + "sendSMS",
                data: {
                    "phone": phone
                },
                success: function(r) {
                    $.modal.msg(r.msg);
                }
            });
        }else{
            $.modal.msg("请输入正确的手机号");
        }
    });

    /* 手机验证码登录 */
	$("#phoneBtnSubmit").click(function () {
        var phone = $.common.trim($("input[name='phone']").val());
        if(phone == "" || phone == null){
            $.modal.msg("手机号不能为空");
            return;
        }
        var pattern = /^1[34578]\d{9}$/;
        if(!pattern.test(phone)){
            $.modal.msg("请输入正确的手机号");
            return;
        }
        var phoneCode = $("input[name='phoneCode']").val();
        if(phoneCode == "" || phoneCode == null){
            $.modal.msg("请输入验证码");
            return;
        }
        $.ajax({
            type: "post",
            url: ctx + "phoneLogin",
            data: {
                "phone": phone,
                "code": phoneCode
            },
            success: function(r) {
                if (r.code == 0) {
                    location.href = ctx + 'index';
                } else {
                    $.modal.msg(r.msg);
                }
            }
        });

    });

	$("#confirmPassword").blur(function () {

        var password = $("input[name='password']").val();
        var confirmPassword = $("input[name='confirmPassword']").val();
        if(password != confirmPassword){
            $.modal.msg("两次密码不一致,请重新输入");
            $("#btnAttach").attr("disabled","disabled")
        }else{
            $("#btnAttach").removeAttr("disabled")
        }

    });

    $("#btnAttach").click(function () {
        var phone = $.common.trim($("input[name='phone']").val());
        if(phone == "" || phone == null){
            $.modal.msg("手机号不能为空");
            return;
        }
        var pattern = /^1[34578]\d{9}$/;
        if(!pattern.test(phone)){
            $.modal.msg("请输入正确的手机号");
            return;
        }
        var phoneCode = $("input[name='phoneCode']").val();
        if(phoneCode == "" || phoneCode == null){
            $.modal.msg("请输入验证码");
            return;
        }
        var password = $("input[name='password']").val();
        if(password == "" || password == null){
            $.modal.msg("请输入密码");
            return;
        }
        var confirmPassword = $("input[name='confirmPassword']").val();
        if(confirmPassword == "" || confirmPassword == null){
            $.modal.msg("请输入密码");
            return;
        }
        if(password != confirmPassword){
            $.modal.msg("两次密码不一致");
            return;
        }
        $.ajax({
            type: "post",
            url: ctx + "attach",
            data: {
                "phone": phone,
                "code": phoneCode,
                "password": password
            },
            success: function(r) {
                if (r.code == 0) {
                    location.href = ctx + 'index';
                } else {
                    $.modal.msg(r.msg);
                }
            }
        });

    });


});

$.validator.setDefaults({
    submitHandler: function() {
		login();
    }
});

    function login() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    var rememberMe = $("input[name='rememberme']").is(':checked');
    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            "username": username,
            "password": password,
            "validateCode" : validateCode,
            "rememberMe": rememberMe
        },
        success: function(r) {
            if (r.code == 0) {
                location.href = ctx + 'index';
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

function validateKickout() {
	if (getParam("kickout") == 1) {
	    layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
	        icon: 0,
	        title: "系统提示"
	    },
	    function(index) {
	        //关闭弹窗
	        layer.close(index);
	        if (top != self) {
	            top.location = self.location;
	        } else {
	            var url  =  location.search;
	            if (url) {
	                var oldUrl  = window.location.href;
	                var newUrl  = oldUrl.substring(0,  oldUrl.indexOf('?'));
	                self.location  = newUrl;
	            }
	        }
	    });
	}
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

function resetCode() {
    $('#phoneCodeBtn').css("display","none");
    $('#resetCodeBtn').css("display","block");
    var second = 60;
    var timer = null;
    timer = setInterval(function(){
        second -= 1;
        if(second >0 ){
            $('#resetCodeBtn').val(second+"s后重发");
        }else{
            clearInterval(timer);
            $('#resetCodeBtn').val("60s后重发");
            $('#phoneCodeBtn').css("display","block");
            $('#resetCodeBtn').css("display","none");
        }
    },1000);
}