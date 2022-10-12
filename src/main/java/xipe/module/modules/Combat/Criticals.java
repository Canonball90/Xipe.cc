package xipe.module.modules.Combat;

import java.util.Objects;

import com.google.common.eventbus.Subscribe;

import io.netty.buffer.Unpooled;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import xipe.event.events.EventSendPacket;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class Criticals extends Mod{

	public ModeSetting critical = new ModeSetting ("Mode", "Normal", "Normal", "Jump");
	
	public Criticals() {
		super("Criticals", "Allways send criticals when attacking", Category.COMBAT);
		instance = this;
		addSetting(critical);
	}
	   @Subscribe
	    public void sendPacket(EventSendPacket event) {
	        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
	            if (!(getInteractType(packet) == InteractType.ATTACK && getEntity(packet) instanceof LivingEntity)) return;
	            if (getEntity(packet) instanceof EndCrystalEntity) return;

	            doCritical();
	        }
	    }

	    public void doCritical() {
	        if (mc.player.isInLava() || mc.player.isTouchingWater()) return;

	        double posX = mc.player.getX();
	        double posY = mc.player.getY();
	        double posZ = mc.player.getZ();
	        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(posX, posY + 0.0633, posZ, false));
	        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(posX, posY, posZ, false));
	    }

	    public Entity getEntity(PlayerInteractEntityC2SPacket packet) {
	        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
	        packet.write(packetBuf);

	        return mc.world.getEntityById(packetBuf.readVarInt());
	    }

	    public InteractType getInteractType(PlayerInteractEntityC2SPacket packet) {
	        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
	        packet.write(packetBuf);

	        packetBuf.readVarInt();
	        return packetBuf.readEnumConstant(InteractType.class);
	    }

	    public enum InteractType {
	        INTERACT,
	        ATTACK,
	        INTERACT_AT
	    }
	    
	    private static final Formatting Gray = Formatting.GRAY;
	    
	    @Override
	    public void onTick() {
	    	this.setDisplayName("Criticals" + Gray + "["+critical.getMode()+"]");
	    	if(critical.isMode("Jump")) {
	    		
	    	}
	    	super.onTick();
	    }

	    public static Criticals instance;

}


