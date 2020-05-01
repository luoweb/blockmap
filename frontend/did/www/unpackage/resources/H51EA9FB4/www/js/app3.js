mui.init({
	swipeBack:true //启用右滑关闭功能
	
});

mui('body').on('tap', '.mui-tab-item', function(e) {
	mui('.mui-off-canvas-wrap').offCanvas().close();
});

mui('.mui-scroll-wrapper').scroll({
    indicators: true //是否显示滚动条
});

 //实现ios平台原生侧滑关闭页面；
if (mui.os.plus && mui.os.ios) {
	mui.plusReady(function() { //5+ iOS暂时无法屏蔽popGesture时传递touch事件，故该demo直接屏蔽popGesture功能
		plus.webview.currentWebview().setStyle({
			'popGesture': 'none'
		});
	});
}

mui('.mui-scroll-wrapper').scroll({
	bounce: false,//滚动条是否有弹力默认是true
	indicators: false //是否显示滚动条,默认是true
});

mui('body').on('tap', '.mui-tab-item', function(e) {
	mui('.mui-off-canvas-wrap').offCanvas().close();
});

mui('.mui-table-view').on('tap', '#applyForm', function(e) {
	mui.openWindow({
	    url: 'applyForm.html', 
	    id:'applyForm'
	  });
});

/*显示首页*/
mui('.mui-bar-tab').on('tap', '#tab-index', function(e) {
	document.getElementById("head-contract").style.display="none";//隐藏
	document.getElementById("head-community").style.display="none";//隐藏
	document.getElementById("head-me").style.display="none";//隐藏
	document.getElementById("head-index").style.display="";//显示
});

/*显示我的数字凭证页面*/
mui('.mui-bar-tab').on('tap', '#tab-contract', function(e) {
	document.getElementById("head-index").style.display="none";//隐藏
	document.getElementById("head-community").style.display="none";//隐藏
	document.getElementById("head-me").style.display="none";//隐藏
	document.getElementById("head-contract").style.display="";//显示
	
});

/*显示扫一扫页面*/
mui('.mui-bar-tab').on('tap', '#tab-community', function(e) {
	document.getElementById("head-index").style.display="none";//隐藏
	document.getElementById("head-contract").style.display="none";//隐藏
	document.getElementById("head-me").style.display="none";//隐藏
	document.getElementById("head-community").style.display="";//显示
});

/*显示我的页面*/
mui('.mui-bar-tab').on('tap', '#tab-me', function(e) {
	document.getElementById("head-index").style.display="none";//隐藏
	document.getElementById("head-community").style.display="none";//隐藏
	document.getElementById("head-contract").style.display="none";//隐藏
	document.getElementById("head-me").style.display="";//显示
});

/*进入我的二维码名片页面*/
mui('#head-me').on('tap', '#openMyPopover', function(e) {
	mui.openWindow({
	    url: 'erweimacar.html', 
	    id:'erweimacar'
	  });
});



//退出登录
//document.getElementById("exit").addEventListener('tap', function()
//{
//	var btnArray = ['确定', '取消'];
//	mui.confirm('是否退出登录？', '', btnArray, function(e) 
//  {
//      if (e.index == 1) {
//      } else {//确认退出登录
//      	localStorage.removeItem("inboxdate");
//      	localStorage.removeItem("inboxmails");
//      	localStorage.removeItem("outboxdate");
//      	localStorage.removeItem("outboxmails");
//      	localStorage.removeItem("mailDetailOne");
//      	localStorage.removeItem("draftsList");
//      	localStorage.clear();
//			mui.openWindow({
//			    url: 'login.html'
//			});
//      }
//  });
//});

//个人页信息展示
mui('.mui-bar-tab').on('tap', '#tab-me', function(e) {
	var myinfo_user = document.getElementById("myinfo-user");
	var myinfo_name = document.getElementById("myinfo-name");
	myinfo_user.innerHTML = localStorage.userId;
	myinfo_name.innerHTML = localStorage.userName;
	if(localStorage.userName == "南非" || localStorage.userName == "南非标准银行"){
		document.getElementById("myinfo-icon").src="./img/myinfo/nanfei.png";
	} else if(localStorage.userName == "商汤" || localStorage.userName == "商汤科技有限公司") {
		document.getElementById("myinfo-icon").src="./img/addrbook/shangtan.png";
	} else{
		document.getElementById("myinfo-icon").src="./img/inbox/icbc.jpg";
	}
	
});



