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
			final Block block = lastLoc.getBlock();
			
			//chest related
			block.setType(Material.CHEST);
			BlockState state = block.getState();
			final Chest chest = (Chest)state;
			
			Location chestLocation = chest.getBlock().getLocation();
			Location newLocation = chestLocation.add(0.0, 0.0, 1.0);
			
			//-----------------------------------------------------------
			final Block signBlock = newLocation.getBlock();
			signBlock.setTypeIdAndData(68, (byte)0x3, false);
			BlockState signState = signBlock.getState();
			final Sign sign = (Sign)signState;
			//-----------------------------------------------------------
			
			
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
				/*
				//sign related
				Location chestLocation = chest.getBlock().getLocation();
				Location newLocation = chestLocation.add(0.0, 0.0, 1.0);
				
				//-----------------------------------------------------------
				Block signBlock = newLocation.getBlock();
				signBlock.setTypeIdAndData(68, (byte)0x3, false);
				BlockState signState = signBlock.getState();
				Sign sign = (Sign)signState;
				//-----------------------------------------------------------
				*/
				if(this.plugin.SignLockette_Enabled == true)
				{
					if(this.plugin.SignLockette_PrivateDefault == true)
					{
						
						sign.setLine(0, "[Private]");
						sign.setLine(1, player.getDisplayName());
						sign.update();
					}
					else
					{
						
						sign.setLine(0, "[Everyone]");
						sign.setLine(1, player.getDisplayName());
						sign.update();
					}
				}
				else
				{
					sign.setLine(0, player.getDisplayName()+"'s");
					sign.setLine(1, "Deathpile");
					sign.update();
				}
				/*
				if(this.plugin.ChestDeleteIntervalEnabled == true)
				{
					int delay = this.plugin.ChestDeleteInterval;
					
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

					    public void run() 
					    {
					    	signBlock.setType(Material.AIR);
					    }
					}, delay);
				}
				*/
				
			}
			//LOL just MCGYVERING UP A QUICK FUNCTION FOR TESTING, DONT FUSS AT ME
			if(this.plugin.Sign_BeaconEnabled == true)
			{
				int height = this.plugin.Sign_BeaconHeight;
				
				if(height == 10)
				{
					Location chestLocation1 = block.getLocation();
					
					Location up = chestLocation1.add(0.0, 2.0, 0.0);
					final Block upBlock = up.getBlock();
					upBlock.setType(Material.GLOWSTONE);
					
					Location up2 = up.add(0.0, 1.0, 0.0);
					final Block upBlock2 = up2.getBlock();
					upBlock2.setType(Material.GLOWSTONE);
					
					Location up3 = up2.add(0.0, 1.0, 0.0);
					final Block upBlock3 = up3.getBlock();
					upBlock3.setType(Material.GLOWSTONE);
					
					Location up4 = up3.add(0.0, 1.0, 0.0);
					final Block upBlock4 = up4.getBlock();
					upBlock4.setType(Material.GLOWSTONE);
					
					Location up5 = up4.add(0.0, 1.0, 0.0);
					final Block upBlock5 = up5.getBlock();
					upBlock5.setType(Material.GLOWSTONE);
					
					Location up6 = up5.add(0.0, 1.0, 0.0);
					final Block upBlock6 = up6.getBlock();
					upBlock6.setType(Material.GLOWSTONE);
					
					Location up7 = up6.add(0.0, 1.0, 0.0);
					final Block upBlock7 = up7.getBlock();
					upBlock7.setType(Material.GLOWSTONE);
					
					Location up8 = up7.add(0.0, 1.0, 0.0);
					final Block upBlock8 = up8.getBlock();
					upBlock8.setType(Material.GLOWSTONE);
					
					Location up9 = up8.add(0.0, 1.0, 0.0);
					final Block upBlock9 = up9.getBlock();
					upBlock9.setType(Material.GLOWSTONE);
					
					Location up10 = up9.add(0.0, 1.0, 0.0);
					final Block upBlock10 = up10.getBlock();
					upBlock10.setType(Material.GLOWSTONE);
					
					if(this.plugin.ChestDeleteIntervalEnabled == true)
					{
						int delay = this.plugin.ChestDeleteInterval*20;
						this.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {

							@Override
							public void run()
							{
								upBlock.setType(Material.AIR);
								upBlock2.setType(Material.AIR);
								upBlock3.setType(Material.AIR);
								upBlock4.setType(Material.AIR);
								upBlock5.setType(Material.AIR);
								upBlock6.setType(Material.AIR);
								upBlock7.setType(Material.AIR);
								upBlock8.setType(Material.AIR);
								upBlock9.setType(Material.AIR);
								upBlock10.setType(Material.AIR);
		
							}}, delay);
					}
				}
			}
			
			if(this.plugin.ChestDeleteIntervalEnabled == true)
			{
				int delay = this.plugin.ChestDeleteInterval*20;
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){

					@Override
					public void run()
					{
						block.setType(Material.AIR);
						signBlock.setType(Material.AIR);
						
					}}, delay);
				
			}
	
		}
	}
}
