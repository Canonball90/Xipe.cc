package xipe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.ui.screens.clickgui.ClickGUI;

public class Client implements ModInitializer{

	public static final Client INSTANCE = new Client();
	public static Logger logger = LogManager.getLogger(Client.class);
	
	static File xipeconfig = new File(MinecraftClient.getInstance().runDirectory + "\\config\\xipe", "config.txt");
	static File xipesettings = new File(MinecraftClient.getInstance().runDirectory + "\\config\\xipe", "settings.txt");

	static File f = new File(MinecraftClient.getInstance().runDirectory + "\\config\\xipe");
    static boolean savedBoolean = true;
	
	public static final EventBus EventBus = new EventBus();
	private MinecraftClient mc = MinecraftClient.getInstance();
	
	@SuppressWarnings("resource")
	public static void CreateFile() {
	    try {
	      if (xipeconfig.createNewFile()) {
	    	  logger.info("File " + xipeconfig.getName() + " was created in " + MinecraftClient.getInstance().runDirectory + "\\config\\xipe");

	      } else {
	    	  logger.info("Config file found, saving config...");
	      }
	    } catch (IOException e) {
	    	e.printStackTrace();
	  }
	}

	public static void save() {
        try {
            PrintWriter pw = new PrintWriter(xipeconfig);
            for(Mod mod : ModuleManager.INSTANCE.getModules()){
                pw.println(mod.getName() + ":" + mod.isEnabled() + ":" + mod.getKey());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(xipeconfig));
            String line = "";
            while ((line = reader.readLine()) != null) {
            	String[] args = line.split(":");
            	Mod mod = ModuleManager.INSTANCE.getModuleByName(args[0]);
            	mod.setEnabled(Boolean.parseBoolean(args[1]));
            	mod.setKey(Integer.valueOf(args[2]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@Override
	public void onInitialize() {
		
		logger.info("Sup nigga");
	}
	
	public void onKeyPress(int key, int action) {
		if(action == GLFW.GLFW_PRESS) {
			for(Mod module : ModuleManager.INSTANCE.getModules()) {
				if(key == module.getKey()) module.toggle();
			}
			
			if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) mc.setScreen(ClickGUI.INSTANCE);
		}
	}
	
	public void onTick() {
		if(mc.player != null) {
			for(Mod module : ModuleManager.INSTANCE.getEnabledModules()) {
				module.onTick();
			}
		}
	}
	
	public void stopped() {
		//save();
	}
	
	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
