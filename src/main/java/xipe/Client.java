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

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.util.Identifier;
import xipe.event.EventManager;
import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.ui.screens.clickgui.ClickGUI;
import xipe.ui.screens.csgo.MenuGUI;
import xipe.utils.HwidVerificator;

public class Client implements ModInitializer{

	public static final Client INSTANCE = new Client();
	public static Logger logger = LogManager.getLogger(Client.class);
	
    public long time = -1L;
	
	public static final EventBus EventBus = new EventBus();
	private MinecraftClient mc = MinecraftClient.getInstance();
	
	static File file;
	
	public final ManagedShaderEffect blur = ShaderEffectManager.getInstance().manage(new Identifier("xipe", "shaders/post/fade_in_blur.json"),
            shader -> shader.setUniformValue("Radius", 8f));

		
	@Override
	public void onInitialize() {
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		logger.info("---------------------|Xipe.cc|---------------------");
		//HwidVerificator.verify();
		this.load();
	}
	
	public void onKeyPress(int key, int action) {
		if(action == GLFW.GLFW_PRESS) {
			for(Mod module : ModuleManager.INSTANCE.getModules()) {
				if(mc.currentScreen instanceof ChatScreen) return;
				if(key == module.getKey()) module.toggle();
			}
			
			if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) mc.setScreen(ClickGUI.INSTANCE);
		}
	}
	
	public void save() {
        try {
            final PrintWriter pw = new PrintWriter(Client.file);
            for (final Mod mod : ModuleManager.INSTANCE.getModules()) {
                pw.println(mod.isEnabled());
            }
            pw.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public void load() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(Client.file));
            for (final Mod m : ModuleManager.INSTANCE.getModules()) {
                m.setEnabled(Boolean.parseBoolean(reader.readLine()));
            }
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public static boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public void stopped() {
        this.save();
    }
    
	
	public void onTick() {
		if(mc.player != null) {
			for(Mod module : ModuleManager.INSTANCE.getEnabledModules()) {
				module.onTick();
			}
		}
	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
	
	static {
		 Client.file = new File(MinecraftClient.getInstance().runDirectory, "config");
	}
}
