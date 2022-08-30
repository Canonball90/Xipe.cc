package xipe.module;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import xipe.module.settings.KeybindSetting;
import xipe.module.settings.Setting;
import xipe.utils.ChatUtil;

public class Mod {

	protected MinecraftClient mc = MinecraftClient.getInstance();
	private String name;
	private String displayName;
	private String description;
	private Category category;
	
	private List<Setting> settings = new ArrayList<>();
	
	public void toggle() {
		this.enabled = !this.enabled;
		if(enabled) {
			onEnable();
			ChatUtil.printInfo(name + " Has been Enabled");
		}
		else {
			onDisable();
			ChatUtil.printInfo(name + " Has been Enabled");
		}
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public void onTick() {
		
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
		if(mc.world == null || mc.player == null || mc.getNetworkHandler() == null) {
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
			ChatUtil.printInfo(name + " Has been Disabled");
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
		
		//addSetting(new KeybindSetting("Key", 0));
	}
	
	public List<Setting> getSettings() {
		return settings;
	}
	
	public void addSetting(Setting setting) {
		settings.add(setting);
	}
	
	public void addSettings(Setting...settings) {
		for(Setting setting : settings) addSetting(setting);
	}
	
	public enum Category{
		COMBAT("Combat", new Identifier("xipe", "combat.png")),
		MOVEMENT("Movement", new Identifier("xipe", "movement.png")),
		RENDER("Render", new Identifier("xipe", "render.png")),
		EXPLOIT("Exploit", new Identifier("xipe", "exploit.png")),
		WORLD("World", new Identifier("xipe", "world.png"));
		//HUD("Hud",  new Identifier("xipe", "Hud.png"));
		
		public String name;
		public Identifier icon;
		
		private Category(String name, Identifier icon) {
			this.name = name;
			this.icon = icon;
		}
		
	}
	
}