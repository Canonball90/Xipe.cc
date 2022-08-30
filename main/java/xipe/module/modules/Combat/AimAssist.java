package xipe.module.modules.Combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import xipe.module.Mod;
import xipe.utils.RotationUtil;

public class AimAssist extends Mod {

	static PlayerEntity target = null;
	
	public AimAssist() {
		super("AimAssist", "Aim's for you", Category.COMBAT);
	}
	
	
	@Override
	public void onTick() {
		
		HitResult hit = mc.crosshairTarget;
		
		if (mc.player != null) {
			if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
			    if (((EntityHitResult) hit).getEntity() instanceof PlayerEntity player) {
			        target = player;
			    }
			} else if (target == null) return;
			
			int maxDistance = 8;
			
			if (!(target == null)) {
				if (target.isDead() || mc.player.squaredDistanceTo(target) > maxDistance) target = null;
			}
			
			if (target != null) {

				mc.player.setYaw(newYAW());
				mc.player.setPitch(newPITCH());

			}
		}
		
		super.onTick();
	}


	private float newYAW() {
		return RotationUtil.getRotationFromPosition((double) target.getX(), (double) target.getZ(), (double) target.getY()+1)[0];
	}
	
	private float newPITCH() {
		return RotationUtil.getRotationFromPosition((double) target.getX(), (double) target.getZ(), (double) target.getY()+1)[1];
	}
}
