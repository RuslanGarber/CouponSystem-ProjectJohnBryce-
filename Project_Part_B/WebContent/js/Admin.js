$("#openNav").click(function() {
  $(".page").addClass("swipe-lft");
  $(".nav").addClass("open");
});
$("#closeNav").click(function() {
  $(".page").removeClass("swipe-lft");
  $(".nav").removeClass("open");
});

//---------------------------------------------------------------
//part 3
function allIncomes() {
	
	$.getJSON("http://localhost:8080/Project_Part_B/rest/admin/get_all_income", function (result) {
		$("#incomesTbl").empty();
		$.each(result, function (i, item) {
			$("#incomesTbl").append(
				"<tr>"
				+ "<td>" + item.amount + "</td>"
				+ "<td>" + item.date + "</td>"
				+ "<td>" + item.description + "</td>"
				+ "<td>" + item.name + "</td>"
				+ "</tr>")
		})
	});
}

function incomesCompany() {
	
	const comp = document.querySelector('#comp').value;
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/all_company_income?COMP_NAME=${comp}`, function (result) {
		$("#incomesCompTbl").empty();
		$.each(result, function (i, item) {
			$("#incomesCompTbl").append(
				"<tr>"
				+ "<td>" + item.amount + "</td>"
				+ "<td>" + item.date + "</td>"
				+ "<td>" + item.description + "</td>"
				+ "</tr>")
		})
	});
}

function incomesCustomer() {
	
	const CUST_NAME = document.querySelector('#CUST_NAME').value;
	$.getJSON("http://localhost:8080/Project_Part_B/rest/admin/all_customer_income/" + CUST_NAME, function (result) {
		$("#incomesCustTbl").empty();
		$.each(result, function (i, item) {
			$("#incomesCustTbl").append(
				"<tr>"
				+ "<td>" + item.amount + "</td>"
				+ "<td>" + item.date + "</td>"
				+ "<td>" + item.description + "</td>"
				+ "</tr>")
		})
	});
}
//the end of Part 3
//--------------------------------------------------------------------


function AllCompanies() {

	$.getJSON("http://localhost:8080/Project_Part_B/rest/admin/getAll_comp", function (result) {
		$("#companyTbl").empty();
		$.each(result, function (i, item) {
			
			$("#companyTbl").append(
				"<tr>"
				+ "<td>" + item.id + "</td>"
				+ "<td>" + item.compName + "</td>"
				+ "<td>" + item.password + "</td>"
				+ "<td>" + item.email + "</td>"
				+ "</tr>")
		}) 
	});
}	

function AllCustomers() {

	$.getJSON("http://localhost:8080/Project_Part_B/rest/admin/getAll_cust", function (result) {
		$("#customerTbl").empty();
		$.each(result, function (i, item) {
			$("#customerTbl").append(
				"<tr>"
				+ "<td>" + item.id + "</td>"
				+ "<td>" + item.custName + "</td>"
				+ "<td>" + item.password + "</td>"
				+ "</tr>")
		})
	});
}

function AllCustomers() {

	$.getJSON("http://localhost:8080/Project_Part_B/rest/admin/getAll_cust", function (result) {
		$("#customerTbl").empty();
		$.each(result, function (i, item) {
			$("#customerTbl").append(
				"<tr>"
				+ "<td>" + item.id + "</td>"
				+ "<td>" + item.custName + "</td>"
				+ "<td>" + item.password + "</td>"
				+ "</tr>")
		})
	});
}

function createCompany() {
	const companieID = document.querySelector('#companieID').value;
	const COMP_NAME = document.querySelector('#COMP_NAME').value;
	const EMAIL = document.querySelector('#EMAIL').value;
	const Password = document.querySelector('#Password').value;			
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/create_comp?companieID=${companieID}&COMP_NAME=${COMP_NAME}&EMAIL=${EMAIL}&Password=${Password}`,				
			function(data) {				
				alert(data);		
			});				
}

function removeCompany() {
	const idRemove = document.querySelector('#idRemove').value;		
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/remove_comp?idRemove=${idRemove}`,				
			function(data) {			
				alert(data);
			});				
}

function updateCompany() {
	console.log('this is js create comp');
	const id = document.querySelector('#id').value;
	const email = document.querySelector('#email').value;
	const password = document.querySelector('#password').value;			
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/update_comp?id=${id}&email=${email}&password=${password}`,				
			function(data) {				
				alert(data);		
			});				
}

function getCompany() {
	const idGet = document.querySelector('#idGet').value;		
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/get_comp?idGet=${idGet}`,				
			function(data) {			
				alert(data);
			});				
}


function createCustomer() {
	const idCust = document.querySelector('#idCust').value;
	const name = document.querySelector('#name').value;
	const passwordCust = document.querySelector('#passwordCust').value;			
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/create_cust?idCust=${idCust}&name=${name}&passwordCust=${passwordCust}`,				
			function(data) {				
				alert(data);		
			});				
}

function removeCustomer() {
	const idRemoveCust = document.querySelector('#idRemoveCust').value;		
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/remove_cust?idRemoveCust=${idRemoveCust}`,				
			function(data) {			
				alert(data);
			});				
}

function updateCustomer() {
	const idUp = document.querySelector('#idUp').value;
	const password1 = document.querySelector('#password1').value;			
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/update_cust?idUp=${idUp}&password1=${password1}`,				
			function(data) {				
				alert(data);		
			});				
}

function getCustomer() {
	const idGetCust = document.querySelector('#idGetCust').value;		
		
	$.getJSON(`http://localhost:8080/Project_Part_B/rest/admin/get_cust?idGetCust=${idGetCust}`,				
			function(data) {			
				alert(data);
			});				
}
