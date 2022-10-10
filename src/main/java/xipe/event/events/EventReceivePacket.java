package xipe.event.events;

import net.minecraft.network.Packet;
import xipe.event.Event;

public class EventReceivePacket extends Event
{
    private Packet<?> packet;
    
    public EventReceivePacket(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
}
