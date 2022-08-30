package xipe.module.modules.movement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;


public class Strafe extends Mod {

	public static MinecraftClient mc = MinecraftClient.getInstance();
	
    public static GameMode getGameMode(PlayerEntity player) {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid()); 
        if (playerListEntry != null) return playerListEntry.getGameMode(); 
        return GameMode.DEFAULT;
    }
	
	public NumberSetting speed = new NumberSetting("Speed", 0.5, 2, 0.5, 0.1);
	public ModeSetting mode = new ModeSetting("Mode", "Static", "Static", "Vanilla");
		
	public Strafe() {
		super("Strafe", "Moves, but faster", Category.MOVEMENT);
		addSettings(speed, mode);
	}
	
	@Override
	public void onTick() {
		if (mc.player == null || mc.getNetworkHandler() == null) {
            return;
        }
		
        if (mode.getMode().equalsIgnoreCase("Vanilla")) {
    		mc.player.airStrafingSpeed = (float) (speed.getValueFloat() / 10);
        } else if (mode.getMode().equalsIgnoreCase("Static")) {
        if(mc.player.isOnGround()) {
        	if(mc.player.input.pressingForward || mc.player.input.pressingBack || mc.player.input.pressingLeft || mc.player.input.pressingRight) {
        	mc.player.jump();
        	}
        }
        	GameOptions go = mc.options;
        	float y = mc.player.getYaw();
        	int mx = 0, mz = 0;
        	if (go.backKey.isPressed()) {
        		mz++;
        	}
        	if (go.leftKey.isPressed()) {
        		mx--;
        	}
        	if (go.rightKey.isPressed()) {
        		mx++;
        	}
        	if (go.forwardKey.isPressed()) {
        		mz--;
        	}
        	double ts = speed.getValueFloat() / 2;
            double s = Math.sin(Math.toRadians(y));
            double c = Math.cos(Math.toRadians(y));
            double nx = ts * mz * s;
            double nz = ts * mz * -c;
            nx += ts * mx * -c;
            nz += ts * mx * -s;
            Vec3d nv3 = new Vec3d(nx, mc.player.getVelocity().y, nz);
            mc.player.setVelocity(nv3);
        	
        
        }
		super.onTick();
	}
}
