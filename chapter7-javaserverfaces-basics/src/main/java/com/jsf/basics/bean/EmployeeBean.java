package com.jsf.basics.bean;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


import com.jsf.basics.db.dao.EmployeeDAO;
import com.jsf.basics.db.dao.model.Employee;
import com.jsf.basics.db.service.EmployeeService;

@ManagedBean(name="employeeBean", eager = true)
@ApplicationScoped
public class EmployeeBean {

	private Long employeeID = 0L;
	
	private Set<Employee> employees;
	
	private Set<Employee> gridEmployees;
	
	private Employee selectedEmployee;
	
	private Employee newEmployee;
	
	

	private EmployeeService employeeService;
	
	
	public EmployeeBean() {
		newEmployee = new Employee();
		employeeService = new EmployeeService(new EmployeeDAO());
	}
	
	@PostConstruct
	public void init() {
		
		if(employees == null) {
			employees = employeeService.findAll();
			setGridEmployees(employees);
		}
	}
	
	public void search(Long employeeId) {
		
		String message = "Employees not found!";
		Severity severityMessage = FacesMessage.SEVERITY_WARN;
		
		if(employeeId != null && employeeId > 0) {
			
			Employee employee = employeeService.findById(employeeId);
			if(employee != null) {
				Set<Employee> employeeSet = new HashSet<Employee>();
				employeeSet.add(employee);
				setGridEmployees(employeeSet);
				message = "Employees found!";
				severityMessage = FacesMessage.SEVERITY_INFO;
			}
		}
		else {
			employees = employeeService.findAll();
			setGridEmployees(employees);
			
			if(employees.size() > 0) {
				message = "Employees found!";
				severityMessage = FacesMessage.SEVERITY_INFO;
			}
		}
		
		FacesMessage facesMessage = new FacesMessage(severityMessage, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void selectEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
	
	public void save() {
		
		if(newEmployee != null) {
			
			employeeService.save(newEmployee);
			clearNewEmployee();
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı kayıt işlemi başarılı!", null);
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		else {
			
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Kullanıcı kayıt işlemi başarısız!", null);
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}

	private void clearNewEmployee() {
		
		this.newEmployee.setName("");
		this.newEmployee.setLastName("");
		this.newEmployee.setGender("");
		this.newEmployee.setBirthDate(null);
		this.newEmployee.setHireDate(null);
	}
	
	public Long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Set<Employee> getGridEmployees() {
		return gridEmployees;
	}

	public void setGridEmployees(Set<Employee> gridEmployees) {
		this.gridEmployees = gridEmployees;
	}
	
	public Employee getNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(Employee newEmployee) {
		this.newEmployee = newEmployee;
	}
}
