package xipe.module.modules.Combat;

import java.util.Objects;

import com.google.common.eventbus.Subscribe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import xipe.event.events.PacketEvent;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class Criticals extends Mod{

	public ModeSetting critical = new ModeSetting ("Mode", "Normal", "Normal", "Normal but weird", "Jump");
	
	public Criticals() {
		super("Criticals", "Every hit is a critical hit", Category.COMBAT);
		addSetting(critical);
	}
	
	 @Subscribe
	    public void onPacketSend(PacketEvent.Send e) {
	        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket) {
	            if (((PlayerInteractEntityC2SPacket) e.getPacket()).getEntity(Objects.requireNonNull(mc.getServer()).getWorld(mc.player.world.getRegistryKey())) instanceof EndCrystalEntity)
	                return;
	            if (critical.isMode("Normal")) {
	                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.11, mc.player.getZ(), false));
	                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.1100013579, mc.player.getZ(), false));
	                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.0000013579, mc.player.getZ(), false));
	            } else {
	                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.0625, mc.player.getZ(), false));
	                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
	            }
	        }
	        if(critical.isMode("Jump")) {
	        	if(mc.player.isOnGround() && critical.isMode("Jump")) {
	        		mc.player.setVelocity(0, 1, 0);
	        	}
	        }
	    }
}


