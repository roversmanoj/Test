/**
 * Interface to retrieve the required metadata for the corresponding database
 * 
 */
package com.project.metadata.common;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author Manoj
 *
 */

public interface GetMetadata {
	//method to retrieve the primary key of the given table
	public String getPrimaryKey(String tableName);
	
	//method to retrieve the foreign key of the given table
	public Set<String> getForeignKeys(String tableName);
	
	//method to retrieve the views available in database
	public Set<String> getViews();
	
	//method to retrieve the triggers available in database
	public Set<String> getTriggers();
	
	//method to retrieve the indexes available in database
	public Set<String> getIndexes();

}
