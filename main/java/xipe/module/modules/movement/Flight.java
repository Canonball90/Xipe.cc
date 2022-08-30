package xipe.module.modules.movement;


import xipe.Client;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;


public class Flight extends Mod {

	public static MinecraftClient mc = MinecraftClient.getInstance();
	
    public static GameMode getGameMode(PlayerEntity player) {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid()); 
        if (playerListEntry != null) return playerListEntry.getGameMode(); 
        return GameMode.DEFAULT;
    }
	
	public NumberSetting speed = new NumberSetting("Speed", 0, 10, 2, 1);
	public ModeSetting mode = new ModeSetting("Mode", "Static", "Static", "Vanilla");
	
	int bypassTimer = 0;
	
	public Flight() {
		super("Flight", "Allows you to fly", Category.MOVEMENT);
		addSettings(speed, mode);
	}
	
	@Override
	public void onTick() {
		nullCheck();
		
        if (mode.getMode().equalsIgnoreCase("Vanilla")) {
        	mc.player.getAbilities().allowFlying = true;
        	//mc.player.getAbilities().flying = true;
        	mc.player.getAbilities().setFlySpeed(((float) speed.getValueFloat()) / 10);
		

        } else if (mode.getMode().equalsIgnoreCase("Static")) {
        	GameOptions go = mc.options;
        	float y = mc.player.getYaw();
        	int mx = 0, my = 0, mz = 0;
 
        	if (go.jumpKey.isPressed()) {
        		my++;
        	}
        	if (go.backKey.isPressed()) {
        		mz++;
        	}
        	if (go.leftKey.isPressed()) {
        		mx--;
        	}
        	if (go.rightKey.isPressed()) {
        		mx++;
        	}
        	if (go.sneakKey.isPressed()) {
        		my--;
        	}
        	if (go.forwardKey.isPressed()) {
        		mz--;
        	}
        	double ts = speed.getValueFloat() / 2;
            double s = Math.sin(Math.toRadians(y));
            double c = Math.cos(Math.toRadians(y));
            double nx = ts * mz * s;
            double nz = ts * mz * -c;
            double ny = ts * my;
            nx += ts * mx * -c;
            nz += ts * mx * -s;
            Vec3d nv3 = new Vec3d(nx, ny, nz);
            mc.player.setVelocity(nv3);
        }
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		if ((getGameMode(mc.player) == GameMode.CREATIVE)) {
			mc.player.getAbilities().allowFlying = true;
			mc.player.getAbilities().flying = false;
		} else {
			mc.player.getAbilities().allowFlying = false;
			mc.player.getAbilities().flying = false;
		}
		super.onTick();
	}
	@Override
	public void onEnable() {
		if ((getGameMode(mc.player) == GameMode.CREATIVE)) {
			mc.player.getAbilities().allowFlying = true;
			mc.player.getAbilities().flying = true;
		} else {
			mc.player.getAbilities().allowFlying = true;
			mc.player.getAbilities().flying = true;
		}
	}
}