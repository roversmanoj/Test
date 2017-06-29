/**
 * 
 */
package com.proect.metadata.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import com.project.metadata.common.GetMetadata;
import com.project.metadata.connection.ConnectDatabase;

/**
 * @author Mano
 *
 */
public class RetriveMetadata implements GetMetadata {

	ConnectDatabase connectDatabase = null;
	Connection connection = null;
	ResultSet resultSet = null;
	DatabaseMetaData meta = null;

	public String getPrimaryKey(String tableName) {
		String primaryKey = null;

		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			meta = connection.getMetaData();
			resultSet = meta.getPrimaryKeys(null, null, tableName);
			while (resultSet.next()) {
				primaryKey = resultSet.getString(4);
				System.out.println("Primary key of " + tableName + " = " + primaryKey);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return primaryKey;
	}

	public Set<String> getForeignKeys(String tableName) {
		// TODO Auto-generated method stub
		Set<String> foreignKeys = new HashSet<String>();

		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			meta = connection.getMetaData();
			resultSet = meta.getImportedKeys(null, null, tableName);
			meta.getColumns(null, null, tableName, null);
			while (resultSet.next()) {
				String fKey = resultSet.getString("FKCOLUMN_NAME");
				System.out.println("Foreign key of " + tableName + " = " + fKey);
				foreignKeys.add(fKey);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foreignKeys;
	}

	public Set<String> getViews() {
		Set<String> views = new HashSet<String>();
		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			meta = connection.getMetaData();
			resultSet = meta.getTables(null, null, null, new String[] { "VIEW" });
			while (resultSet.next()) {
				String view = resultSet.getString(3);
				System.out.println("Views of DB = " + view);
				views.add(view);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return views;

	}

	
	public Set<String> getTriggers() {
		Set<String> triggers = new HashSet<String>();
		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			Statement statement = connection.createStatement();
			String query = "select TRIGGER_NAME from INFORMATION_SCHEMA.TRIGGERS;";
			resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				String trigger = resultSet.getString("TRIGGER_NAME");
				System.out.println("Triggers:"+resultSet.getString("TRIGGER_NAME"));
				triggers.add(trigger);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return triggers;
	}
	

	public Set<String> getIndexes() {
		Set<String> indexes = new HashSet<String>();
		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			meta = connection.getMetaData();
			for (String table : getTables(meta)) {
				resultSet = meta.getIndexInfo(null, null, table, false, false);
				while (resultSet.next()) {
					String index = resultSet.getString("INDEX_NAME");
					System.out.println("Index of " + table + " = " + resultSet.getString("INDEX_NAME"));
					indexes.add(index);
				}

			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return indexes;

	}

	public Set<String> getTableTypes() {
		Set<String> types = new HashSet<String>();
		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			meta = connection.getMetaData();
			resultSet = meta.getTableTypes();
			while (resultSet.next()) {
				String type = resultSet.getString(1);
				System.out.println("Table Type = " + type);
				types.add(type);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return types;

	}

	public Set<String> getTables(DatabaseMetaData metaData) {
		Set<String> tableNames = new HashSet<String>();
		try {
			resultSet = metaData.getTables(null, null, null, null);
			while (resultSet.next()) {
				String tableName = resultSet.getString(3);
				System.out.println("Table  = " + tableName);
				tableNames.add(tableName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;

	}
	


	public Set<String> getAutoIncrementField(String tableName) {
		Set<String> autoFields = new HashSet<String>();
		try {
			connectDatabase = new ConnectDatabase();
			connection = connectDatabase.getMySqlConnection();
			Statement statement = connection.createStatement();
			String query = "select * from "+tableName+";";
			resultSet = statement.executeQuery(query);
			ResultSetMetaData data = resultSet.getMetaData();
			int noOfColumns = data.getColumnCount();
			for(int i=1;i<noOfColumns;i++){
				if(data.isAutoIncrement(i)){
					 String columnName = data.getColumnName(i);
					 System.out.println("Auto Increment field:"+columnName);
					 autoFields.add(columnName);
				}
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autoFields;

	}
	
	

	public static void main(String[] args) {
		RetriveMetadata metadata = new RetriveMetadata();
		metadata.getPrimaryKey("address");
		metadata.getForeignKeys("film_actor");
		metadata.getAutoIncrementField("address");
		metadata.getIndexes();
		metadata.getTriggers();
		metadata.getViews();
		metadata.getTableTypes();
		
	}

}
