package com.jsf.basics.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.jsf.basics.db.DbSQLQuery;
import com.jsf.basics.db.dao.model.Employee;

public class EmployeeDAO {

	private static final Logger logger = Logger.getLogger(EmployeeDAO.class);

	public Set<Employee> getAll() {

		Set<Employee> employees = new TreeSet<Employee>();
		ResultSet resultSet = DbSQLQuery.select("SELECT * FROM employees");

		try {

			if (resultSet == null) {
				return employees;
			}

			while (resultSet.next()) {
				employees.add(createEmployee(resultSet));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return employees;
	}

	public Employee findEmployeeById(Long empNo) {

		String sql = "SELECT * FROM employees WHERE emp_no=" + empNo;
		ResultSet resultSet = DbSQLQuery.select(sql);

		try {

			if (resultSet == null) {
				return null;
			}

			while (resultSet.next()) {
				return createEmployee(resultSet);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public Employee save(Employee employee) {
		
		String sql = "INSERT INTO employees (emp_no, first_name, last_name, gender, birth_date, hire_date) VALUES(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = DbSQLQuery.createPreparedStatement(sql);
		
		try {
			preparedStatement.setLong(1, employee.getId());
			preparedStatement.setString(2, employee.getName());
			preparedStatement.setString(3, employee.getLastName());
			preparedStatement.setString(4, employee.getGender());
			preparedStatement.setDate(5, new java.sql.Date(employee.getBirthDate().getTime()));
			preparedStatement.setDate(6, new java.sql.Date(employee.getHireDate().getTime()));
			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return employee;
	}
	
	public Long getMaxId() {
		
		ResultSet resultSet = DbSQLQuery.select("SELECT MAX(emp_no) FROM employees");
		try {
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0L;
	}
	

	private Employee createEmployee(ResultSet resultSet) throws SQLException {

		Employee employee = new Employee();

		employee.setId(resultSet.getLong("emp_no"));
		employee.setName(resultSet.getString("first_name"));
		employee.setLastName(resultSet.getString("last_name"));
		employee.setGender(resultSet.getString("gender"));
		employee.setBirthDate(resultSet.getDate("birth_date"));
		employee.setHireDate(resultSet.getDate("hire_date"));

		return employee;
	}

}
