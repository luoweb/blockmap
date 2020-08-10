document.write('<link href="css/modal.css" rel="stylesheet" />');
document.write('<script src="js/jquery-3.4.1.min.js"></script>');
document.write('<script src="js/modal.js"></script>');


//弹窗
const alert = function(msg, confunc) {
	var mModal1 = new mModal({
		content: msg,
		showCancelButton: false,
		showClose: true,
		confirm: function(){
			mModal1.close();
		}
	});
	mModal1.renderDom();
}
const confirmDialog = function(msg,title, confirmCallBack, cancleCallback) {
	var mModal2 = new mModal({
		content: msg,
		confirm: confirmCallBack,
		cancel: cancleCallback
	});
	mModal2.renderDom();
}

