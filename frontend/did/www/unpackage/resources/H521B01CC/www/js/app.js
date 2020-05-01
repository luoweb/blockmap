mui.init({
	swipeBack:true //启用右滑关闭功能
});

//点击加载数据
mui('body').on('tap', '#info2', function(e) {
	mui.ajax('https://www.tianqiapi.com/api/?version=v1&cityid=101110101',{
		dataType:"jsonp",  //数据格式设置为jsonp
		type:'get',//HTTP请求类型
		timeout:10000,//超时时间设置为10秒；
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/json;charset=utf-8'
		},
		success:function(data){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			mui.alert(data);
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			mui.alert(type);
		}
	});
});

mui('body').on('tap', '.mui-tab-item', function(e) {
	mui('.mui-off-canvas-wrap').offCanvas().close();
	
});

mui('.mui-table-view').on('tap', '#newguarantee', function(e) {
	mui.openWindow({
	    url: 'newguarantee.html', 
	    id:'info'
	  });
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

mui('.mui-table-view').on('tap', '#newguarantee', function(e) {
	mui.openWindow({
	    url: 'newguarantee.html', 
	    id:'info'
	  });
});

mui('#offCanvasSideScroll').on('tap','#inbox', function(e) {
	mui.ajax('http://122.18.61.62:9080/valexchsys/mailmgt.flowc?flowActionName=qrymail',{
		dataType:"json",
		data :{
			user:"wentao",
			qrytype:"1"
		},
		type:'post',//HTTP请求类型
		timeout:500,//超时时间设置为10秒；
		crossDomain:true,
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/json;charset=utf-8'
		},
		success:function(data){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			mui.alert("信息发送成功");
			var mails = data.mailList;
			var table = document.body.querySelector('#pullrefresh-table');  
			mui.each(mails,function(index,item){  
		        var mailID = item.mailID,  
	            sender = item.sender,  
	            receiver = item.receiver[0],  
	            subject = item.subject,  
	            templateID = item.templateID;
	            sendDate = item.sendDate;
	            var li = document.createElement('li');  
                li.className = 'mail-cell';
                li.id = mailID;
                li.innerHTML = '<div class="title time">'+sendDate+'</div>' 
                	+ '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="img/pingan.png">'
                	+ '<div class="mui-media-object ">'+sender+'</div>'
                	+ '<div style="margin-top:10px">保函单号'+mailID+'</div>'
                	+ '<div style="margin-top:10px; color: burlywood;">【待签收】保函单号'+mailID+' 点击查看函件</div>'
                	+ '</a></li></ul>';
                //下拉刷新，新纪录插到最前面；  
                table.insertBefore(li, table.firstChild);
		   });
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			// mui.alert("数据请求失败:"+type);
			
			var data = {"errorCode":"0", "mailList":[{"sender":"工商银行001","mailID":"0001","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:01"},{"sender":"工商银行002","mailID":"0002","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:02"},{"sender":"工商银行003","mailID":"0003","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:03"},{"sender":"工商银行004","mailID":"0004","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:04"},{"sender":"工商银行005","mailID":"0005","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:05"},{"sender":"工商银行006","mailID":"0006","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:06"},{"sender":"工商银行007","mailID":"0007","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:07"}]};

			var mails = data.mailList;
			var table = document.body.querySelector('#pullrefresh-table');  
			mui.each(mails,function(index,item){  
			 var mailID = item.mailID,  
			 sender = item.sender,  
			 receiver = item.receiver[0],  
			 subject = item.subject,  
			 templateID = item.templateID;
			 sendDate = item.sendDate;
				
			var li = document.createElement('li');
			li.className = 'pullrefresh-table-cell';
			li.id = mailID;
			li.innerHTML = '<div class="title time">'+sendDate+'</div>'+
			'<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell">'+
			'<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="img/pingan.png">'+
			'<div class="mui-media-object ">'+sender+'</div>'+
			'<div style="margin-top:10px">保函单号'+mailID+'</div>'+
			'<div style="margin-top:10px; color: burlywood;">【待签收】保函单号'+mailID+' 点击查看函件</div>'+
			'</a></li></ul></li>';				
				
				
			 //下拉刷新，新纪录插到最前面；  
			 table.insertBefore(li, table.firstChild);
		});		
			
		}
	});
});

mui('#offCanvasSideScroll').on('tap','#outbox', function(e) {
	mui.ajax('http://122.18.61.62:9080/valexchsys/mailmgt.flowc?flowActionName=qrymail',{
		dataType:"json",
		data :{
			user:"wentao",
			qrytype:"1"
		},
		type:'post',//HTTP请求类型
		timeout:500,//超时时间设置为10秒；
		crossDomain:true,
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/json;charset=utf-8'
		},
		success:function(data){
			//服务器返回响应，根据响应结果，分析是否登录成功；
			mui.alert("信息发送成功");
			var mails = data.mailList;
			var table = document.body.querySelector('#pullrefresh-table');  
			mui.each(mails,function(index,item){  
		        var mailID = item.mailID,  
	            sender = item.sender,  
	            receiver = item.receiver[0],  
	            subject = item.subject,  
	            templateID = item.templateID;
	            sendDate = item.sendDate;
	            var li = document.createElement('li');  
                li.className = 'mail-cell';
                li.id = mailID;
                li.innerHTML = '<div class="title time">'+sendDate+'</div>' 
                	+ '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="img/pingan.png">'
                	+ '<div class="mui-media-object ">'+sender+'</div>'
                	+ '<div style="margin-top:10px">保函单号'+mailID+'</div>'
                	+ '<div style="margin-top:10px; color: burlywood;">【待签收】保函单号'+mailID+' 点击查看函件</div>'
                	+ '</a></li></ul>';
                //下拉刷新，新纪录插到最前面；  
                table.insertBefore(li, table.firstChild);
		   });
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			// mui.alert("数据请求失败:"+type);
			
			var data = {"errorCode":"0", "mailList":[{"sender":"工商银行001","mailID":"0001","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:01"},{"sender":"工商银行002","mailID":"0002","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:02"},{"sender":"工商银行003","mailID":"0003","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:03"},{"sender":"工商银行004","mailID":"0004","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:04"},{"sender":"工商银行005","mailID":"0005","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:05"},{"sender":"工商银行006","mailID":"0006","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:06"},{"sender":"工商银行007","mailID":"0007","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:07"}]};

			var mails = data.mailList;
			var table = document.body.querySelector('#pullrefresh-table');  
			mui.each(mails,function(index,item){  
			 var mailID = item.mailID,  
			 sender = item.sender,  
			 receiver = item.receiver[0],  
			 subject = item.subject,  
			 templateID = item.templateID;
			 sendDate = item.sendDate;
				
			var li = document.createElement('li');
			li.className = 'pullrefresh-table-cell';
			li.id = mailID;
			li.innerHTML = '<div class="title time">'+sendDate+'</div>'+
			'<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell">'+
			'<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="img/pingan.png">'+
			'<div class="mui-media-object ">'+sender+'</div>'+
			'<div style="margin-top:10px">保函单号'+mailID+'</div>'+
			'<div style="margin-top:10px; color: burlywood;">【待签收】保函单号'+mailID+' 点击查看函件</div>'+
			'</a></li></ul></li>';				
				
				
			 //下拉刷新，新纪录插到最前面；  
			 table.insertBefore(li, table.firstChild);
		});		
		
		}
	});
});

/*进入详情事件*/
mui('.mui-table-view').on('tap', '.pullrefresh-table-cell', function(e) {
	var id = this.id;
	var obj = {  
        "mailID": id
    };
    var str = JSON.stringify(obj);  
    localStorage.obj = str;  
	mui.openWindow({
	    url: 'inboxdetail.html', 
	    id:'info'
	  });
});

/*显示首页*/
mui('.mui-bar-tab').on('tap', '#tab-index', function(e) {
	document.getElementById("head-contract").style.display="none";//隐藏
	document.getElementById("head-community").style.display="none";//隐藏
	document.getElementById("head-me").style.display="none";//隐藏
	document.getElementById("head-index").style.display="";//显示
});

/*显示联系人页面*/
mui('.mui-bar-tab').on('tap', '#tab-contract', function(e) {
	document.getElementById("head-index").style.display="none";//隐藏
	document.getElementById("head-community").style.display="none";//隐藏
	document.getElementById("head-me").style.display="none";//隐藏
	document.getElementById("head-contract").style.display="";//显示
});

/*显示社区页面*/
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

