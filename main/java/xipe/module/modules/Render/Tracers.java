package xipe.module.modules.Render;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.utils.RenderUtil;

import java.awt.Color;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Tracers extends Mod {
	

	public BooleanSetting players = new BooleanSetting("Players", true);
	public BooleanSetting monsters = new BooleanSetting("Monsters", true);
	public BooleanSetting animals = new BooleanSetting("Animals", true);
	public BooleanSetting passives = new BooleanSetting("Passives", true);
	public BooleanSetting invisibles = new BooleanSetting("Invisibles", true);
	
	public Tracers() {
		super("Tracers", "draw cool lines", Category.RENDER);
		addSettings(players, monsters, animals, passives, invisibles);
	}
    
	@Override
	public void onWorldRender(MatrixStack matrices) {
		for (Entity entity : StreamSupport.stream(mc.world.getEntities().spliterator(), false).sorted(Comparator
                .comparingDouble(value -> -value.distanceTo(mc.player))).collect(Collectors.toList())) {
            if (entity.squaredDistanceTo(mc.player) > 4096) continue;
            double dc = entity.squaredDistanceTo(mc.player) / 4096;
            dc = Math.abs(1 - dc);
            if (entity.getUuid().equals(mc.player.getUuid())) continue;
            Color c = getEntityColor(entity);

            if (shouldRenderEntity(entity)) {

                    RenderUtil.line(RenderUtil.center(), entity.getPos().add(0, entity.getStandingEyeHeight(), 0), c, matrices);
                    //RenderUtils.line(entity.getPos(), entity.getPos().add(0, entity.getStandingEyeHeight(), 0), c, matrices);
            }
        }
		super.onWorldRender(matrices);
	}

	
	
	Vec2f getPY(Vec3d target1) {
        Camera c = mc.gameRenderer.getCamera();
        double vec = 57.2957763671875;
        Vec3d target = target1.subtract(c.getPos());
        double square = Math.sqrt(target.x * target.x + target.z * target.z);
        float pitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(target.y, square) * vec)));
        float yaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(target.z, target.x) * vec) - 90.0F);
        return new Vec2f(pitch, yaw);
    }
	
	public boolean shouldRenderEntity(Entity entity) {
		if (players.isEnabled() && entity instanceof PlayerEntity) return true;
		if (monsters.isEnabled() && entity instanceof Monster) return true;
		if (animals.isEnabled() && entity instanceof AnimalEntity) return true;
		if (passives.isEnabled() && entity instanceof PassiveEntity && !(entity instanceof AnimalEntity)) return true;
		if (invisibles.isEnabled() && entity.isInvisible()) return true;
		return false;
	}
	
	public Color getEntityColor(Entity entity) {
		if (entity instanceof PlayerEntity) return new Color(255, 10, 255, 255);
		if (entity instanceof Monster) return new Color(255, 25, 25, 255);
		if (entity instanceof AnimalEntity) return new Color(255, 25, 25, 255);
		if (entity instanceof PassiveEntity) return new Color(255, 25, 25, 255);
		if (entity.isInvisible()) return new Color(255, 25, 25, 255);
		
		return new Color(255, 255, 255);
	}
}
