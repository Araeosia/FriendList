package me.zippy120.AraeosiaFriendList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

	private AraeosiaFriendList plugin;
	public LoginListener(AraeosiaFriendList plugin){
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void playerLogin(PlayerLoginEvent event) throws SQLException{
		for(String sender : FriendRequests(event.getPlayer().getDisplayName()))
			plugin.Message("You have a pending friend request from " + sender + "!", event.getPlayer());
			
	}


	private String[] FriendRequests(String player) throws SQLException {
		ArrayList<String> Requests = new ArrayList<String>();
		try {
			if (plugin.conn == null || plugin.conn.isClosed()) plugin.dbConnect();
            PreparedStatement s = plugin.conn.prepareStatement ("SELECT sender FROM friends WHERE recipient = ? ORDER BY id");
            s.setString(1, player);
    		s.executeQuery();
    		ResultSet trs = s.getResultSet();
    		while(trs.next()){
    			Requests.add(trs.getString("sender"));
    		}
    		trs.close();
    		s.close();
            plugin.conn.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
		String[] requestArray = (String[]) Requests.toArray();
		return requestArray;
	}
}
