package com.wesnc.playerchestdeath;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

public class EntLis extends EntityListener
{
	public ChestDeath plugin;
	
	public EntLis(ChestDeath instance)
	{
		plugin = instance;
	}
	
	public void onEntityDeath(EntityDeathEvent event)
	{
		Entity entity = event.getEntity();
		
		if(entity instanceof Player)
		{
			Player player = (Player)entity;
			Location lastLoc = player.getLocation();
			Block block = lastLoc.getBlock();
			//ItemStack[] inv = player.getInventory().getContents();
			//EntityDamageEvent e = player.getLastDamageCause();
			
			//chest related
			block.setType(Material.CHEST);
			BlockState state = block.getState();
			Chest chest = (Chest)state;
			
			
			for(ItemStack item : event.getDrops())
			{
				if(item == null) continue;
				
				chest.getInventory().addItem(item);
			}
			
			if(this.plugin.drops == false)
			{
				
				event.getDrops().clear();
			}
			
			if(this.plugin.deathMessage == true)
			{
				this.plugin.getServer().broadcastMessage(ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + " " + this.plugin.deathMessageString);
			}
			
			if(this.plugin.SignOnChest == true)
			{
				//sign related
				Location chestLocation = chest.getBlock().getLocation();
				Location newLocation = chestLocation.add(0.0, 0.0, 1.0);
				
				Block signBlock = newLocation.getBlock();
				signBlock.setTypeIdAndData(68, (byte)0x3, false);
				BlockState signState = signBlock.getState();
				Sign sign = (Sign)signState;
					
				//sign.setLine(0, player.getDisplayName()+"'s");
				//sign.setLine(1, "Deathpile");
				if(this.plugin.SignLockette_Enabled == true)
				{
					if(this.plugin.SignLockette_PrivateDefault == true)
					{
						//63
						
						Block SignBlockPost = newLocation.getBlock();
						SignBlockPost.setTypeIdAndData(68, (byte)0x3, false);
						BlockState signBlockState = SignBlockPost.getState();
						Sign signPost = (Sign)signBlockState;
						
						signPost.setLine(0, "[Private]");
						signPost.setLine(1, player.getDisplayName());
						signPost.update();
					}
					else
					{
						Block SignBlockPost = newLocation.getBlock();
						SignBlockPost.setTypeIdAndData(68, (byte)0x3, false);
						BlockState signBlockState = SignBlockPost.getState();
						Sign signPost = (Sign)signBlockState;
						
						signPost.setLine(0, "[Everyone]");
						signPost.setLine(1, player.getDisplayName());
						signPost.update();
					}
				}
				else
				{
					sign.setLine(0, player.getDisplayName()+"'s");
					sign.setLine(1, "Deathpile");
					sign.update();
				}
				
			}
	
		}
	}
}
