package com.kite.orm.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.kite.orm.dao.AbstractKiteDao;


/**
 * Utility Class related to Reflection Methods
 * @author Deepak Garg
 *
 */
public class ReflectionUtils
{
	private static Logger log = Logger.getLogger(AbstractKiteDao.class);

	private ReflectionUtils() 
	{
	}
	
	/**
	 * @param object
	 * @param strFieldName
	 * @return T value of field
	 * @throws DataAccessException
	 */
	@SuppressWarnings({"serial"})
	public static <T extends Object> T getFieldValue(T object, String strFieldName) throws DataAccessException
	{
// Field f;
		try
		{
// f = object.getClass().getDeclaredField(strFieldName);
// f.setAccessible(true);
// return (T)f.get(object);
			return getValue(object, strFieldName);
		}
		catch (SecurityException e)
		{
			log.debug(e.getMessage(), e);
			throw new DataAccessException("SecurityException", e)
				{
				};
		}
		catch (IllegalArgumentException e)
		{
			log.debug(e.getMessage(), e);
			throw new DataAccessException("IllegalArgumentException", e)
				{
				};
		}
		catch (IllegalAccessException e)
		{
			log.debug(e.getMessage(), e);
			throw new DataAccessException("IllegalAccessException", e)
				{
				};
		}
		catch (InvocationTargetException e)
		{
			log.debug(e.getMessage(), e);
			throw new DataAccessException("InvocationTargetException", e)
				{
				};
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Object> T getValue(T object, String strFieldName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Class<T> d = (Class<T>)object.getClass();
		T e = object;
		
		
		String[] fieldnames = strFieldName.split("\\.");
		for (int i = 0; i < fieldnames.length; i++)
		{
			String name = fieldnames[i];
			StringBuilder newName = new StringBuilder(name.length());
			for (int k = 0; k < name.length(); k++)
			{
				if (k == 0)
				{
					newName.append(Character.toUpperCase(name.charAt(k)));
				}
				else
				{
					newName.append(name.charAt(k));
				}
			}
			Method[] methods = d.getMethods();
			Method tobeinvoked = null;
			for (int j = 0; j < methods.length; j++)
			{
				if (("get" + newName.toString()).equals(methods[j].getName()))
				{
					tobeinvoked = methods[j];
					break;
				}
			}
			
			if (tobeinvoked == null)
			{
				return null;
			}
			
			e = (T)tobeinvoked.invoke(e, new Object[0]);
			
			if (e == null)
			{
				return null;
			}
			d = (Class<T>)e.getClass();
			
		}
		return e;
	}
}
