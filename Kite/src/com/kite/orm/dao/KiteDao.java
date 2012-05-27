package com.kite.orm.dao;


import org.springframework.dao.DataAccessException;


/**
 * The super class of Data Access Objects generated through the kite framework. 
 * This class offers utility routines for quickly invoking queries and manipulating persistence objects.
 * @author Deepak Garg
 *
 * @param <T>
 */
public interface KiteDao<T>
{
	/**
	 * Persist (Create New Object) the specified object through the kite framework.
	 * 
	 * @param <T> the type for the any Entity to be persisted
	 * @param object of the Entity to be persisted
	 * @return the persisted Entity
	 * @throws DataAccessException
	 */
	public int insert(T object) throws DataAccessException;
	
	/**
	 * Persist (Modify Object) the specified object through the kite framework.
	 * 
	 * @param <T> the type for the any Entity to be persisted
	 * @param object of the Entity to be persisted
	 * @return the persisted Entity
	 * @throws DataAccessException
	 */
	public int update(T object) throws DataAccessException;
	
	/**
	 * Remove the specified Entity through the kite framework
	 * 
	 * @param <T> the type for the Entity to be removed
	 * @param object of the Entity to be removed
	 */
	public int remove(T object) throws DataAccessException;
	
	/**
	 * Retrieved Single Object based on Primary Key or combination of any Unique key.
	 * 
	 * @param <T> the type for the Entity to be returned
	 * @param object of the Entity to be retrieved
	 */
	public T read(T object) throws DataAccessException;
}
