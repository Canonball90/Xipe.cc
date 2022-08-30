package xipe.module.modules.Player;

import xipe.event.events.PacketEvent;
import xipe.module.Mod;
import com.google.common.eventbus.Subscribe;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

public class Velocity extends Mod{

	public Velocity() {
		super("Velocity", "Prevents knockback", Category.WORLD);
	}
	
	@Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        if ((event.getPacket() instanceof EntityVelocityUpdateS2CPacket) || (event.getPacket() instanceof ExplosionS2CPacket))
            event.cancel();
    }

}
