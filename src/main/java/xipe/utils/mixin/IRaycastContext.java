package xipe.utils.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.util.math.Vec3d;

public interface IRaycastContext
{
    void set(final Vec3d p0, final Vec3d p1, final ShapeType collider, final RaycastContext.FluidHandling p3, final Entity p4);
}
