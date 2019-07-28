$(document).on("click", ".naccs .menu div", function() {
    var numberIndex = $(this).index();

    if (!$(this).is("active")) {
        $(".naccs .menu div").removeClass("active");
        $(".naccs ul li").removeClass("active");

        $(this).addClass("active");
        $(".naccs ul").find("li:eq(" + numberIndex + ")").addClass("active");

        var listItemHeight = $(".naccs ul")
            .find("li:eq(" + numberIndex + ")")
            .innerHeight();
        $(".naccs ul").height(listItemHeight + "px");
    }
});
      
function AllCoupons() {

    $.getJSON("http://localhost:8080/Project_Part_B/rest/customer/getAll_coup", function (result) {
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

function AllCouponsP() {

    $.getJSON("http://localhost:8080/Project_Part_B/rest/customer/getAllpurch_coup", function (result) {
    	$("#couponTblP").empty();
        $.each(result, function (i, item) {
            $("#couponTblP").append(
                "<tr>"
                + "<td>" + item.title + "</td>"
                + "<td>" + item.startDate + "</td>"
                + "<td>" + item.endDate + "</td>"
                + "<td>" + item.type + "</td>"
                + "<td>" + item.message + "</td>"
                + "<td>" + item.price + "</td>"
                + "<td>" + item.image + "</td>"
                + "</tr>")
        })
    });
}

function AllCouponsByType() {
	
	const type = document.querySelector('#type').value;
    $.getJSON(`http://localhost:8080/Project_Part_B/rest/customer/getAllpurchByType_coup?type=${type}`, function (result) {
    	$("#couponTblByType").empty();
        $.each(result, function (i, item) {
            $("#couponTblByType").append(
                "<tr>"
                + "<td>" + item.title + "</td>"
                + "<td>" + item.startDate + "</td>"
                + "<td>" + item.endDate + "</td>"
                + "<td>" + item.type + "</td>"
                + "<td>" + item.message + "</td>"
                + "<td>" + item.price + "</td>"
                + "<td>" + item.image + "</td>"
                + "</tr>")
        })
    });
}

function AllCouponsByPrice() {
	
	const price = document.querySelector('#price').value;

    $.getJSON(`http://localhost:8080/Project_Part_B/rest/customer/getAllpurchByPrice_coup?price=${price}`, function (result) {
    	$("#couponTblByPrice").empty();
        $.each(result, function (i, item) {
            $("#couponTblByPrice").append(
                "<tr>"
                + "<td>" + item.title + "</td>"
                + "<td>" + item.startDate + "</td>"
                + "<td>" + item.endDate + "</td>"
                + "<td>" + item.type + "</td>"
                + "<td>" + item.message + "</td>"
                + "<td>" + item.price + "</td>"
                + "<td>" + item.image + "</td>"
                + "</tr>")
        })
    });
}

function purchaseCoupon() {
	const idP = document.querySelector('#idP').value;
	
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/customer/purch_coup?idP=${idP}`,				
			function(data) {			
				alert(data);
			});	
}