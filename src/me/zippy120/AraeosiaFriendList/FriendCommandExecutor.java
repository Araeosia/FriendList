package me.zippy120.AraeosiaFriendList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendCommandExecutor implements CommandExecutor {

	AraeosiaFriendList plugin;
	
	public FriendCommandExecutor(AraeosiaFriendList plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("friend")){
			if(args[0].equalsIgnoreCase("add")){
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length == 3){
						
					} else plugin.error("Incorrect amount of arguements!", player);
				}
			}
		}
		return false;
	}

}
