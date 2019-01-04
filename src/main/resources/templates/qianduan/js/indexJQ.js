
$(function () {
    //导航栏
    $("#DrNav").mouseover(function () {$( "#DRsubNav").show();}).mouseout(function () {$( "#DRsubNav").hide();});
    $("#qhzjNav").mouseover(function () {$( "#qhzjsubNav").show();}).mouseout(function () {$( "#qhzjsubNav").hide();});
    $("#zaskNav").mouseover(function () {$( "#zasksubNav").show();}).mouseout(function () {$( "#zasksubNav").hide();});
    $("#jnrlwNav").mouseover(function () {$( "#jnrlwsubNav").show();}).mouseout(function () {$( "#jnrlwsubNav").hide();});
    $("#shopNav").mouseover(function () {$( "#ShopsubNav").show();}).mouseout(function () {$( "#ShopsubNav").hide();});
    $("#DRsqNav").mouseover(function () {$( "#DRsqsubNav").show();}).mouseout(function () {$( "#DRsqsubNav").hide();});
    $("#ppwhNav").mouseover(function () {$( "#ppwhsubNav").show();}).mouseout(function () {$( "#ppwhsubNav").hide();});

    //第二个导航子导航(0)
    $(".dr_navsuv-same:eq(0)>a").mouseover(function () {
        $(".dr_navsuv-ring:eq(0)>a:eq("+$(this).index()+")").show();
        $(".dr_navsuv-ring:eq(0)>a:eq("+$(this).index()+")").siblings().hide();
    }).mouseout(function () {
        $(".dr_navsuv-ring:eq(0)>a:eq("+$(this).index()+")").hide();
        $(".dr_navsuv-ring:eq(0)>a:eq(0)").show();
    });
    //第二个导航子导航(1)
    $(".dr_navsuv-same:eq(1)>a").mouseover(function () {
        $(".dr_navsuv-ring:eq(1)>a:eq("+$(this).index()+")").show();
        $(".dr_navsuv-ring:eq(1)>a:eq("+$(this).index()+")").siblings().hide();
    }).mouseout(function () {
        $(".dr_navsuv-ring:eq(1)>a:eq("+$(this).index()+")").hide();
        $(".dr_navsuv-ring:eq(1)>a:eq(1)").show();
    });

    //DR实体地区显示
    //默认显示华北区，隐藏其他区
    $(".drstore-tabhover:eq(0)").children("div").show().end().siblings().children("div").hide();
    $(".drstore-tabhover").mouseover(function () {
        $(this).children("div").show();
        $(this).siblings().children("div").hide();
    }).mouseout(function () {
        $(this).children("div").hide();
        $(".drstore-tabhover:eq(0)").children("div").show();
    });

    /*首页轮播*/
    var len = $("#drHomeBan li").length; //获取焦点图个数
    var index = 0;
    var picTimer;


    //为小按钮添加鼠标滑入事件，以显示相应的内容
    $("#drHomeBanxd>li").css("opacity",0.4).mouseenter(function() {
        index = $("#drHomeBanxd>li").index(this);
        showPics(index);
    }).eq(0).trigger("mouseenter");

    //上一页、下一页按钮透明度处理
    $("#focus .drbanBtn").css("opacity",0.2).hover(function() {
        $(this).stop(true,false).animate({"opacity":"0.5"},300);
    },function() {
        $(this).stop(true,false).animate({"opacity":"0.2"},300);
    });

    //上一页按钮
    $("#drbanPrev").click(function() {
        index -= 1;
        if(index == -1) {index = len - 1;}
        showPics(index);
    });

    //下一页按钮
    $("#drbanNext").click(function() {
        index += 1;
        if(index == len) {index = 0;}
        showPics(index);
    });

    //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
    $("#focus").hover(function() {
        clearInterval(picTimer);
    },function() {
        picTimer = setInterval(function() {
            showPics(index);
            index++;
            if(index == len) {index = 0;}
        },4000); //此4000代表自动播放的间隔，单位：毫秒
    }).trigger("mouseleave");


    function showPics(index) { //普通切换
        $("#drHomeBan li:eq("+index+")").fadeIn(2000).siblings().fadeOut(2000);

        $("#drHomeBanxd li:eq("+index+")").css("background","#ff3c3c").siblings().css("background","gray");
    }
   /*首页轮播结束*/

   /*真爱礼物*/
  $(".highsearchCort li").mouseover(function () {
      $(this).children("div").show().end().siblings().children("div").hide();
  }).mouseout(function () {
      $(this).children("div").hide();
  });

    /*表单切换*/
    $(".dr_formTabTitle li").click(function(){
        //通过 .index()方法获取元素下标，从0开始，赋值给某个变量
        var _index = $(this).index();
        //让内容框的第 _index 个显示出来，其他的被隐藏
        $(".dr_formTabMain .dr_formTabCon").eq(_index).show().parent().siblings().children("div").hide();
        //改变选中时候的选项框的样式，移除其他几个选项的样式
        if(_index == 0){
            $(this).removeClass("changeLeft").css("background", "#fff").siblings().addClass("changeRight").css("background", "#f5f5f5");
        }else{
            $(this).removeClass("changeRight").css("background", "#fff").siblings().addClass("changeLeft").css("background", "#f5f5f5");
        }
    });

 /* //自动校验验证码再登录
	$("#mobile_log").click(function(){
		var code = $("#yzCode").val();
		if(code != null && code != ""){
			$.post("../UserServlet?action=veryCode","yzcode="+code,callBack);
			function callBack(data){	
    			if(data == "false"){
    			   $("#prError").show().html("验证码错误！");
    			}else{
    				$("#loginform").submit();
    			}
    		}
    		 $("#prError").hide().html("");	      		 
		}else{
			$("#prError").show().html("验证码不能为空！");
		}
		return false;
	});*/
	

	
	
	 //展开查看详情
	   $(".ringdetail_show").click(   
	       function () {
	    	   var index = $(this).index();
	    	   if($(this).eq(index).siblings().css("display") == "none"){ 	   
	    	   $(this).eq(index).siblings().slideDown(500);}
	    	   else{
	    		   $(this).eq(index).siblings().slideUp(500);
	    	   }
           });
	   //地址的显示
	    $(".address_other").click(function () {
	        $(".address_hide").show();
	    });

	    //订单详情展开与隐藏
	    $(".toggle_detail").click(
	             function () {
	               if($(".detail_hide").css("display") == "none"){
	                   $(".detail_hide").slideDown(500);
	               }else{
	                   $(".detail_hide").slideUp(500);
	               }});
	    
	  
});


