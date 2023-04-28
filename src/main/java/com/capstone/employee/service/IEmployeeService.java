package com.capstone.employee.service;

import java.util.List;

import com.capstone.employee.entity.Employee;
import com.capstone.employee.vo.EmployeeVO;



public interface IEmployeeService {
	
	public EmployeeVO getEmployeeById(int ID) throws Exception;
	
	public List<Employee> getEmployees();

}
