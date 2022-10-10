package xipe.utils.render;

import java.util.Objects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Vec3
{
    public double x;
    public double y;
    public double z;
    
    public Vec3() {
    }
    
    public Vec3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec3 set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vec3 set(final Vec3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }
    
    public Vec3 set(final Vec3d vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }
    
    public Vec3 set(final Entity entity, final double tickDelta) {
        this.x = MathHelper.lerp(tickDelta, entity.lastRenderX, entity.getX());
        this.y = MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY());
        this.z = MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ());
        return this;
    }
    
    public Vec3 add(final double x, final double y, final double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3 add(final Vec3 vec) {
        return this.add(vec.x, vec.y, vec.z);
    }
    
    public Vec3 subtract(final double x, final double y, final double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3 subtract(final Vec3d vec) {
        return this.subtract(vec.x, vec.y, vec.z);
    }
    
    public Vec3 multiply(final double x, final double y, final double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3 multiply(final double v) {
        return this.multiply(v, v, v);
    }
    
    public Vec3 divide(final double v) {
        this.x /= v;
        this.y /= v;
        this.z /= v;
        return this;
    }
    
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public double distanceTo(final Vec3 vec) {
        final double d = vec.x - this.x;
        final double e = vec.y - this.y;
        final double f = vec.z - this.z;
        return Math.sqrt(d * d + e * e + f * f);
    }
    
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public Vec3 normalize() {
        return this.divide(this.length());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Vec3 vec3 = (Vec3)o;
        return Double.compare(vec3.x, this.x) == 0 && Double.compare(vec3.y, this.y) == 0 && Double.compare(vec3.z, this.z) == 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }
    
    @Override
    public String toString() {
        return String.format("[%.3f, %.3f, %.3f]", this.x, this.y, this.z);
    }
}


