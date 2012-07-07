package me.zippy120.AraeosiaFriendList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendCommandExecutor implements CommandExecutor {

	AraeosiaFriendList plugin;
	Database db;
	int id = 1;
	
	public FriendCommandExecutor(AraeosiaFriendList plugin, Database db) {
		this.plugin = plugin;
		this.db = db;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("friend")){
			if(args[0].equalsIgnoreCase("add")){
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length == 3){
						String friend = args[1];
						try {
							if (isNotBlocked(player, friend) || notAlreadyFriends(player, friend))
									addRequest(player, friend);
								} catch (Exception e) {e.printStackTrace();}
						return true;
					} else plugin.error("Incorrect amount of arguements!", player);
				} else plugin.log("You must be a player to execute that command!");
			} else if (args[0].equalsIgnoreCase("accept")){
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length == 3){
						String friend = args[1];
						try {
							if(requestPending(player, friend))
								acceptRequest(player, friend);
							else plugin.error("This player has not sent you a friend request. Try /friend add " + friend + " to send them one.", player);
						} catch (Exception e) {e.printStackTrace();}
					} else plugin.error("Incorrect amount of arguements!", player);
				} else plugin.log("You must be a player to execute that command!");
			} else return false;
		} else return false;
		return true;
	}

	private boolean isNotBlocked(Player player, String friend) {
		// TODO Auto-generated method stub
		return false;
	}

	private void acceptRequest(Player player, String friend) throws Exception{
		int lastId = 0;
		if (plugin.conn == null || plugin.conn.isClosed()) plugin.dbConnect();
        PreparedStatement s = plugin.conn.prepareStatement ("SELECT username FROM friends WHERE sender = ? AND recipient = ? AND status = ?");
        s.setString(1, player.getDisplayName());
        s.setString(2, friend);
        s.setInt(3, 0);
		s.executeQuery();
		ResultSet trs = s.getResultSet();
		while(trs.next()){
			lastId = trs.getInt("id");
		}
		trs.close();
		s.close();
        s = plugin.conn.prepareStatement ("DELETE FROM friends WHERE sender = ? AND recipient = ? AND status = ?");
        s.setString(1, player.getDisplayName());
		s.setString(2, friend);
		s.setInt(3, 0);
		s.executeUpdate();
		s.close();
		s = plugin.conn.prepareStatement ("INSERT INTO friends (id,sender,recipient,status) VALUES (?,?,?,?)");
		s.setInt(1, lastId);
        s.setString(2, player.getDisplayName());
		s.setString(3, friend);
		s.setInt(4, 1);
		s.executeUpdate();
		s.close();
        plugin.conn.close();
		
	}

	private boolean requestPending(Player player, String friend) throws Exception {
		if (plugin.conn == null || plugin.conn.isClosed()) plugin.dbConnect();
        PreparedStatement s = plugin.conn.prepareStatement ("SELECT username FROM friends WHERE sender = ? AND recipient = ? AND status = ?");
        s.setString(1, player.getDisplayName());
        s.setString(2, friend);
        s.setInt(3, 0);
		s.executeQuery();
		ResultSet trs = s.getResultSet();
		if(trs.next()){
			return false;
		}
		trs.close();
		
		s.close();
        plugin.conn.close();
		return true;
	}

	private boolean notAlreadyFriends(Player player, String friend) throws Exception {
		if (plugin.conn == null || plugin.conn.isClosed()) plugin.dbConnect();
        PreparedStatement s = plugin.conn.prepareStatement ("SELECT username FROM friends WHERE sender = ? AND recipient = ?");
        s.setString(1, player.getDisplayName());
        s.setString(2, friend);
		s.executeQuery();
		ResultSet trs = s.getResultSet();
		if(trs.next()){
			return false;
		}
		trs.close();
		s.close();
        plugin.conn.close();
		return true;
	}

	private void addRequest(Player player, String friend) throws Exception{
				if (plugin.conn == null || plugin.conn.isClosed()) plugin.dbConnect();
	            PreparedStatement s = plugin.conn.prepareStatement ("INSERT INTO friends (id,sender,recipient,status) VALUES (?,?,?,?)");
	            s.setInt(1, id);
	            s.setString(2, player.getDisplayName());
	    		s.setString(3, friend);
	    		s.setInt(4, 0);
	    		s.executeUpdate();
	    		s.close();
	            plugin.conn.close();
	            id++;
	}
		
} 