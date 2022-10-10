package xipe.utils.player;

import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import xipe.utils.world.Pool;
import xipe.utils.world.Target;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.entity.LivingEntity;
import java.util.List;
import net.minecraft.client.MinecraftClient;

public class RotationUtils
{
    public static MinecraftClient mc;
    private static final Pool<Rotation> rotationPool;
    private static final List<Rotation> rotations;
    public static float serverPitch;
    public static boolean isCustomPitch;
    public static boolean isCustomYaw;
    public static float serverYaw;
    private static float clientPitch;
    private static float clientYaw;
    private static long lastModificationTime;
    public static float startYaw, startPitch;
    
    public static void setSilentPitch(final float pitch) {
        RotationUtils.serverPitch = pitch;
        RotationUtils.isCustomPitch = true;
    }
    
    public static void setSilentYaw(final float yaw) {
        RotationUtils.serverYaw = yaw;
        RotationUtils.isCustomYaw = true;
    }
    
    public static void resetPitch() {
        RotationUtils.isCustomPitch = false;
    }
    
    public static void resetYaw() {
        RotationUtils.isCustomYaw = false;
    }
    
    public static void setSilentRotations(final LivingEntity target, final float yaw, final float pitch) {
        RotationUtils.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(getRotations(target)[0], getRotations(target)[1], RotationUtils.mc.player.isOnGround()));
        RotationUtils.serverPitch = pitch;
        RotationUtils.isCustomPitch = true;
        RotationUtils.serverYaw = yaw;
        RotationUtils.isCustomYaw = true;
    }
    
    public static void setSilentRotations(final float yaw, final float pitch) {
        RotationUtils.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, RotationUtils.mc.player.isOnGround()));
        RotationUtils.serverPitch = pitch;
        RotationUtils.isCustomPitch = true;
        RotationUtils.serverYaw = yaw;
        RotationUtils.isCustomYaw = true;
    }
    
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - RotationUtils.mc.player.getX();
        final double zDiff = z - RotationUtils.mc.player.getZ();
        final double yDiff = y - RotationUtils.mc.player.getY() - 1.2;
        final double dist = MathHelper.sqrt((float)(xDiff * xDiff + zDiff * zDiff));
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotations(final LivingEntity ent) {
        final double x = ent.getX();
        final double z = ent.getZ();
        final double y = ent.getY() + ent.getStandingEyeHeight() / 2.0f;
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getRotations(final Entity ent) {
        final double x = ent.getX();
        final double z = ent.getZ();
        final double y = ent.getY() + ent.getStandingEyeHeight() / 2.0f;
        return getRotationFromPosition(x, z, y);
    }
    
    public static double getYaw(final Vec3d pos) {
        return RotationUtils.mc.player.getYaw() + MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(pos.getZ() - RotationUtils.mc.player.getZ(), pos.getX() - RotationUtils.mc.player.getX())) - 90.0f - RotationUtils.mc.player.getYaw());
    }
    
    public static double getPitch(final Vec3d pos) {
        final double diffX = pos.getX() - RotationUtils.mc.player.getX();
        final double diffY = pos.getY() - (RotationUtils.mc.player.getY() + RotationUtils.mc.player.getEyeHeight(RotationUtils.mc.player.getPose()));
        final double diffZ = pos.getZ() - RotationUtils.mc.player.getZ();
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        return RotationUtils.mc.player.getPitch() + MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - RotationUtils.mc.player.getPitch());
    }
    
    public static double getYaw(final BlockPos pos) {
        return RotationUtils.mc.player.getYaw() + MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(pos.getZ() + 0.5 - RotationUtils.mc.player.getZ(), pos.getX() + 0.5 - RotationUtils.mc.player.getX())) - 90.0f - RotationUtils.mc.player.getYaw());
    }
    
    public static double getPitch(final BlockPos pos) {
        final double diffX = pos.getX() + 0.5 - RotationUtils.mc.player.getX();
        final double diffY = pos.getY() + 0.5 - (RotationUtils.mc.player.getY() + RotationUtils.mc.player.getEyeHeight(RotationUtils.mc.player.getPose()));
        final double diffZ = pos.getZ() + 0.5 - RotationUtils.mc.player.getZ();
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        return RotationUtils.mc.player.getPitch() + MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - RotationUtils.mc.player.getPitch());
    }
    
    public static void rotate(final double yaw, final double pitch, final int priority, final boolean clientSide, final Runnable callback) {
        final Rotation rotation = RotationUtils.rotationPool.get();
        rotation.set(yaw, pitch, priority, clientSide, callback);
        int i;
        for (i = 0; i < RotationUtils.rotations.size() && priority <= RotationUtils.rotations.get(i).priority; ++i) {}
        RotationUtils.rotations.add(i, rotation);
    }
    
    public static void rotate(final double yaw, final double pitch, final int priority, final Runnable callback) {
        rotate(yaw, pitch, priority, false, callback);
    }
    
    public static void rotate(final double yaw, final double pitch, final Runnable callback) {
        rotate(yaw, pitch, 0, callback);
    }
    
    public static void rotate(final double yaw, final double pitch) {
        rotate(yaw, pitch, 0, null);
    }
    
    public static double getYaw(final Entity entity) {
        return RotationUtils.mc.player.getYaw() + MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(entity.getZ() - RotationUtils.mc.player.getZ(), entity.getX() - RotationUtils.mc.player.getX())) - 90.0f - RotationUtils.mc.player.getYaw());
    }
    
    public static double getPitch(final Entity entity, final Target target) {
        double y;
        if (target == Target.Head) {
            y = entity.getEyeY();
        }
        else if (target == Target.Body) {
            y = entity.getY() + entity.getHeight() / 2.0f;
        }
        else {
            y = entity.getY();
        }
        final double diffX = entity.getX() - RotationUtils.mc.player.getX();
        final double diffY = y - (RotationUtils.mc.player.getY() + RotationUtils.mc.player.getEyeHeight(RotationUtils.mc.player.getPose()));
        final double diffZ = entity.getZ() - RotationUtils.mc.player.getZ();
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        return RotationUtils.mc.player.getPitch() + MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - RotationUtils.mc.player.getPitch());
    }
    
    public static Vec3d getEyesPos() {
        final ClientPlayerEntity player = RotationUtils.mc.player;
        return new Vec3d(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());
    }
    
    public static Vec3d getLegitLookPos(final BlockPos pos, final Direction dir, final boolean raycast, final int res) {
        return getLegitLookPos(new Box(pos), dir, raycast, res, 0.01);
    }
    
    public static Vec3d getLegitLookPos(final Box box, final Direction dir, final boolean raycast, final int res, final double extrude) {
        final Vec3d eyePos = RotationUtils.mc.player.getEyePos();
        final Vec3d blockPos = new Vec3d(box.minX, box.minY, box.minZ).add((dir == Direction.WEST) ? (-extrude) : (dir.getOffsetX() * box.getXLength() + extrude), (dir == Direction.DOWN) ? (-extrude) : (dir.getOffsetY() * box.getYLength() + extrude), (dir == Direction.NORTH) ? (-extrude) : (dir.getOffsetZ() * box.getZLength() + extrude));
        for (double i = 0.0; i <= 1.0; i += 1.0 / res) {
            for (double j = 0.0; j <= 1.0; j += 1.0 / res) {
                final Vec3d lookPos = blockPos.add((dir.getAxis() == Direction.Axis.X) ? 0.0 : (i * box.getXLength()), (dir.getAxis() == Direction.Axis.Y) ? 0.0 : ((dir.getAxis() == Direction.Axis.Z) ? (j * box.getYLength()) : (i * box.getYLength())), (dir.getAxis() == Direction.Axis.Z) ? 0.0 : (j * box.getZLength()));
                if (eyePos.distanceTo(lookPos) <= 4.55) {
                    if (!raycast) {
                        return lookPos;
                    }
                    if (RotationUtils.mc.world.raycast(new RaycastContext(eyePos, lookPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity)RotationUtils.mc.player)).getType() == HitResult.Type.MISS) {
                        return lookPos;
                    }
                }
            }
        }
        return null;
    }
    
    public static double getPitch(final Entity entity) {
        return getPitch(entity, Target.Body);
    }
    
    public static Vec2f getPitchYaw(final Vec3d targetV3) {
        return getPitchYawFromOtherEntity(Objects.requireNonNull(RotationUtils.mc.player).getEyePos(), targetV3);
    }
    
    public static Vec2f getPitchYawFromOtherEntity(final Vec3d eyePos, final Vec3d targetV3) {
        final double vec = 57.2957763671875;
        final Vec3d target = targetV3.subtract(eyePos);
        final double square = Math.sqrt(target.x * target.x + target.z * target.z);
        final float pitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(target.y, square) * vec)));
        final float yaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(target.z, target.x) * vec) - 90.0f);
        return new Vec2f(pitch, yaw);
    }
    
    public static void setClientPitch(final float clientPitch) {
        RotationUtils.lastModificationTime = System.currentTimeMillis();
        RotationUtils.clientPitch = clientPitch;
    }
    
    public static void setClientYaw(final float clientYaw) {
        RotationUtils.lastModificationTime = System.currentTimeMillis();
        RotationUtils.clientYaw = clientYaw;
    }
    
    static {
        RotationUtils.mc = MinecraftClient.getInstance();
        rotationPool = new Pool<Rotation>(Rotation::new);
        rotations = new ArrayList<Rotation>();
        RotationUtils.isCustomPitch = false;
        RotationUtils.isCustomYaw = false;
        RotationUtils.lastModificationTime = 0L;
    }
    
    private static class Rotation
    {
        public double yaw;
        public double pitch;
        public int priority;
        public boolean clientSide;
        public Runnable callback;
        
        public void set(final double yaw, final double pitch, final int priority, final boolean clientSide, final Runnable callback) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.priority = priority;
            this.clientSide = clientSide;
            this.callback = callback;
        }
        
        public void sendPacket() {
            RotationUtils.mc.getNetworkHandler().sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround((float)this.yaw, (float)this.pitch, RotationUtils.mc.player.isOnGround()));
            this.runCallback();
        }
        
        public void runCallback() {
            if (this.callback != null) {
                this.callback.run();
            }
        }
    }
    
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
    
   
    public static void rotateP(float yaw, float pitch) {
        startYaw = MinecraftClient.getInstance().player.getYaw();
        startPitch = MinecraftClient.getInstance().player.getPitch();
        float[] rotations = {
        		MinecraftClient.getInstance().player.getYaw()
                        + MathHelper.wrapDegrees(yaw - MinecraftClient.getInstance().player.getYaw()),
                        MinecraftClient.getInstance().player.getPitch() + MathHelper
                        .wrapDegrees(pitch - MinecraftClient.getInstance().player.getPitch())};

        MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], MinecraftClient.getInstance().player.isOnGround()));
    }

	public static float getYaw(float pos) {
		return RotationUtils.mc.player.getYaw();
	}

	public static float getPitch(float pitch) {
		// TODO Auto-generated method stub
		return RotationUtils.mc.player.getPitch();
	}
}
