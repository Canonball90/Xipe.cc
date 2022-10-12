package xipe.module.modules.movement;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class ElytraFly extends Mod{
	
	public static ElytraFly get;

    ModeSetting mode = new ModeSetting("Mode", "Boost", "Boost", "Negruim");
    BooleanSetting autoFly = new BooleanSetting("AutoFly", false);
    BooleanSetting takeOff = new BooleanSetting("Take Off", true);
    BooleanSetting flatFly = new BooleanSetting("Flat Fly", true);
    BooleanSetting upEly = new BooleanSetting("Up Fly", true);
    NumberSetting speed = new NumberSetting("Factor", 0.1, 4, 0.2, 1);
    
	public ElytraFly() {
		super("ElytraFly", "Makes flying with an elytra easier", Category.MOVEMENT);
		addSettings(mode,autoFly,takeOff,flatFly,upEly,speed);
		get = this;
	}
	
	private static final Formatting Gray = Formatting.GRAY;
	
	 @Override
	    public void onTick() {
		 this.setDisplayName("Elytra Fly" + Gray + "["+mode.getMode()+"]");
	        if (!mc.player.isFallFlying()) {
	            if (takeOff.isEnabled() && !mc.player.isOnGround() && mc.options.jumpKey.isPressed()) {
	                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
	            }
	            return;
	        }

	        if (autoFly.isEnabled() && mc.player.isFallFlying()) {
	            mc.options.forwardKey.setPressed(true);
	        }

	        if (mode.isMode("Boost")) {
	            if (mc.player.getAbilities().flying) mc.player.getAbilities().flying = false;

	            float yaw = (float) Math.toRadians(mc.player.getYaw());
	            if (mc.options.forwardKey.isPressed()) {
	                mc.player.addVelocity(-MathHelper.sin(yaw) * speed.getValue() / 10, 0, MathHelper.cos(yaw) * speed.getValue() / 10);
	            }
	        } else {
	            Vec3d vec = new Vec3d(0, 0, speed.getValue())
	                    .rotateX(flatFly.isEnabled() ? 0.02f : -(float) Math.toRadians(mc.player.getPitch()))
	                    .rotateY(-(float) Math.toRadians(mc.player.getYaw()));

	            if (mc.player.isFallFlying()) {
	                mc.getNetworkHandler().sendPacket(new UpdatePlayerAbilitiesC2SPacket(new PlayerAbilities()));
	                if (upEly.isEnabled() && mc.player.getPitch() < 0.f)
	                    return;

	                if (mc.options.backKey.isPressed()) vec = vec.multiply(-1);
	                else if (mc.options.leftKey.isPressed()) vec = vec.rotateY((float) Math.toRadians(90));
	                else if (mc.options.rightKey.isPressed()) vec = vec.rotateY(-(float) Math.toRadians(90));
	                else if (mc.options.jumpKey.isPressed())
	                    vec = new Vec3d(0, speed.getValue(), 0);
	                else if (mc.options.sneakKey.isPressed())
	                    vec = new Vec3d(0, -speed.getValue(), 0);
	                else if (!mc.options.forwardKey.isPressed()) vec = Vec3d.ZERO;
	                mc.player.setVelocity(vec);
	            }
	        }
	    }

}
