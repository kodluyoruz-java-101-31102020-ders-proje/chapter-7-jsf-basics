package com.jsf.basics.db.service;

import java.util.Set;

import com.jsf.basics.db.dao.EmployeeDAO;
import com.jsf.basics.db.dao.model.Employee;


public class EmployeeService {

	private EmployeeDAO employeeDAO;
	
	public EmployeeService(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public Set<Employee> findAll() {
		
		return this.employeeDAO.getAll();
	}
	
	public Employee findById(Long empNo) {
		
		return this.employeeDAO.findEmployeeById(empNo);
	}
	
	public Employee save(Employee employee) {
		
		 Long maxEmployeeId = this.employeeDAO.getMaxId();
		 employee.setId(maxEmployeeId + 1);
		 return this.employeeDAO.save(employee);
	}
}
