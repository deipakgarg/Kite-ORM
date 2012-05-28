package com.kite.orm.test;


import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BasicDataSource ds = (BasicDataSource)new ClassPathXmlApplicationContext("spring/applicationContext.xml").getBean("wileyDataSource");
		//ICommonDao dao = (CommonDao)new ClassPathXmlApplicationContext("spring/applicationContext.xml").getBean("ICommonDao");
		ICommonDao dao = new CommonDao();
		dao.setDataSource(ds);
		Template t = new Template();
		// t.setTemplateId(1);
		t.setName("template_New");
		t.setType("SYSTEM");
		t.setTitle("Title_New");
		t.setDescription("Description_MySingle");
		t.setCategory("SINGLE");
		t.setCreatedBy("DpakGarg");
		t.setUpdatedBy("DpakGarg");
		
		
		dao.insert(t);
		// t.setTemplateId(6);
		// dao.read(t);
		// System.out.println(t.toString());
		// t.setName("template_Update");
		// t.setTitle("Title_Update");
		// t.setDescription("Description_Update");
		// dao.update(t);
		System.out.println(t.toString());
		// dao.remove(t);
		List<Template> list = dao.read(t, "1=1");
		for (Template template : list)
		{
			System.out.println("*********************************");
			System.out.println(template.toString());
		}
	}
	
}
