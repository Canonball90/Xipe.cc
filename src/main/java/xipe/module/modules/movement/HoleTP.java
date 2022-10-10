package xipe.module.modules.movement;

import com.google.common.eventbus.Subscribe;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class HoleTP extends Mod{
	
	private boolean flag;
	
	NumberSetting power = new NumberSetting("Power", -2.0, -0.1, -1.0, 0.1);

	public HoleTP() {
		super("HoleTP", "Teleports you into hole", Category.MOVEMENT);
		addSetting(power);
	}
	
	 @Override
	    public void onTick() {
	       nullCheck();
	       
	        mc.player.setVelocity(0, power.getValue(), 0);
	    }
	 
	 private boolean fallingIntoHole()
	    {
	        final Vec3d vec = interpolateEntity(mc.player, 3);

	        final BlockPos pos = new BlockPos(vec.x, vec.y - 1, vec.z);

	        final BlockPos[] posList = {pos.north(), pos.south(), pos.east(), pos.west(), pos.down()};

	        int blocks = 0;

	        for (final BlockPos blockPos : posList)
	        {
	            final Block block = mc.world.getBlockState(blockPos).getBlock();

	            if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) ++blocks;
	        }

	        return blocks == 5;
	    }

	    private Vec3d interpolateEntity(final PlayerEntity entity, final float time)
	    {
	        return new Vec3d(entity.lastRenderX + (entity.getX() - entity.lastRenderX) * time, entity.lastRenderY + (entity.getY() - entity.lastRenderY) * time, entity.lastRenderZ + (entity.getZ() - entity.lastRenderZ) * time);
	    }

}
