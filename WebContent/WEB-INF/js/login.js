
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

function movieSearch() {
	$.ajax({
        type: "POST",
        url: "searchMoive",
        data: $("#searchForm").serialize(),
        success: function(data){
     	   	alert(data);
     	   $("#sub").click();
     		$("#pingtai").css("display","block");
     		alert("a");
           }
     });
	
}