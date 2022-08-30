package xipe.module.modules.Player;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import xipe.module.Mod;
import xipe.utils.FakePlayerEntity;

public class FakePlayer extends Mod {

	public ArrayList<FakePlayerEntity> fakePlayers = new ArrayList<>();
	private PlayerEntity playerEntity;
	
	public FakePlayer() {
		super("FakePlayer", "Spawns a fake player to test combat modules on", Category.WORLD);
	}

	@Override
	public void onEnable() {
		if (mc.player != null) {
			playerEntity = new FakePlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), mc.player.getName().getString()));
    		playerEntity.copyFrom(mc.player);
    		playerEntity.copyPositionAndRotation(mc.player);
			mc.world.addEntity(1000000, playerEntity);
			fakePlayers.add((FakePlayerEntity) playerEntity);
		}
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if (playerEntity != null) {
    		playerEntity.setPos(0, Double.NEGATIVE_INFINITY, 0);
    		if (mc.world != null) mc.world.removeEntity(1000000, Entity.RemovalReason.DISCARDED);
    		playerEntity = null;
    	}
		if (fakePlayers.contains((FakePlayerEntity) playerEntity)) fakePlayers.remove((FakePlayerEntity) playerEntity);
		super.onDisable();
	}
}
