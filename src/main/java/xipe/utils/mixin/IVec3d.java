package xipe.utils.mixin;

import net.minecraft.util.math.Vec3i;
import xipe.utils.render.Vec3;

public interface IVec3d
{
    void set(final double p0, final double p1, final double p2);
    
    default void set(final Vec3i vec) {
        this.set(vec.getX(), vec.getY(), vec.getZ());
    }
    
    default void set(final Vec3 vec) {
        this.set(vec.x, vec.y, vec.z);
    }
    
    void setXZ(final double p0, final double p1);
    
    void setY(final double p0);
}
