package xipe.module.modules.Combat;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import xipe.event.EventTarget;
import xipe.event.events.EventRender3D;
import xipe.event.events.EventSendPacket;
import xipe.mixins.PlayerMoveC2SPacketAccessor;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.player.RotationUtils;
import xipe.utils.render.RenderUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Comparator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import com.google.common.collect.Lists;

import java.awt.Color;
import java.util.ArrayList;

public class KillAura extends Mod
{
    public static ArrayList<String> modes;
    public static ModeSetting mode;
    public static ModeSetting rotationmode;
    public static NumberSetting range;
    public static BooleanSetting cooldown;
    public static ModeSetting priority;
    public static BooleanSetting render;
    public static ModeSetting critMode;
    public static BooleanSetting switchItem;
    
    //ToDo: Make it render the target
    
    public static KillAura instance;
    
    public KillAura() {
        super("KillAura", "Automatically attacks entities for you", Category.COMBAT);
        this.addSettings(KillAura.mode, KillAura.rotationmode, KillAura.critMode, KillAura.range, KillAura.cooldown, KillAura.priority,KillAura.render,KillAura.switchItem);
        instance = this;
    }
    
    private static final Formatting Gray = Formatting.GRAY;
    
    @Override
    public void onTick() {
    	this.setDisplayName("KillAura" + Gray + "["+mode.getMode()+"]");
        if (this.isEnabled() && this.mc.world != null) {
           final List<LivingEntity> targets = (List)Lists.newArrayList();
            if (this.mc.world.getEntities() != null) {
                for (final Entity e : this.mc.world.getEntities()) {
                    if (e instanceof LivingEntity && e != this.mc.player && this.mc.player.distanceTo(e) <= KillAura.range.getValue()) {
                        targets.add((LivingEntity)e);
                    }
                    else if (targets.contains(e)) {
                        targets.remove(e);
                    }
                    if (KillAura.priority.isMode("Distance")) {
                        targets.sort(Comparator.comparingDouble(entity -> this.mc.player.distanceTo(e)).reversed());
                    }
                    else {
                        if (!KillAura.priority.isMode("Health")) {
                            continue;
                        }
                        targets.sort(Comparator.comparingDouble(entity -> ((LivingEntity) entity).getHealth()).reversed());
                    }
                }
                if (targets.size() - 1 >= 0) {
                	 final float pitch = RotationUtils.getRotations(targets.get(0))[1];
                     final float yaw = RotationUtils.getRotations(targets.get(0))[0];
                    if (KillAura.mode.getMode() == "Camera" && targets.get(0).isAlive()) {
                        if (KillAura.rotationmode.isMode("Legit")) {
                        	
                            this.mc.player.setYaw(yaw);
                            this.mc.player.setPitch(pitch);
                        }
                        else if (KillAura.rotationmode.isMode("Silent")) {
                            RotationUtils.setSilentPitch(pitch);
                            RotationUtils.setSilentYaw(yaw);
                        }
                        if (!KillAura.cooldown.isEnabled() || this.mc.player.getAttackCooldownProgress(0.5f) == 1.0f ) {
                        	if(mc.player.isOnGround() && critMode.isMode("Jump")) {
                      			mc.player.setVelocity(0, 0.15, 0);
                      		}
                        	
                        	if (switchItem.isEnabled()) {
    		                    for (int i = 0; i < 9; i++) {
    		                        if (mc.player.getInventory().getStack(i).getItem() instanceof SwordItem)
    		                            mc.player.getInventory().selectedSlot = i;
    		                    }
    		                }
                        	
                            this.mc.interactionManager.attackEntity((PlayerEntity)this.mc.player, (Entity)targets.get(0));
                            this.mc.player.swingHand(Hand.MAIN_HAND);
                            this.resetRotation();
                        }
                    }
                    if (KillAura.mode.getMode() == "Packet") {
                    	
                    }
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.resetRotation();
        super.onDisable();
    }
    
    public void resetRotation() {
        RotationUtils.resetPitch();
        RotationUtils.resetYaw();
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
    	 final List<LivingEntity> targets = (List)Lists.newArrayList();
    	 
    	  if (this.mc.world.getEntities() != null && render.isEnabled()) {
              for (final Entity e : this.mc.world.getEntities()) {
            	  if (e instanceof LivingEntity && e != this.mc.player && this.mc.player.distanceTo(e) <= KillAura.range.getValue()) {
                      targets.add((LivingEntity)e);
                  } else if (targets.contains(e)) {
                      targets.remove(e);
                  }
            	  Vec3d renderPos = RenderUtils.getEntityRenderPosition(e, EventRender3D.getTickDelta());
            	  if(render.isEnabled()) {
            	  RenderUtils.drawEntityBox(matrices, e, renderPos.x, renderPos.y, renderPos.z, new Color(230, 30, 30));
            	  }
              }
              }
    	super.onWorldRender(matrices);
    }

    static {
        KillAura.modes = new ArrayList<String>();
        KillAura.mode = new ModeSetting("Mode", "Camera", new String[] { "Camera", "Packet" });
        KillAura.rotationmode = new ModeSetting("Rotation", "Silent", new String[] { "Silent", "Legit" });
        KillAura.range = new NumberSetting("Range", 3.0, 6.0, 4.0, 0.1);
        KillAura.cooldown = new BooleanSetting("Cooldown", true);
        KillAura.priority = new ModeSetting("Priority", "Random", new String[] { "Random", "Random" });
        KillAura.render = new BooleanSetting("Render", false);
        KillAura.critMode = new ModeSetting("Crit Mode", "Jump", "Jump", "Soon");
        KillAura.switchItem = new BooleanSetting("Switch", true);
    }
}