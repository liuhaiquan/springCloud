$(document).ready(function(){ 
	$('#login').on('click',function(){
		
		//后台应该接收字符串类型的json
		var  parameter = JSON.stringify({
				"project":"order",
				"model":"order",
				"method":"findOrder",
				"requestJson":{"username":$('input[name=username]').val(),
							   "password": $('input[name=password]').val(),
							   "accessToken":"1223443432"
							   }
		});
		
		
		
		var url = "http://localhost:7075/order/order/callUI";
		$.ajax({
			url : url,
			type : "POST",
	        contentType: 'application/json; charset=UTF-8',
			async : true,
			cache : false,
			dataType : "json",
			data : parameter,
			success : function(data) {
				console.log(data);
			},
			error : function(data) {
				 alert("error!");
			}
		});
	});
}); 