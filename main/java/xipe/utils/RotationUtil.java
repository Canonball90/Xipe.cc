package xipe.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {

    public static float startYaw, startPitch;

    private static final Vec3d eyesPos = new Vec3d(MinecraftClient.getInstance().player.getX(),
            MinecraftClient.getInstance().player.getY() + MinecraftClient.getInstance().player.getEyeHeight(MinecraftClient.getInstance().player.getPose()),
            MinecraftClient.getInstance().player.getZ());

    public static void rotate(BlockPos bp) {
        startYaw = MinecraftClient.getInstance().player.getYaw();
        startPitch = MinecraftClient.getInstance().player.getPitch();
        Vec3d vec = new Vec3d(bp.getX(), bp.getY(), bp.getZ());
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        float[] rotations = {
                MinecraftClient.getInstance().player.getYaw()
                        + MathHelper.wrapDegrees(yaw - MinecraftClient.getInstance().player.getYaw()),
                MinecraftClient.getInstance().player.getPitch() + MathHelper
                        .wrapDegrees(pitch - MinecraftClient.getInstance().player.getPitch())};

        MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], MinecraftClient.getInstance().player.isOnGround()));
    }

    public static void rotate(Entity e) {
        startYaw = MinecraftClient.getInstance().player.getYaw();
        startPitch = MinecraftClient.getInstance().player.getPitch();
        Vec3d vec = new Vec3d(e.getX(), e.getY(), e.getZ());
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        float[] rotations = {
                MinecraftClient.getInstance().player.getYaw()
                        + MathHelper.wrapDegrees(yaw - MinecraftClient.getInstance().player.getYaw()),
                MinecraftClient.getInstance().player.getPitch() + MathHelper
                        .wrapDegrees(pitch - MinecraftClient.getInstance().player.getPitch())};

        MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], MinecraftClient.getInstance().player.isOnGround()));
    }

    public static void rotate(float yaw, float pitch) {
        startYaw = MinecraftClient.getInstance().player.getYaw();
        startPitch = MinecraftClient.getInstance().player.getPitch();
        float[] rotations = {
                MinecraftClient.getInstance().player.getYaw()
                        + MathHelper.wrapDegrees(yaw - MinecraftClient.getInstance().player.getYaw()),
                MinecraftClient.getInstance().player.getPitch() + MathHelper
                        .wrapDegrees(pitch - MinecraftClient.getInstance().player.getPitch())};

        MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], MinecraftClient.getInstance().player.isOnGround()));
    }
    
    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x -  MinecraftClient.getInstance().player.getX();
        double zDiff = z -  MinecraftClient.getInstance().player.getZ();
        double yDiff = y -  MinecraftClient.getInstance().player.getY() - 1.2;
    
        double dist = MathHelper.sqrt((float) (xDiff * xDiff + zDiff * zDiff));
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

}
