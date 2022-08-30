package xipe.module.modules.movement;

import net.minecraft.block.AirBlock;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class Nofall extends Mod{
	
	public ModeSetting mode = new ModeSetting("Mode", "OnGround", "OnGround", "BreakFall");

	public Nofall() {
		super("NoFall", "Prevents you from taking fall damage", Category.WORLD);
		 addSetting(mode);
	}
	
	  @Override
	    public void onTick() {
	        if (mc.player == null || mc.getNetworkHandler() == null) {
	            return;
	        }
	        if (mode.getMode().equalsIgnoreCase("BreakFall")) {
	        	if (mc.player.fallDistance > 2.5) {
	        		mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
	        		mc.player.setVelocity(0, 0.1, 0);
	        		mc.player.fallDistance = 0;
	        	}
	        } else if (mode.getMode().equalsIgnoreCase("OnGround")) {
	        	if (mc.player.fallDistance > 2.5) mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
	        }
	        super.onTick();
	    }

}
