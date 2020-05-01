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

mui('.mui-table-view').on('tap', '#newguarantee', function(e) {
	mui.openWindow({
	    url: 'newguarantee.html', 
	    id:'newguarantee'
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

/*进入我的二维码名片页面*/
mui('#head-me').on('tap', '#openMyPopover', function(e) {
	mui.openWindow({
	    url: 'erweimacar.html', 
	    id:'erweimacar'
	  });
});

//点击侧滑栏的选项
mui("#offCanvasSideScroll").on('tap', '.mui-table-view-cell', function(e) {
	var id = this.id;
	var head = document.getElementById("head-index");
	document.getElementById("head-contract").style.display="none";//隐藏
	document.getElementById("head-community").style.display="none";//隐藏
	document.getElementById("head-me").style.display="none";//隐藏
	document.getElementById("head-index").style.display="";//显示
	var table = document.body.querySelector('#pullrefresh-table');
	table.innerHTML = '<ul></ul>';
	if(id == "inbox") {
		head.children[2].innerHTML = "收件箱";
		getInbox();
	} else if(id == "draftbox") {
		head.children[2].innerHTML = "草稿箱";
		getDrafts();
	} else if(id == "outbox") {
		head.children[2].innerHTML = "已发送";
		if(localStorage.outboxmails == null) {
			getOutbox(1);
		} else {
			getOutbox(0);
		}
	} else if(id == "rabbox") {
		head.children[2].innerHTML = "垃圾箱";
	}
});

if(localStorage.inboxmails == null) {
	getInbox();
} else {
	showInbox();
}


//获取收件箱
function getInbox() {
	var begindate = "";
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth()+1;
	var day = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if(month < 10) {
		month = "0"+month;
	}
	if(day < 10) {
		day = "0"+day;
	}
	if(hour < 10) {
		hour = "0"+hour;
	}
	if(minute < 10) {
		minute = "0"+minute;
	}
	if(second < 10) {
		second = "0"+second;
	}
	var nowdate = year + "-" + month +"-"+day+" "+hour+":"+minute+":"+second;
	if(localStorage.inboxdate == null) {
		begindate = '2000-01-01 00:00:01';
	} else {
		begindate = localStorage.inboxdate;
	}
	console.log("begindate:"+begindate);
	mui.ajax('http://122.18.61.62:9080/valexchsys/mailmgt.flowc',{
		dataType:"json",
		data :{
			flowActionName: 'qrymail',
			user: localStorage.userName,
			qrytype: "1",
			begindate: begindate
		},
		type:'post',//HTTP请求类型
		timeout:5000,//超时时间设置为10秒；
		crossDomain:true,
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/x-www-form-urlencoded;charset=utf-8'
		},
		success:function(data){
			//服务器返回响应，根据响应结果，分析是否登录成功；
//			mui.alert("信息发送成功");
			var mails = data.mailList;
			console.log("收件mails："+mails);
			var table = document.body.querySelector('#pullrefresh-table');
			table.innerHTML = '<ul></ul>';
			localStorage.inboxdate = nowdate;
			if(localStorage.inboxmails == null) {
				localStorage.inboxmails = JSON.stringify(mails);
				showInbox();
			} else {
				var json = JSON.parse(localStorage.inboxmails);
				mui.each(mails,function(index,item){
					json.push(item);
			    });
			    localStorage.inboxmails = JSON.stringify(json);
			    showInbox();
			}
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			mui.alert("数据请求失败:"+type);
			
//			var data = {"errorCode":"0", "mailList":[{"sender":"工商银行001","mailID":"0001","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:01"},{"sender":"工商银行002","mailID":"0002","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:02"},{"sender":"工商银行003","mailID":"0003","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:03"},{"sender":"工商银行004","mailID":"0004","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:04"},{"sender":"工商银行005","mailID":"0005","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:05"},{"sender":"工商银行006","mailID":"0006","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:06"},{"sender":"工商银行007","mailID":"0007","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:00:07"}]};
		}
	});
}

//展示收件箱
function showInbox() {
	//读取本地数据
	var mails = JSON.parse(localStorage.inboxmails);
	var table = document.body.querySelector('#pullrefresh-table');
	table.innerHTML = '<ul></ul>';
	for(var i = 0; i < mails.length; i++) {
		var item = mails[i];
		console.log("item:"+JSON.stringify(item));
		var mailID = item.mailID;  
		sender = item.sender;  
		receiver = item.receiver[0]; 
		subject = item.subject;  
		templateID = item.templateID;
		sendDate = item.sendDate;
		templateFields = item.templateFields;
		
		var icon = "";
		if(sender == "南非" || sender == "南非标准银行"){
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/myinfo/nanfei.png">';
		} else if(sender == "商汤" || sender == "商汤科技有限公司") {
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/addrbook/shangtan.png">';
		} else{
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/inbox/icbc.jpg">';
		}
		
		var li = document.createElement('li');
		li.className = 'pullrefresh-table-cell';
		li.dmailID = mailID;
		li.dsender = sender;
		li.dreceiver = receiver;
		li.dsubject = subject;
		li.dtemplateID = templateID;
		li.dsendDate = sendDate;
		li.templateFields = templateFields;
		li.qrytype = "inbox";
		li.innerHTML = '<div class="title time">'+sendDate+'</div>'+
		'<ul class="mui-table-view">'+
		'<li class="mui-table-view-cell">'+
		icon +
		'<div class="mui-media-object ">'+sender+'</div>'+
		'<div style="margin-top:10px">保函单号'+mailID+'</div>'+
		'<div style="margin-top:10px; color: burlywood;">'+subject+'</div>'+
		'</a></li></ul></li>';
			
		 //新纪录插到最前面
		 table.insertBefore(li, table.firstChild);
	}
}


//获取发件箱
function getOutbox() {
	var begindate = "";
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth()+1;
	var day = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if(month < 10) {
		month = "0"+month;
	}
	if(day < 10) {
		day = "0"+day;
	}
	if(hour < 10) {
		hour = "0"+hour;
	}
	if(minute < 10) {
		minute = "0"+minute;
	}
	if(second < 10) {
		second = "0"+second;
	}
	var nowdate = year + "-" + month +"-"+day+" "+hour+":"+minute+":"+second;
	if(localStorage.outboxdate == null) {
		begindate = '2000-01-01 00:00:01';
	} else {
		begindate = localStorage.outboxdate;
	}
	mui.ajax('http://122.18.61.62:9080/valexchsys/mailmgt.flowc',{
		dataType:"json",
		data :{
			flowActionName: 'qrymail',
			user: localStorage.userName,
			qrytype: "2",
			begindate: begindate
		},
		type:'post',//HTTP请求类型
		timeout:5000,//超时时间设置为10秒；
		crossDomain:true,
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/x-www-form-urlencoded;charset=utf-8'
		},
		success:function(data){
			var mails = data.mailList;
			console.log("data:"+JSON.stringify(data));
			console.log("发件mails:"+JSON.stringify(mails));
			var table = document.body.querySelector('#pullrefresh-table');
			table.innerHTML = '<ul></ul>';
			localStorage.outboxdate = nowdate;
			if(localStorage.outboxmails == null) {
				localStorage.outboxmails = JSON.stringify(mails);
				showOutbox();
			} else {
				var json = JSON.parse(localStorage.outboxmails);
				mui.each(mails,function(index,item){
					json.push(item);
			    });
			    localStorage.outboxmails = JSON.stringify(json);
			    showOutbox();
			}
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			mui.alert("数据请求失败:"+type);
			
//			var data = {"errorCode":"0", "mailList":[{"sender":"工商银行001","mailID":"0001","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:01"},{"sender":"工商银行002","mailID":"0002","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:02"},{"sender":"工商银行003","mailID":"0003","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:03"},{"sender":"工商银行004","mailID":"0004","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:04"},{"sender":"工商银行005","mailID":"0005","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:05"},{"sender":"工商银行006","mailID":"0006","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:06"},{"sender":"工商银行007","mailID":"0007","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:07"}]};
		}
	});
}

//展示发件箱
function showOutbox() {
	//读取本地数据
	var mails = JSON.parse(localStorage.outboxmails);
	var table = document.body.querySelector('#pullrefresh-table');
	table.innerHTML = '<ul></ul>';
	for(var i = 0; i < mails.length; i++) {
		var item = mails[i];
		var mailID = item.mailID,  
		sender = item.sender,  
		receiver = item.receiver.toString(),  
		subject = item.subject,  
		templateID = item.templateID;
		templateFields = item.templateFields;
		sendDate = item.sendDate;
		
		var icon = "";
		if(receiver == "南非" || receiver == "南非标准银行"){
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/myinfo/nanfei.png">';
		} else if(receiver == "商汤" || receiver == "商汤科技有限公司") {
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/addrbook/shangtan.png">';
		} else{
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/inbox/icbc.jpg">';
		}
		
		var li = document.createElement('li');
		li.className = 'pullrefresh-table-cell';
		li.dmailID = mailID;
		li.dsender = sender;
		li.dreceiver = receiver;
		li.dsubject = subject;
		li.dtemplateID = templateID;
		li.dsendDate = sendDate;
		li.templateFields = templateFields;
		li.qrytype = "outbox";
		li.innerHTML = '<div class="title time">'+sendDate+'</div>'+
		'<ul class="mui-table-view">'+
		'<li class="mui-table-view-cell">'+
		icon +
		'<div class="mui-media-object ">'+receiver+'</div>'+
		'<div style="margin-top:10px">保函单号'+mailID+'</div>'+
		'<div style="margin-top:10px; color: burlywood;">'+subject+'</div>'+
		'</a></li></ul></li>';
			
		 //新纪录插到最前面
		 table.insertBefore(li, table.firstChild);
	}
}


/*进入详情事件*/
mui('.mui-table-view').on('tap', '.pullrefresh-table-cell', function(e) {
	var detaildmailID = this.dmailID;
	/*var detaildsender = this.dsender;
	var detaildreceiver = this.dreceiver;
	var detaildsubject = this.dsubject;
	var detaildtemplateID = this.dtemplateID;
	var detaildsendDate = this.dsendDate;
	var detaildtemplateFields = this.templateFields;*/
	var qrytype = this.qrytype;
	/*console.log("detaildmailID"+detaildmailID);
	console.log("detaildsender:"+detaildsender);
	var maildetail = {  
        mailID: detaildmailID,
        sender: detaildsender,
        receiver: detaildreceiver,
        subject: detaildsubject,
        templateID: detaildtemplateID,
        sendDate: detaildsendDate,
        templateFields: detaildtemplateFields,
        qrytype: qrytype,
    };
    console.log("maildetail:"+maildetail);
    
    var str = JSON.stringify(maildetail);  
    localStorage.mailDetailOne = str;  
    
	mui.openWindow({
	    url: 'inboxdetail.html', 
	    id:'inboxdetail',
	    extras:{
      		mailDetailOne:str
    	}
	});*/
	
	mui.ajax('http://122.18.61.62:9080/valexchsys/mailmgt.flowc',{
		dataType:"json",
		data :{
			flowActionName: 'qrymail',
			mailID: detaildmailID,
			qrytype: "3"
		},
		type:'post',//HTTP请求类型
		timeout:5000,//超时时间设置为10秒；
		crossDomain:true,
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/x-www-form-urlencoded;charset=utf-8'
		},
		success:function(data){
			var mailOne = data.mailList;
			console.log("详情data:"+JSON.stringify(data));
			console.log("mailOne:"+JSON.stringify(mailOne));
			var str = JSON.stringify(mailOne);  
		    localStorage.mailDetailOne = str;  
		    localStorage.qrytype = qrytype;
		    
			mui.openWindow({
			    url: 'inboxdetail.html', 
			    id:'inboxdetail'
			    /*extras:{
		      		mailDetailOne: str,
		      		qrytype: qrytype
		    	}*/
			});
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			mui.alert("数据请求失败:"+type);
			
//			var data = {"errorCode":"0", "mailList":[{"sender":"工商银行001","mailID":"0001","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:01"},{"sender":"工商银行002","mailID":"0002","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:02"},{"sender":"工商银行003","mailID":"0003","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:03"},{"sender":"工商银行004","mailID":"0004","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:04"},{"sender":"工商银行005","mailID":"0005","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:05"},{"sender":"工商银行006","mailID":"0006","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:06"},{"sender":"工商银行007","mailID":"0007","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:07"}]};
		}
	});
	
});


//获取草稿箱
function getDrafts(){
	var userId = localStorage.userId;
	console.log(userId);
	mui.ajax('http://122.18.61.62:9080/valexchsys/mailmgt.flowc',{
		dataType:"json",
		data :{
			flowActionName:'qrydrafts',
			userId: userId
		},
		type:'post',//HTTP请求类型
		timeout:5000,//超时时间设置为10秒；
		crossDomain:true,
		headers:{
			'Access-Control-Allow-Origin':'*',
			'Access-Control-Allow-Headers':'Content-Type,Content-Length,Authorization,Accept,X-Requested-With,yourHeaderFeild',
			'Access-Control-Allow-Methods':'PUT,POST,GET,DELETE,OPTIONS',
			'X-Powered-By':'3.2.1',
			'Content-Type':'application/x-www-form-urlencoded;charset=utf-8'
		},
		success:function(data){
			console.log("data:"+JSON.stringify(data));
			var drafts = data.draftsList;
			console.log("drafts:"+JSON.stringify(drafts));
			if(data.errorCode === "0"){
    			localStorage.draftsList = JSON.stringify(drafts);
    			showDrafts();
    		} else {
    			mui.alert(data.errorMsg);
    		}
		},
		error:function(xhr,type,errorThrown){
			//异常处理；
			mui.alert("数据请求失败:"+type);
			
//			var data = {"errorCode":"0", "mailList":[{"sender":"工商银行001","mailID":"0001","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:01"},{"sender":"工商银行002","mailID":"0002","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:02"},{"sender":"工商银行003","mailID":"0003","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:03"},{"sender":"工商银行004","mailID":"0004","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:04"},{"sender":"工商银行005","mailID":"0005","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:05"},{"sender":"工商银行006","mailID":"0006","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:06"},{"sender":"工商银行007","mailID":"0007","receiver":["mo","hai"],"subject":"test","templateID":"Temp123","sendDate":"2019-07-10 00:01:07"}]};
		}
	});
}

//展示草稿箱
function showDrafts() {
	//读取本地数据
	console.log("localStorage.draftsList:"+localStorage.draftsList);
	var draftsList = JSON.parse(localStorage.draftsList);
	console.log("draftsList:"+draftsList);
	var table = document.body.querySelector('#pullrefresh-table');
	table.innerHTML = '<ul></ul>';
	for(var i = 0; i < draftsList.length; i++) {
		var item = draftsList[i];
		var templateFields = JSON.parse(item.templateFields),  
		draftsId = item.draftsId,  
		sender = item.sender,  
		receiver = item.receiver.toString(),
		copyTo = item.copyTo.toString(), 
		subject = item.subject,  
		templateID = item.templateID;
		saveTime = item.saveTime;
		
		var icon = "";
		if(receiver == "南非" || receiver == "南非标准银行"){
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/myinfo/nanfei.png">';
		} else if(receiver == "商汤" || receiver == "商汤科技有限公司") {
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/addrbook/shangtan.png">';
		} else{
			icon = '<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="./img/inbox/icbc.jpg">';
		}
		
		var li = document.createElement('li');
		li.className = 'pullrefresh-table-cell2';
		li.id = 'tabbar-drafts';
		li.ddraftsId = draftsId;
		li.dsender = sender;
		li.dreceiver = receiver;
		li.dcopyTo = copyTo; 
		li.dsubject = subject;
		li.dtemplateID = templateID;
		li.dsaveTime = saveTime;
		li.templateFields = templateFields;
		li.innerHTML = '<div class="title time">'+saveTime+'</div>'+
		'<ul class="mui-table-view">'+
		'<li class="mui-table-view-cell">'+
		icon +
		'<div class="mui-media-object ">'+receiver+'</div>'+
		'<div style="margin-top:10px">保函号'+templateFields.guaranteeNum+'</div>'+
		'<div style="margin-top:10px; color: burlywood;">'+subject+'</div>'+
		'</a></li></ul></li>';
			
		 //新纪录插到最前面
		 table.insertBefore(li, table.firstChild);
	}
}

/*草稿进入详情重新编辑函件*/
mui('.mui-table-view').on('tap', '.pullrefresh-table-cell2', function(e) {
	var draftdraftsId = this.ddraftsId;
	var draftsender = this.dsender;
	var draftreceiver = this.dreceiver;
	var draftcopyTo = this.dcopyTo;
	var draftsubject = this.dsubject;
	var drafttemplateID = this.dtemplateID;
	var draftsaveTime = this.dsaveTime;
	var drafttemplateFields = this.templateFields;
	console.log("drafttemplateFields:"+drafttemplateFields);
	var draftsDetail = { 
		draftsId: draftdraftsId,
        sender: draftsender,
        receiver: draftreceiver,
        subject: draftsubject,
        copyTo: draftcopyTo,  
        templateID: drafttemplateID,
        saveTime: draftsaveTime,
        templateFields: drafttemplateFields
    };
    console.log("draftsDetail:"+draftsDetail);
    
    var str = JSON.stringify(draftsDetail);  
    
	mui.openWindow({
	    url: 'newguarantee.html', 
	    id:'newguarantee',
	    extras:{
      		draftsDetail:str
    	}
	});
});


//退出登录
document.getElementById("exit").addEventListener('tap', function()
{
	var btnArray = ['确定', '取消'];
	mui.confirm('是否退出登录？', '', btnArray, function(e) 
    {
        if (e.index == 1) {
        } else {//确认退出登录
        	localStorage.removeItem("inboxdate");
        	localStorage.removeItem("inboxmails");
        	localStorage.removeItem("outboxdate");
        	localStorage.removeItem("outboxmails");
        	localStorage.removeItem("mailDetailOne");
        	localStorage.removeItem("draftsList");
        	localStorage.clear();
			mui.openWindow({
			    url: 'login.html'
			});
        }
    });
});

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



