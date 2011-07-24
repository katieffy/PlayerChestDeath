package com.wesnc.playerchestdeath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yi.acru.bukkit.Lockette.Lockette;

public class ChestDeath extends JavaPlugin
{
	
	Logger logger = Logger.getLogger("Minecraft");
	
	public String mainDir = "plugins/PCD/";
	public File configFile = new File(mainDir+"PCDConfig.cfg");
	public Properties prop = new Properties();
	
	EntLis entityListener = new EntLis(this);

	public Lockette LockettePlugin = null;
	
	public String configFileHeader = 
		"Edit this file as needed.\n" +
		"Death Message must be true for the death message String to work!" +
		"Beacon Height are only 10 for now, until I figure out a better way of doing it" +
		"ChestDeleteInterval is in seconds.";
	
	public boolean drops;
	public boolean deathMessage;
	public String deathMessageString;
	public boolean SignOnChest;
	public boolean SignLockette_Enabled;
	public boolean SignLockette_PrivateDefault;
	public boolean Sign_BeaconEnabled;
	public int Sign_BeaconHeight;
	public int ChestDeleteInterval;
	public boolean ChestDeleteIntervalEnabled;
	
	@Override
	public void onDisable()
	{
		logger.log(Level.INFO, "PCD unloaded.");
		
	}

	@Override
	public void onEnable()
	{
		logger.log(Level.INFO, "PCD loaded.");
		registerEvents();
		
		LockettePlugin = (Lockette)this.getServer().getPluginManager().getPlugin("Lockette");
		
		new File(mainDir).mkdir();
		
		if(!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
				FileOutputStream out = new FileOutputStream(configFile);
				prop.put("DropsEnabled", "false");
				prop.put("DeathMessage", "true");
				prop.put("DeathMessageString", "died and left a chest where he died.");
				prop.put("SignOnChest", "true");
				prop.put("SignLocketteEnabled", "true");
				prop.put("SignLockettePrivateDefault", "true");
				prop.put("Sign_BeaconEnabled", "true");
				prop.put("Sign_BeaconHeight", "10");
				prop.put("ChestDeleteIntervalEnabled", "true");
				prop.put("ChestDeleteInterval", "80");
				prop.store(out, configFileHeader);
				out.flush();
				out.close();
			}
			catch(IOException ex) {}
		}
		else
		{
			loadConfig();
		}
	}

	private void loadConfig()
	{
		try
		{
			FileInputStream in = new FileInputStream(configFile);
			prop.load(in);
			
			drops = Boolean.parseBoolean(prop.getProperty("DropsEnabled"));
			deathMessage = Boolean.parseBoolean(prop.getProperty("DeathMessage"));
			deathMessageString = prop.getProperty("DeathMessageString");
			SignOnChest = Boolean.parseBoolean(prop.getProperty("SignOnChest"));
			SignLockette_Enabled = Boolean.parseBoolean(prop.getProperty("SignLocketteEnabled"));
			SignLockette_PrivateDefault = Boolean.parseBoolean(prop.getProperty("SignLockettePrivateDefault"));
			Sign_BeaconEnabled = Boolean.parseBoolean(prop.getProperty("Sign_BeaconEnabled"));
			Sign_BeaconHeight = Integer.parseInt(prop.getProperty("Sign_BeaconHeight"));
			ChestDeleteInterval = Integer.parseInt(prop.getProperty("ChestDeleteInterval"));
			ChestDeleteIntervalEnabled = Boolean.parseBoolean(prop.getProperty("ChestDeleteIntervalEnabled"));
		}
		catch(IOException ex) { }
		
	}

	private void registerEvents()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
		
	}

}
