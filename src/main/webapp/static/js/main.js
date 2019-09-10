
$().ready(function(){
	var nowUrl = window.location.href;
	if(nowUrl.indexOf('/create.html') > -1){
		$('#mynav li:eq(0)').addClass('active');
	}else if(nowUrl.indexOf('/test.html') > -1){
		$('#mynav li:eq(1)').addClass('active');
	}else if(nowUrl.indexOf('/upload.html') > -1){
		$('#mynav li:eq(2)').addClass('active');
	}else if(nowUrl.indexOf('/list.html') > -1){
		$('#mynav li:eq(3)').addClass('active');
	}else if(nowUrl.indexOf('/emailMonitor.html') > -1){
		$('#mynav li:eq(4)').addClass('active');
	}
	else{
		$('#mynav li:eq(0)').addClass('active');
	}
});