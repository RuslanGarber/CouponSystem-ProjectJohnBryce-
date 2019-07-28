package com.couponSys.couponsystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.couponSys.couponsystem.model.Income;
import com.couponSys.couponsystem.repository.IncomeRepository;


@RestController
@RequestMapping("/api")
public class IncomeController {

		@Autowired
		IncomeRepository IncomeRepository;
		
		
		@PostMapping("/income")
		public Income addIncome(@Valid @RequestBody Income income) {
			return IncomeRepository.save(income);
		}
		
		@GetMapping("/allincome")
		public List<Income> viewAllIncome() {
			return IncomeRepository.findAll();
		}
		
		
		@GetMapping("/allincome_Customer/{CUST_NAME}")
		public List<Income> viewIncomeByCustomer(@PathVariable(value="CUST_NAME") String CUST_NAME) {
			List<Income> allincome = IncomeRepository.findAll();
			List<Income> customerIncome = new ArrayList<Income>();
			
			for(Income i:allincome) {
				if(i.getName().equals(CUST_NAME)) {
					customerIncome.add(i);
				}
			}
			return customerIncome;
		}
		
		@GetMapping("/allincome_Company")
		public List<Income> viewIncomeByCompany(@RequestParam("COMP_NAME") String COMP_NAME) {
			List<Income> allincome = IncomeRepository.findAll();
			List<Income> companyIncome = new ArrayList<Income>();
			
			for(Income i:allincome) {
				if(i.getName().equals(COMP_NAME)) {
					companyIncome.add(i);
				}
			}
			return companyIncome;
		}

}
