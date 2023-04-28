package com.capstone.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.employee.entity.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}
