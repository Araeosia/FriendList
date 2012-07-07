package me.zippy120.AraeosiaFriendList;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AraeosiaFriendList extends JavaPlugin{
	
	public java.sql.Connection conn;
	Logger log;
	
	public void onEnable(){
		
		this.log("Enabling Plugin...");
		this.dbConnect();
		Database db = new Database(this);
		CommandExecutor Executor = new FriendCommandExecutor(this, db);
		getCommand("basic").setExecutor(Executor);
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
		this.log("Plugin enabled!");
	}

	protected void log(String string) {
		log.info("[FriendList]: " + string);
		
	}
	
	public void dbConnect(){
    	
        java.util.Properties conProperties = new java.util.Properties();
        conProperties.put("user", this.getConfig().getString("mysql.username") );
        conProperties.put("password",this.getConfig().getString("mysql.password") );
        conProperties.put("autoReconnect", "true");
        conProperties.put("maxReconnects", "3");
        String uri = "jdbc:mysql://"+this.getConfig().getString("mysql.hostname")+":"+this.getConfig().getString("mysql.port")+"/"+this.getConfig().getString("mysql.database");
        
        try {
        	conn = DriverManager.getConnection(uri, conProperties);
        } catch (SQLException e) {
            log.throwing("me.botsko.dhmcstats", "dbc()", e);
        }
    }

	public void error(String string, Player player) {
		player.sendMessage(ChatColor.RED + string);
		
	}
	
	public void Message(String string, Player player) {
		player.sendMessage(ChatColor.GREEN + string);
		
	}
}
