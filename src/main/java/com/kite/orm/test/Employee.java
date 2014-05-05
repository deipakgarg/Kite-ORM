package com.kite.orm.test;


import java.util.Date;

import com.kite.orm.annotation.Column;
import com.kite.orm.annotation.Keys;
import com.kite.orm.annotation.PrimaryKey;
import com.kite.orm.annotation.Table;
import com.kite.orm.annotation.UniqueKey;


@Table(name = "EMPLOYEE")
@PrimaryKey(key = "empId", autoIncrement = true)
@Keys(
		uniqueKey = {
				@UniqueKey(keys = {"panCode"}),
				@UniqueKey(keys = {"name", "designation", "grade"})})
public class Employee
{
	@Column(name = "EMP_ID")
	private Integer empId;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "PAN_CODE", nullable = false)
	private String panCode;
	
	@Column(name = "DESIGNATION", nullable = false)
	private String designation;
	
	@Column(name = "GRADE")
	private String grade;
	
	@Column(name = "SALARY")
	private Double salary;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate = new Date();
	
	
	public Integer getEmpId()
	{
		return empId;
	}
	
	
	public void setEmpId(Integer empId)
	{
		this.empId = empId;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	public String getPanCode()
	{
		return panCode;
	}
	
	
	public void setPanCode(String code)
	{
		this.panCode = code;
	}
	
	
	public String getDesignation()
	{
		return designation;
	}
	
	
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	
	
	public String getGrade()
	{
		return grade;
	}
	
	
	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	
	
	public Double getSalary()
	{
		return salary;
	}
	
	
	public void setSalary(Double salary)
	{
		this.salary = salary;
	}
	
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	
	
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
	
	public Date getUpdatedDate()
	{
		return updatedDate;
	}
	
	
	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append("empId=" + empId);
		ret.append(", name=" + name);
		ret.append(", panCode=" + panCode);
		ret.append(", designation=" + designation);
		ret.append(", grade=" + grade);
		ret.append(", salary=" + salary);
		ret.append(", createdBy=" + createdBy);
		ret.append(", createdDate=" + createdDate);
		ret.append(", updatedBy=" + updatedBy);
		ret.append(", updatedDate=" + updatedDate);
		return ret.toString();
	}
}
