$(function(){
  /*--------- show and hide the menu  ---*/
  $('.button').on("click", function(){
    if($('body').hasClass('nav_is_visible') == true){
     $('body').removeClass('nav_is_visible');
     $('.button').removeClass('close');
        }
    else{
     $('body').addClass('nav_is_visible');
     $('.button').addClass('close');
       }
   });
  
  $('body').addClass('home_is_visible');

    
 function removeClasses() {
  $(".menu ul li").each(function() {
    var custom_class = $(this).find('a').data('class');
  $('body').removeClass(custom_class);
  });
}
  
  $('.menu a').on('click',function(e){
    e.preventDefault();
    removeClasses();
    var custom_class = $(this).data('class');
    $('body').addClass(custom_class);
  });
});

function AllCoupons() {

	$.getJSON("http://localhost:8080/Project_Part_B/rest/company/getAll_coup", function (result) {
		$("#couponTbl").empty();
		$.each(result, function (i, item) {
		
			$("#couponTbl").append(
				"<tr>"
				+ "<td>" + item.id + "</td>"
				+ "<td>" + item.title + "</td>"
				+ "<td>" + item.startDate + "</td>"
				+ "<td>" + item.endDate + "</td>"
				+ "<td>" + item.amount + "</td>"
				+ "<td>" + item.type + "</td>"
				+ "<td>" + item.message + "</td>"
				+ "<td>" + item.price + "</td>"
				+ "<td>" + item.image + "</td>"
				+ "</tr>")
		})
	});
}

function createCoupon() {
	const idCreate = document.querySelector('#idCreate').value;
	const titleCreate = document.querySelector('#titleCreate').value;
	const startDateCreate = document.querySelector('#startDateCreate').value;
	const endDateCreate = document.querySelector('#endDateCreate').value;
	const amountCreate = document.querySelector('#amountCreate').value;
	const typeCreate = document.querySelector('#typeCreate').value;
	const messageCreate = document.querySelector('#messageCreate').value;
	const priceCreate = document.querySelector('#priceCreate').value;	
	const imageCreate = document.querySelector('#imageCreate').value;
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/company/create_coup?idCreate=${idCreate}&titleCreate=${titleCreate}&startDateCreate=${startDateCreate}&endDateCreate=${endDateCreate}&amountCreate=${amountCreate}&typeCreate=${typeCreate}&messageCreate=${messageCreate}&priceCreate=${priceCreate}&imageCreate=${imageCreate}`,				
			function(data) {				
				alert(data);		
			});				
}

function removeCoupon() {
	const idRemoveCoup= document.querySelector('#idRemoveCoup').value;
	
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/company/remove_coup?idRemoveCoup=${idRemoveCoup}`,
			function(data) {
				alert(data);
			});

}

function updateCoupon() {
	const idUpdate = document.querySelector('#idUpdate').value;
	const startDateUpdate = document.querySelector('#startDateUpdate').value;
	const endDateUpdate = document.querySelector('#endDateUpdate').value;
	const amountUpdate = document.querySelector('#amountUpdate').value;
	const typeUpdate = document.querySelector('#typeUpdate').value;
	const messageUpdate = document.querySelector('#messageUpdate').value;
	const priceUpdate = document.querySelector('#priceUpdate').value;	
	const imageUpdate = document.querySelector('#imageUpdate').value;
	console.log('idUpdate');
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/company/update_coup?idUpdate=${idUpdate}&startDateUpdate=${startDateUpdate}&endDateUpdate=${endDateUpdate}&amountUpdate=${amountUpdate}&typeUpdate=${typeUpdate}&messageUpdate=${messageUpdate}&priceUpdate=${priceUpdate}&imageUpdate=${imageUpdate}`,				
			function(data) {				
				alert(data);		
			});				
}

function getCoupon() {
	const idGetCoup= document.querySelector('#idGetCoup').value;
	
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/company/get_coup?idGetCoup=${idGetCoup}`,
			function(data) {
				alert(data);
			});

}

function viewIncomesFromCompany(){
	    $.getJSON("http://localhost:8080/Project_Part_B/rest/company/view_all_company_incomes", function (result) {
		$("#viewIncomesCompTbl").empty();
		$.each(result, function (i, item) {
			$("#viewIncomesCompTbl").append(
				"<tr>"
				+ "<td>" + item.amount + "</td>"
				+ "<td>" + item.date + "</td>"
				+ "<td>" + item.description + "</td>"
				+ "</tr>")
		})
	});
}