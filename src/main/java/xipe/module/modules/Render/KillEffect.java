package xipe.module.modules.Render;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;

public class KillEffect extends Mod{
	
    public BooleanSetting thunder = new BooleanSetting("Thunder", true);
    public BooleanSetting firework = new BooleanSetting("Firework", false);
    public BooleanSetting arrow = new BooleanSetting("Arrow", false);
    public BooleanSetting areaCloud = new BooleanSetting("Area Effect Cloud", false);
    public BooleanSetting bat = new BooleanSetting("Bat", false);
    public BooleanSetting exp = new BooleanSetting("Exp", false);
    public BooleanSetting fire = new BooleanSetting("FireBall", false);
    public BooleanSetting random = new BooleanSetting("Random", true);
    
    public NumberSetting thunderCount = new NumberSetting("Count", 1, 100, 1, 1);
    public NumberSetting scale = new NumberSetting("Scale", 0.01, 2, 0.1, 1);
    public NumberSetting scaleY = new NumberSetting("Scale Y",  0.01, 3, 0.1, 1);
    public NumberSetting offset = new NumberSetting("Offset", 0.1, 2, 0.5, 1);

    private ArrayList<PlayerEntity> playersDead = new ArrayList<>();

	public KillEffect() {
		super("KillEffect", "Shows effect on any player's death", Category.RENDER);
		addSettings(thunder,firework,arrow,areaCloud,bat,exp,random,thunderCount,scale,scaleY,offset);
	}

	 @Override
	    public void onEnable() {
	        playersDead.clear();
	    }

	 @Override
	    public void onTick(){
	        if (mc.world == null) {
	            playersDead.clear();
	            return;
	        }

	        try {
	            if (thunder.isEnabled()) mc.world.getPlayers().forEach(this::create);
	            if (firework.isEnabled()) mc.world.getPlayers().forEach(this::create1);
	            if (arrow.isEnabled()) mc.world.getPlayers().forEach(this::create2);
	            if (areaCloud.isEnabled()) mc.world.getPlayers().forEach(this::create3);
	            if (bat.isEnabled()) mc.world.getPlayers().forEach(this::create4);
	            if (exp.isEnabled()) mc.world.getPlayers().forEach(this::create5);
	            if (fire.isEnabled()) mc.world.getPlayers().forEach(this::create6);
	        }catch (ConcurrentModificationException ignored){}
	    }

	    public void create(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                    LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, mc.world);
	                    if (!random.isEnabled()) lightningEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        lightningEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(lightningEntity.getId(), lightningEntity);
	                }
	                playersDead.add(entity);
	            }
	        }

	    }
	    
	    public void create1(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                    FireworkRocketEntity fireEntity = new FireworkRocketEntity(EntityType.FIREWORK_ROCKET, mc.world);
	                    if (!random.isEnabled()) fireEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        fireEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(fireEntity.getId(), fireEntity);
	                }
	                playersDead.add(entity);
	            }
	        }

	    }
	    
	    public void create2(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                    ArrowEntity fireEntity = new ArrowEntity(EntityType.ARROW, mc.world);
	                    if (!random.isEnabled()) fireEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        fireEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(fireEntity.getId(), fireEntity);
	                }
	                playersDead.add(entity);
	                playersDead.remove(entity);
	            }
	        }

	    }
	    
	    public void create3(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                    AreaEffectCloudEntity fireEntity = new AreaEffectCloudEntity(EntityType.AREA_EFFECT_CLOUD, mc.world);
	                    if (!random.isEnabled()) fireEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        fireEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(fireEntity.getId(), fireEntity);
	                }
	                playersDead.add(entity);
	                playersDead.remove(entity);
	            }
	        }

	    }
	    
	    public void create4(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                    BatEntity fireEntity = new BatEntity(EntityType.BAT, mc.world);
	                    if (!random.isEnabled()) fireEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        fireEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(fireEntity.getId(), fireEntity);
	                }
	                playersDead.add(entity);
	                playersDead.remove(entity);
	            }
	        }

	    }
	    
	    public void create5(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                	ExperienceOrbEntity fireEntity = new ExperienceOrbEntity(EntityType.EXPERIENCE_ORB, mc.world);
	                    if (!random.isEnabled()) fireEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        fireEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(fireEntity.getId(), fireEntity);
	                }
	                playersDead.add(entity);
	                playersDead.remove(entity);
	            }
	        }

	    }
	    
	    public void create6(PlayerEntity entity){
	        if (playersDead.contains(entity)) {
	            if (entity.getHealth() > 0)
	                playersDead.remove(entity);
	        }
	        else {
	            if (entity.getHealth() == 0){
	                for(int i = 0; i < thunderCount.getValue(); i++){
	                	FireballEntity fireEntity = new FireballEntity(EntityType.FIREBALL, mc.world);
	                    if (!random.isEnabled()) fireEntity.setPosition(entity.getX(), entity.getY(), entity.getZ());
	                    else {
	                        Random random1 = new Random();
	                        double x = entity.getX() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double y = entity.getY() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        double z = entity.getZ() + random1.nextDouble(-offset.getValue(), offset.getValue());
	                        fireEntity.setPosition(x, y, z);
	                    }
	                    mc.world.addEntity(fireEntity.getId(), fireEntity);
	                }
	                playersDead.add(entity);
	            }
	        }

	    }
	    
}
