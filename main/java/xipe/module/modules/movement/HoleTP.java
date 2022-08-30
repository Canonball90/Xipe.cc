package xipe.module.modules.movement;

import com.google.common.eventbus.Subscribe;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import xipe.module.Mod;

public class HoleTP extends Mod{
	
	private boolean flag;

	public HoleTP() {
		super("HoleTP", "Teleports you into hole", Category.MOVEMENT);
	}
	
	 @Override
	    public void onTick() {
	       nullCheck();
	        if (mc.player.isInLava()) {
	            mc.player.setVelocity(0, -1.5, 0);
	        }
	    }

}
