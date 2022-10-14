package xipe.utils.player;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil {

	private static MinecraftClient mc = MinecraftClient.getInstance();

	public static void blinkToPos(Vec3d startPos, final BlockPos endPos, final double slack, final double[] pOffset) {
        double curX = startPos.x;
        double curY = startPos.y;
        double curZ = startPos.z;
        try {
            final double endX = endPos.getX() + 0.5;
            final double endY = endPos.getY() + 1.0;
            final double endZ = endPos.getZ() + 0.5;

            double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            int count = 0;
            while (distance > slack) {
                distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
                if (count > 120) {
                    break;
                }
                final double diffX = curX - endX;
                final double diffY = curY - endY;
                final double diffZ = curZ - endZ;
                final double offset = ((count & 0x1) == 0x0) ? pOffset[0] : pOffset[1];
                if (diffX < 0.0) {
                    if (Math.abs(diffX) > offset) {
                        curX += offset;
                    } else {
                        curX += Math.abs(diffX);
                    }
                }
                if (diffX > 0.0) {
                    if (Math.abs(diffX) > offset) {
                        curX -= offset;
                    } else {
                        curX -= Math.abs(diffX);
                    }
                }
                if (diffY < 0.0) {
                    if (Math.abs(diffY) > 0.25) {
                        curY += 0.25;
                    } else {
                        curY += Math.abs(diffY);
                    }
                }
                if (diffY > 0.0) {
                    if (Math.abs(diffY) > 0.25) {
                        curY -= 0.25;
                    } else {
                        curY -= Math.abs(diffY);
                    }
                }
                if (diffZ < 0.0) {
                    if (Math.abs(diffZ) > offset) {
                        curZ += offset;
                    } else {
                        curZ += Math.abs(diffZ);
                    }
                }
                if (diffZ > 0.0) {
                    if (Math.abs(diffZ) > offset) {
                        curZ -= offset;
                    } else {
                        curZ -= Math.abs(diffZ);
                    }
                }
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(curX, curY, curZ, true));
                ++count;
            }
        } catch (Exception e) {

        }
    }
	
	 public static boolean isCollidable(final Block block) {
	        return block != Blocks.AIR && block != Blocks.BEETROOTS && block != Blocks.CARROTS && block != Blocks.LAVA && block != Blocks.MELON_STEM && block != Blocks.NETHER_WART && block != Blocks.POTATOES && block != Blocks.PUMPKIN_STEM && block != Blocks.RED_MUSHROOM && block != Blocks.REDSTONE_TORCH  && block != Blocks.TORCH && block != Blocks.VINE && block != Blocks.WATER && block != Blocks.COBWEB && block != Blocks.WHEAT;
	    }
	 
	 public static double getPlayerSpeed() {
	        float currentTps = mc.getServer().getTickTime() / 1000.0f;
	        double dx = Math.abs(mc.player.getX() - mc.player.prevX);
	        double dz = Math.abs(mc.player.getZ() - mc.player.prevZ);
//	        return ((MathHelper.sqrt((float) (Math.pow(dirSpeed(Direction.Axis.X), 2) + Math.pow(dirSpeed(Direction.Axis.Z), 2))) / currentTps)) * 3.6;
	        return Math.sqrt(dx * dx + dz * dz) * 20 * 3.6;
	    }
	 
	 private static final ArrayList<PlayerEntity> list = new ArrayList<>();

	    public static int getEntityPing(PlayerEntity entity) {
	        if (mc.getNetworkHandler() == null) return 0;
	        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(entity.getUuid());
	        if (playerListEntry == null) return 0;
	        return playerListEntry.getLatency();
	    }

        //get the amount of deaths the player has

}
