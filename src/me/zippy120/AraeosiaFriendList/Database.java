package me.zippy120.AraeosiaFriendList;

import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private AraeosiaFriendList plugin;
	
	public Database(AraeosiaFriendList plugin){
		this.plugin = plugin;
	}
	public void dbCreate() throws SQLException{
		Statement stmnt = plugin.conn.createStatement();
		stmnt.execute("CREATE TABLE IF NOT EXISTS friends (id INTEGER NOT NULL AUTO_INCREMENT, sender varchar(254), recipient varchar(254), status INTEGER);");
		if (stmnt != null)
			stmnt.close();
		plugin.conn.close();
	}
}
