
function test() {
	
	
		
	
			$.ajax({
	               type: "POST",
	               url: "register",
	               data: $("#register").serialize(),
	               success: function(data){
	            	   	alert(data);
	            	 
	                  }
	            });
		
			
	
	
}
