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

function searchCinemaList(id){
	alert(id);
}