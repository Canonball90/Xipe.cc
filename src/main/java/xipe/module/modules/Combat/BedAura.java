package xipe.module.modules.Combat;
/*
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;
import com.google.common.collect.Streams;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BedAura extends Mod{

	NumberSetting range = new NumberSetting("Range", 0.1, 5.0, 4.0, 1.0);
	NumberSetting delay = new NumberSetting("Delay", 0.1, 40.0, 10.0, 1.0);
	
	public BedAura() {
		super("BedAura", "Explody", Category.COMBAT);
		addSettings(range, delay);
	}

	//TODO: Rewrite this

	//BedAura
	private int delayTimeTicks;
	private int oldSlot = -1;
	private boolean isSpoofingAngles;
	private double yaw;
	private double pitch;
	//find
	private BlockPos findClosestTarget() {
		List<BlockPos> blocks = new ArrayList<>();
		Streams.stream(mc.world.getEntities()).filter(entity -> entity instanceof PlayerEntity).forEach(entity -> {
			BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
			if (mc.world.getBlockState(pos).getBlock() instanceof BedBlock) {
				blocks.add(pos);
			}
		});
		return blocks.stream().min(Comparator.comparing(c -> mc.player.squaredDistanceTo(Vec3d.ofCenter(c)))).orElse(null);
	}

	//

	//BedAura
	@Override
	public void onEnable() {
		if (mc.player == null) {
			this.toggle();
			return;
		}
		oldSlot = mc.player.getInventory().selectedSlot;
	}

	//BedAura
	@Override
	public void onDisable() {
		if (mc.player == null) {
			return;
		}
		if (oldSlot != -1) {
			mc.player.getInventory().selectedSlot = oldSlot;
			oldSlot = -1;
		}
		if (isSpoofingAngles) {
			isSpoofingAngles = false;
		}
	}

	//BedAura
	@Override
	public void onTick() {
		if (mc.player == null) {
			this.toggle();
			return;
		}
		if (delayTimeTicks < delay.getValue()) {
			delayTimeTicks++;
			return;
		} else {
			delayTimeTicks = 0;
		}
		List<BlockPos> blocks = findBeds();
		if (blocks.isEmpty()) {
			return;
		}
		BlockPos target = blocks.get(0);
		Direction side = getPlaceableSide(target);
		if (side == null) {
			return;
		}
		BlockPos neighbour = target.offset(side);
		Direction opposite = side.getOpposite();
		Vec3d hitVec = new Vec3d(neighbour.getX() + 0.5 + opposite.getOffsetX() * 0.5, neighbour.getY() + 0.5 + opposite.getOffsetY() * 0.5, neighbour.getZ() + 0.5 + opposite.getOffsetZ() * 0.5);
		int newSlot = -1;
		for (int i = 0; i < 9; i++) {
			if (mc.player.getInventory().getStack(i).getItem() instanceof BedItem) {
				newSlot = i;
				break;
			}
		}
		if (newSlot == -1) {
			return;
		}
		mc.player.getInventory().selectedSlot = newSlot;
		rotateVec3d(hitVec);
		assert mc.interactionManager != null;
		mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(hitVec, opposite, neighbour, false));
		mc.player.swingHand(Hand.MAIN_HAND);
		mc.player.getInventory().selectedSlot = oldSlot;
		resetRotation();
	}

}

 */
