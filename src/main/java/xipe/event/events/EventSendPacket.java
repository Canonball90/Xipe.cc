package xipe.event.events;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import xipe.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event
{
    public Packet<?> packet;
    
    public EventSendPacket(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    public void setPacket(final PlayerMoveC2SPacket packet) {
        this.packet = (Packet<?>)packet;
    }
}

