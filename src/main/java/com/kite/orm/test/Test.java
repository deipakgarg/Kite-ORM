package com.kite.orm.test;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test
{
	private static CommonDao dao;
	private static Logger log = Logger.getLogger(Test.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		dao = (CommonDao)new ClassPathXmlApplicationContext("spring/applicationContext.xml").getBean("CommonDao");
		
		addEmployee();
		appraisal();
		displayEmployee(null);
		Employee e1 = new Employee();
		e1.setPanCode("ABCD1234"); // Based of First Unique Key
		// Based of First Unique Key (PanCode)
		e1 = dao.read(e1);
		log.info("Employee Details are : " + e1.toString()); // Shows record of
		Employee e2 = new Employee();
		e2.setName("Krishna Gaur");
		e2.setDesignation("SE");
		e2.setGrade("C");
		// Based of Second Unique Key (Combination of 3 Columns: Name, Designation, Grade)
		e2 = dao.read(e2);
		log.info("Employee Details are : " + e2.toString()); // Shows record of
		removeEmployee();
	}
	
	private static void addEmployee()
	{
		Employee e = new Employee();
		
		e.setName("Deepak Garg");
		e.setPanCode("ABCD1234");
		e.setDesignation("Tech Lead");
		e.setGrade("A");
		e.setSalary(1500.00);
		e.setCreatedBy("Admin");
		e.setUpdatedBy("Admin");
		
		e.setEmpId(dao.insert(e));
		
		log.info(e.toString());
		log.info("Record Inserted");
		
		e = new Employee();
		e.setName("Prateek Dhall");
		e.setPanCode("ABCD2345");
		e.setDesignation("SSE");
		e.setGrade("B");
		e.setSalary(1200.00);
		e.setCreatedBy("Admin");
		e.setUpdatedBy("Admin");
		
		e.setEmpId(dao.insert(e));
		log.info(e.toString());
		log.info("Record Inserted");
		
		e = new Employee();
		e.setName("Krishna Gaur");
		e.setPanCode("ABCD3456");
		e.setDesignation("JSE");
		e.setGrade("C");
		e.setSalary(900.00);
		e.setCreatedBy("Admin");
		e.setUpdatedBy("Admin");
		
		e.setEmpId(dao.insert(e));
		log.info(e.toString());
		log.info("Record Inserted");
		
		e = new Employee();
		e.setName("Krishna Gaur");
		e.setPanCode("ABCD4456");
		e.setDesignation("SE");
		e.setGrade("C");
		// e.setSalary(950.00);
		e.setCreatedBy("Admin");
		e.setUpdatedBy("Admin");
		
		e.setEmpId(dao.insert(e));
		log.info(e.toString());
		log.info("Record Inserted");
		
		log.info("********* Insertion Complete");
		
	}
	
	private static void appraisal()
	{
		List<Employee> list = dao.getList(Employee.class, null);
		Double salary = 0.00;
		
		for (Employee employee : list)
		{
			if (null == employee.getSalary())
			{
				employee.setSalary(1000.00);
			}
			salary = employee.getSalary();
			if (employee.getGrade().equals("A"))
			{
				employee.setSalary(salary + (salary * 0.25));
			}
			else if (employee.getGrade().equals("B"))
			{
				employee.setSalary(salary + (salary * 0.4));
			}
			else if (employee.getGrade().equals("C"))
			{
				employee.setSalary(salary + (salary * 0.5));
			}
			dao.update(employee);
		}
	}
	
	private static void displayEmployee(final String strWhereClause)
	{
		List<Employee> list = dao.getList(Employee.class, strWhereClause);
		
		for (Employee employee : list)
		{
			log.info(employee.toString());
		}
	}
	
	private static void removeEmployee()
	{
		List<Employee> list = dao.getList(Employee.class, null);
		
		for (Employee employee : list)
		{
			dao.remove(employee);
			log.info("Employee " + employee.getName() + " get deleted.");
		}
	}
}
