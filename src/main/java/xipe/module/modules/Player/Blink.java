package xipe.module.modules.Player;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xipe.event.EventTarget;
import xipe.event.events.EventSendPacket;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.player.PlayerUtil;
import xipe.utils.render.FakePlayerEntity;
import net.minecraft.entity.Entity;

public class Blink extends Mod {

	public BooleanSetting buffer = new BooleanSetting("Buffer Packets", true);
	public NumberSetting amount = new NumberSetting("Send Amount PT", 5, 50, 25, 1);
	
	private ArrayList<Packet<?>> packets = new ArrayList<>();
	
	private double startX, startY, startZ;
	
	public static PlayerEntity playerEntity;
	private boolean stopCatching;
	
    public Blink() {
        super("Blink", "Blink hack", Category.WORLD);
        addSettings(buffer, amount);
    }
    
    @Override
    public void onEnable() {
    	stopCatching = false;
    	if (mc.player != null) {
			playerEntity = new FakePlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), mc.player.getName().getString()));
    		playerEntity.copyFrom(mc.player);
    		playerEntity.copyPositionAndRotation(mc.player);
			mc.world.addEntity(1000000, playerEntity);
		}
    	startX = mc.player.getX();
    	startY = mc.player.getY();
    	startZ = mc.player.getZ();
    	super.onEnable();
    }

    @Override
    public void onTick() {
    	if (stopCatching && !packets.isEmpty()) {
			for (int i = 0; i < amount.getValue(); i++) {
				mc.getNetworkHandler().sendPacket(packets.get(i));
			}
		}
        super.onTick();
    }
    
    @EventTarget
    private void onSendPacket(EventSendPacket event) {
    	if (mc.player == null || (packets.isEmpty() && stopCatching)) {
			packets.clear();
			this.setEnabled(false);
			return;
		}
		if (!stopCatching && !(event.getPacket() instanceof KeepAliveC2SPacket)) {
			if (event.getPacket() instanceof PlayerMoveC2SPacket) {
				packets.add(event.getPacket());
			}
			event.setCancelled(true);;
		}
    }
    
    @Override
    public void onDisable() {
    	stopCatching = true;
		if (!buffer.isEnabled() || packets.isEmpty())
			super.onDisable();
		if (!buffer.isEnabled())
			packets.forEach(packet -> {
				mc.getNetworkHandler().sendPacket(packet);
			});
		packets.clear();
    	if (playerEntity != null) {
    		playerEntity.setPos(0, Double.NEGATIVE_INFINITY, 0);
    		if (mc.world != null) mc.world.removeEntity(1000000, Entity.RemovalReason.DISCARDED);
    		playerEntity = null;
    	}
    	PlayerUtil.blinkToPos(new Vec3d(startX, startY, startZ), new BlockPos(mc.player.getX(), mc.player.getY(), mc.player.getZ()), 0, new double[] {1, 2});
    	super.onDisable();
    }
}
