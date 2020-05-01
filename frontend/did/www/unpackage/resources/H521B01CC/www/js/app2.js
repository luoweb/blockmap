////下拉刷新
//mui.init({
//	pullRefresh2: {
//		container: '#pullrefresh2',
//		down: {
//			style:'circle',
//			callback: pulldownRefresh2
//		},
//		up: {
//			auto:true,
//			contentrefresh: '正在加载...',
//			callback: pullupRefresh2
//		}
//	}
//});
//var count = 0;
//function pullupRefresh2() {
//	setTimeout(function() {
//		mui('#pullrefresh2').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
//		var table = document.body.querySelector('#pullrefresh-table');
//		var cells = document.body.querySelectorAll('.pullrefresh-table-cell');
//		var newCount = cells.length>0?5:20;//首次加载20条，满屏
//		for (var i = cells.length, len = i + newCount; i < len; i++) {
//			var li = document.createElement('li');
//			li.className = 'pullrefresh-table-cell';
//			li.innerHTML = '<div class="title">09:55</div>'+
//			'<ul class="mui-table-view">'+
//			'<li class="mui-table-view-cell">'+
//			'<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="img/pingan.png">'+
//			'<div class="mui-media-object ">平安银行</div>'+
//			'<div style="margin-top:10px">保函单号EA463456</div>'+
//			'<div style="margin-top:10px; color: burlywood;">【待签收】保函单号EA453456 点击查看函件</div>'+
//			'</a></li></ul></li>';
//			table.appendChild(li);
//		}
//	}, 1500);
//}
//
//function addData() {
//	var table = document.body.querySelector('#pullrefresh-table');
//	var cells = document.body.querySelectorAll('.pullrefresh-table-cell');
//	for(var i = cells.length, len = i + 5; i < len; i++) {
//		var li = document.createElement('li');
//		li.className = 'pullrefresh-table-cell';
//		li.innerHTML = '<div class="title">09:55</div>'+
//		'<ul class="mui-table-view">'+
//		'<li class="mui-table-view-cell">'+
//		'<a href="javascript:;"><img class="mui-media-object mui-pull-left" src="img/pingan.png">'+
//		'<div class="mui-media-object ">平安银行</div>'+
//		'<div style="margin-top:10px">保函单号EA463456</div>'+
//		'<div style="margin-top:10px; color: burlywood;">【待签收】保函单号EA453456 点击查看函件</div>'+
//		'</a></li></ul></li>';
//		//下拉刷新，新纪录插到最前面；
//		table.insertBefore(li, table.firstChild);
//	}
//}
///**
// * 下拉刷新具体业务实现
// */
//function pulldownRefresh2() {
//	setTimeout(function() {
//		addData();
//		mui('#pullrefresh2').pullRefresh().endPulldownToRefresh();
//		mui.toast("为你推荐了5篇文章");
//	}, 1500);
//}