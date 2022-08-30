package xipe.module.modules.movement;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;
import xipe.event.EventTarget;
import xipe.event.events.EventMove;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class ElytraFly extends Mod{
	
	public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Firework", "NCP");
	public NumberSetting flySpeed = new NumberSetting("Speed", 0.5, 0.1, 2, 0.1);
	public NumberSetting downSpeed = new NumberSetting("Down Speed", 0.5, 0.1, 2, 0.1);
	public NumberSetting fallDistance = new NumberSetting("Fall Distance", 3, 0, 10, 0.5);
	public BooleanSetting autoElytra = new BooleanSetting("Auto Elytra", true);
	public BooleanSetting slowGlide = new BooleanSetting("Slow Glide", false);

	public ElytraFly() {
		super("ElytraFly", "Allows an easier elytra fly", Category.MOVEMENT);
		addSettings(mode,flySpeed,downSpeed,fallDistance,autoElytra,slowGlide);
	}

	@EventTarget
    public void onMotion(EventMove event) {
        if (wearingElytra() && (autoElytra.isEnabled() && mc.player.fallDistance >= fallDistance.getValue() && !mc.player.isOnGround() && !mc.player.isFallFlying())) {
            if (mc.player.age % 5 == 0)
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }

        if (mc.player.isFallFlying()) {
            if (mode.isMode("Firework")) {

                Vec3d vec3d_1 = mc.player.getRotationVector();
//                double double_1 = 1.5D;
                Vec3d vec3d_2 = mc.player.getVelocity();
                mc.player.setVelocity(vec3d_2.add(vec3d_1.x * 0.1D + (vec3d_1.x * 1.5D - vec3d_2.x) * 0.5D, vec3d_1.y * 0.1D + (vec3d_1.y * 1.5D - vec3d_2.y) * 0.5D, vec3d_1.z * 0.1D + (vec3d_1.z * 1.5D - vec3d_2.z) * 0.5D));
            } else {
                if (mode.isMode("NCP") || mode.isMode("Vanilla")) {
                    //PlayerUtils.setMoveSpeed(event, flySpeed.getValue());
                    if (event.getY() <= 0)
                        event.setY(mc.player.isSneaking() ? (float)-downSpeed.getValue() : (slowGlide.isEnabled() ? -0.01 : 0));
                    if (mode.isMode("Vanilla")) {
                    	if (mc.options.jumpKey.isPressed()) event.setY(flySpeed.getValue());
                    	if (mc.options.sneakKey.isPressed()) event.setY(-flySpeed.getValue());
                    }
                }

            }
        }
    }

    private boolean wearingElytra() {
        ItemStack equippedStack = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        return equippedStack != null && equippedStack.getItem() == Items.ELYTRA;
    }

	
}
