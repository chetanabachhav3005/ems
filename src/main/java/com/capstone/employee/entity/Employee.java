package com.capstone.employee.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

	@Id
	private int EmployeeID;
	private String EmployeeName;
	private Date DateOfBirth;

}
