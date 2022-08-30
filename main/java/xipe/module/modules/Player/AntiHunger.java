package xipe.module.modules.Player;

import com.google.common.eventbus.Subscribe;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import xipe.event.events.PacketEvent;
import xipe.module.Mod;
import xipe.utils.FabricReflect;

public class AntiHunger extends Mod{

	public AntiHunger() {
		super("AntiHunger", "No Hungry", Category.WORLD);
	}
	
	 @Subscribe
	    public void onPacketSend(PacketEvent.Send event) {
	        if (event.getPacket() instanceof PlayerMoveC2SPacket) {
	            FabricReflect.writeField(event.getPacket(), false, "field_12891", "onGround");
	        }
	    }

}
