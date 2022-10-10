package xipe.module.modules.Render;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.render.QuadColor;
import xipe.utils.render.RenderUtils;
import xipe.utils.world.WorldUtil;

public class StorageESP extends Mod{
	
	 public NumberSetting width;
	 float[] color  = new float[] {127f, 0f, 127f};
	 public BooleanSetting chest;
	 public BooleanSetting eChest;
	 public BooleanSetting shulker;
	 public BooleanSetting furnace;
	 public BooleanSetting hopper;

	public StorageESP() {
		super("StorageESP", "Allows you to see storage shit better", Category.RENDER);
        this.width = new NumberSetting("Width", 0.0, 20.0, 2.0, 1.0);
        this.chest = new BooleanSetting("Chest", true);
        this.eChest = new BooleanSetting("E-Chest", true);
        this.shulker = new BooleanSetting("Shulker", true);
        this.furnace = new BooleanSetting("Furnace", true);
        this.hopper = new BooleanSetting("Hopper", true);
        addSettings(width,chest,eChest,shulker,furnace,hopper);
	}
	
	  @Override
	    public void onWorldRender(MatrixStack matrices) {
	    for (BlockEntity block : WorldUtil.getBlockEntities()) {
	    	if(chest.isEnabled()) {
	    if (block instanceof ChestBlockEntity) {
	    	 RenderUtils.drawBoxBoth(new Box(
						block.getPos().getX()+0.06,
						block.getPos().getY(),
						block.getPos().getZ()+0.06,
						block.getPos().getX()+0.94,
						block.getPos().getY()+0.88,
						block.getPos().getZ()+0.94), QuadColor.single(1f, 1f, 0f, 0.3f), 1);
 }
	    	}
	    if(eChest.isEnabled()) {
	    RenderUtils.drawBoxBoth(new Box(
	    						block.getPos().getX()+0.06,
	    						block.getPos().getY(),
	    						block.getPos().getZ()+0.06,
	    						block.getPos().getX()+0.94,
	    						block.getPos().getY()+0.88,
	    						block.getPos().getZ()+0.94), QuadColor.single(1f, 0f, 1f, 0.3f), 1);
		    }
	    if(shulker.isEnabled()) {
	    	  if (block instanceof ShulkerBoxBlockEntity) {
	    		  RenderUtils.drawBoxBoth(new Box(
  						block.getPos().getX()+0.06,
  						block.getPos().getY(),
  						block.getPos().getZ()+0.06,
  						block.getPos().getX()+0.94,
  						block.getPos().getY()+0.88,
  						block.getPos().getZ()+0.94), QuadColor.single(1f, 1f, 1f, 0.3f), 1);
	    }
			    }
	    if(furnace.isEnabled()) {
	    	  if (block instanceof FurnaceBlockEntity) {
	    		  RenderUtils.drawBoxBoth(new Box(
  						block.getPos().getX()+0.06,
  						block.getPos().getY(),
  						block.getPos().getZ()+0.06,
  						block.getPos().getX()+0.94,
  						block.getPos().getY()+0.88,
  						block.getPos().getZ()+0.94), QuadColor.single(1f, 0f, 0f, 0.3f), 1);
	    }
			    }
	    if(hopper.isEnabled()) {
	    	  if (block instanceof HopperBlockEntity) {
	    		  RenderUtils.drawBoxBoth(new Box(
  						block.getPos().getX()+0.06,
  						block.getPos().getY(),
  						block.getPos().getZ()+0.06,
  						block.getPos().getX()+0.94,
  						block.getPos().getY()+0.88,
  						block.getPos().getZ()+0.94), QuadColor.single(0f, 0f, 1f, 0.3f), 1);
	    }
			    }
	   
	    	}
	    }
	  }



