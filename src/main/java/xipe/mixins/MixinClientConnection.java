package xipe.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import xipe.Client;
import xipe.event.events.EventReceivePacket;
import xipe.event.events.EventSendPacket;
import xipe.module.ModuleManager;
import xipe.module.modules.Combat.Criticals;
import xipe.module.modules.Combat.Velocity;
import xipe.module.modules.Combat.CrystalAura;
import xipe.module.modules.Combat.KillAura;
import xipe.module.modules.Combat.Criticals.InteractType;
import xipe.module.modules.Player.PacketLogger;
import xipe.module.modules.exploit.MountBypass;
import xipe.utils.ReflectionHelper;
import xipe.utils.player.ChatUtil;
import xipe.utils.player.RotationUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.network.ClientConnection;

import java.util.Objects;

import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ClientConnection.class })
public class MixinClientConnection
{
	
	 @Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
	    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
	    	EventSendPacket event = new EventSendPacket(packet);
	    	event.call();
	    	if (event.isCancelled()) ci.cancel();
	    	if(ModuleManager.INSTANCE.getModule(PacketLogger.class).isEnabled() && ModuleManager.INSTANCE.getModule(PacketLogger.class).outgoing.isEnabled()) {
	    	if(event instanceof EventSendPacket) ChatUtil.printInfo(event.getPacket().toString());
	    	}
	    	if(ModuleManager.INSTANCE.getModule(Criticals.class).isEnabled()) {
	    		   if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet1) {
	   	            if (!(Criticals.instance.getInteractType(packet1) == InteractType.ATTACK && Criticals.instance.getEntity(packet1) instanceof LivingEntity)) return;
	   	            if (Criticals.instance.getEntity(packet1) instanceof EndCrystalEntity) return;

	   	         Criticals.instance.doCritical();
	   	        }
	    		
	    	}
	    	if(ModuleManager.INSTANCE.getModule(MountBypass.class).isEnabled()) {
	    		if (MinecraftClient.getInstance().world.getServer() == null) return;
	  	        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket && ((PlayerInteractEntityC2SPacket) event.getPacket()).getEntity(Objects.requireNonNull(MinecraftClient.getInstance().world.getServer()).getOverworld()) instanceof AbstractDonkeyEntity) {
	  	            event.cancel();
	  	        }
	    	}
	    	if(ModuleManager.INSTANCE.getModule(CrystalAura.class).isEnabled()) {
	    		if (event.getPacket() instanceof PlayerMoveC2SPacket) {
	    			if (CrystalAura.instance.targets != null && !CrystalAura.instance.targets.isEmpty() && CrystalAura.instance.lookVec != null) {
	    				((PlayerMoveC2SPacketAccessor) event.getPacket()).setYaw((float)RotationUtils.getYaw(CrystalAura.instance.lookVec));
	    				((PlayerMoveC2SPacketAccessor) event.getPacket()).setPitch((float)RotationUtils.getPitch(CrystalAura.instance.lookVec));
	    			} else {
	    				((PlayerMoveC2SPacketAccessor) event.getPacket()).setYaw(MinecraftClient.getInstance().player.getYaw());
	    				((PlayerMoveC2SPacketAccessor) event.getPacket()).setPitch(MinecraftClient.getInstance().player.getPitch());
	    			}
	    		}
	    	}
	 
	 }

	 
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    public void receive(final ChannelHandlerContext channelHandlerContext, final Packet<?> packet, final CallbackInfo ci) {
        final EventReceivePacket event = new EventReceivePacket(packet);
        event.call();
        if (event.isCancelled()) {
            ci.cancel();
        }
        if(ModuleManager.INSTANCE.getModule(PacketLogger.class).isEnabled() && ModuleManager.INSTANCE.getModule(PacketLogger.class).income.isEnabled()) {
        if(event instanceof EventReceivePacket) ChatUtil.printInfo(event.getPacket().toString());
        }
        if(ModuleManager.INSTANCE.getModule(Velocity.class).isEnabled()) {
        	Velocity.get.onReceivePacket(event);
        }
    }
    
 
}
