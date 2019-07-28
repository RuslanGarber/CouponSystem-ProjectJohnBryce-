package com.web.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.web.model.Income;


public class BussinessDelegate {

	//jersey 2 client API
	
	private static final String URL = "http://localhost:8888/api";
	
	private Client client = ClientBuilder.newClient();
	
	public Response storeIncome(Income income) {
		return client
				.target(URL)
				.path("/income")
				.request(MediaType.APPLICATION_XML)
				.post(Entity.entity(income, MediaType.APPLICATION_XML));
	}
	
	public List<Income> getAllIncome() {
		Response serviceResponse = client
					.target(URL)
					.path("/allincome")
					.request(MediaType.APPLICATION_JSON)
					.get(Response.class);
		
	//	List<Income> incomes = serviceResponse.readEntity(new GenericType<List<Income>>(){});
		 
		String incomesStr = serviceResponse.readEntity(String.class);
		List <Income> incomes = new Gson().fromJson(incomesStr, new TypeToken<ArrayList<Income>>() {}.getType()); 
		return incomes;
	}
	
	public List<Income> getAllIncomeByCompany(String companyName) {
		Response serviceResponse = client
					.target(URL+"/allincome_Company?COMP_NAME=" + companyName)
					//.path("/allincome_Company")
					//.queryParam(companyName)
					.request(MediaType.APPLICATION_JSON)
					.get(Response.class);
		
		// List<Income> incomes = serviceResponse.readEntity(new GenericType<List<Income>>(){});
		
		String incomesByCompanyStr = serviceResponse.readEntity(String.class); 
		List <Income> incomesByCompany = new Gson().fromJson(incomesByCompanyStr, new TypeToken<ArrayList<Income>>() {}.getType());
		
		 
		return incomesByCompany;
	}
	
	public List<Income> getAllIncomeByCustomer(String customerName) {
		Response serviceResponse = client
					.target(URL)
					.path("/allincome_Customer/" + customerName)
					.request(MediaType.APPLICATION_JSON)
					.get(Response.class);
		
		// List<Income> incomes = serviceResponse.readEntity(new GenericType<List<Income>>(){});
		
		String incomesByCustomerStr = serviceResponse.readEntity(String.class); 
		List <Income> incomesByCustomer = new Gson().fromJson(incomesByCustomerStr, new TypeToken<ArrayList<Income>>() {}.getType());
		return incomesByCustomer;
	}
			
}
