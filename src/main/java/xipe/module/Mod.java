 package xipe.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.block.BedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import xipe.module.settings.KeybindSetting;
import xipe.module.settings.Setting;
import xipe.utils.player.ChatUtil;
import xipe.utils.player.Notification;
import xipe.utils.player.NotificationUtil;

public class Mod {

	private int x;
	private int x2;
	private int y;
	private int y2;
	protected MinecraftClient mc = MinecraftClient.getInstance();
	private String name;
	private String displayName;
	private String description;
	private Category category;
	public double arrayAnimation = -1;
	
	private static final Formatting RED = Formatting.RED;
	private static final Formatting GREEN = Formatting.GREEN;
	
	private List<Setting> settings = new ArrayList<>();
	
	public void toggle() {
		this.enabled = !this.enabled;
		if(enabled) {
			onEnable();
			NotificationUtil.send_notification(new Notification(name +" is enabled", 0, 255, 0));
		}
		else {
			onDisable();
			NotificationUtil.send_notification(new Notification(name +" is disabled", 255, 0, 0));
		}
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public void onTick() {
		
	}
	
	public void onMotion() {
    }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getKey() {
		return key;
	}
	
	public void nullCheck() {
		if(mc.world == null || mc.player == null || mc.getNetworkHandler() == null || mc.getBufferBuilders() == null) {
			return;
		}
	}

	public void setKey(int key) {
		this.key = key;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		if(enabled) {
			onEnable();
			
		}
		else {
			onDisable();
		}
	}

	public Category getCategory() {
		return category;
	}
	
	 public void onWorldRender(MatrixStack matrices) {}

	private int key;
	private boolean enabled;
	
	public Mod(String name, String description, Category category) {
		this.name = name;
		this.displayName = name;
		this.description = description;
		this.category = category;
		
		addSetting(new KeybindSetting("Key", 0));
	}
	
	public List<Setting> getSettings() {
		return settings;
	}
	
	 public Setting getSettingByIndex(int i) {
	        return (Setting)this.getSettings().get(i);
	    }
	
	public void addSetting(Setting setting) {
		settings.add(setting);
	}
	
	public void addSettings(Setting...settings) {
		for(Setting setting : settings) addSetting(setting);
	}
	
	 private static double animation(double current, double end, double factor, double start) {
	        double progress = (end - current) * factor;

	        if (progress > 0) {
	            progress = Math.max(start, progress);
	            progress = Math.min(end - current, progress);
	        } else if (progress < 0) {
	            progress = Math.min(-start, progress);
	            progress = Math.max(end - current, progress);
	        }

	        return current + progress;
	    }
	
	public enum Category{
		COMBAT("Combat"),
		MOVEMENT("Movement"),
		RENDER("Render"),
		EXPLOIT("Exploit"),
		WORLD("World"),
		HUD("Hud");
		
		public String name;
		
		private Category(String name) {
			this.name = name;
		}
		
	}
	
	
}