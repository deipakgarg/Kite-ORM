package com.kite.orm.util;


import java.lang.reflect.Field;

import org.springframework.dao.DataAccessException;


/**
 * Utility Class related to Reflection Methods
 * @author Deepak Garg
 *
 */
public class ReflectionUtils
{
	/**
	 * @param object
	 * @param strFieldName
	 * @return T value of field
	 * @throws DataAccessException
	 */
	@SuppressWarnings({"serial", "unchecked"})
	public static <T extends Object> T getFieldValue(T object, String strFieldName) throws DataAccessException
	{
		Field f;
		try
		{
			f = object.getClass().getDeclaredField(strFieldName);
			f.setAccessible(true);
			return (T)f.get(object);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
			throw new DataAccessException("SecurityException", e)
				{
				};
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
			throw new DataAccessException("NoSuchFieldException", e)
				{
				};
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			throw new DataAccessException("IllegalArgumentException", e)
				{
				};
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			throw new DataAccessException("IllegalAccessException", e)
				{
				};
		}
	}
}
