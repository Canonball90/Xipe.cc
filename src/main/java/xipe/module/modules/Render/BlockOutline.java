package xipe.module.modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xipe.event.EventTarget;
import xipe.event.events.EventRender3D;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.render.QuadColor;
import xipe.utils.render.RenderUtils;
import net.minecraft.util.hit.HitResult.Type;

public class BlockOutline extends Mod{

	 final List<BlockPos> renders;
    public NumberSetting r;
    public NumberSetting g;
    public NumberSetting b;
    public NumberSetting width;
    public BooleanSetting rainbow;
    public ModeSetting mode;
    public Color color;
	
	public BlockOutline() {
		super("BlockOutline", "Outlines the block you're looking at", Category.RENDER);
		this.renders = new ArrayList<BlockPos>();
		 	this.r = new NumberSetting("Red", 0.0, 255.0, 0.0, 1.0);
	        this.g = new NumberSetting("Green", 0.0, 255.0, 0.0, 1.0);
	        this.b = new NumberSetting("Blue", 0.0, 255.0, 0.0, 1.0);
	        this.width = new NumberSetting("Width", 0.0, 20.0, 2.0, 1.0);
	        this.rainbow = new BooleanSetting("RainBow", true);
	        this.mode = new ModeSetting("Render Mode", "Outline", "Outline", "Fill", "Both");
	        this.addSettings(this.r, this.g, this.b,this.rainbow, this.width,this.mode);
	}
	
	@Override
	public void onWorldRender(MatrixStack matrices)  {
		HitResult target = mc.crosshairTarget;
		if (target != null) {
			if (target.getType() == Type.BLOCK) {
				BlockPos pos = (BlockPos) ((BlockHitResult) target).getBlockPos();
				Color c = color;
				if(rainbow.isEnabled()) {
					if(mode.isMode("Outline")) {
						RenderUtils.drawBoxOutline1(pos, QuadColor.single(rainbow(300)), (int) width.getValue());
					}
					if(mode.isMode("Fill")) {
						RenderUtils.drawBoxBoth(pos, QuadColor.single(r.getValueFloat(), g.getValueFloat(), b.getValueFloat(), 200), (int) width.getValue());
					}
				}else {
					RenderUtils.drawBoxOutline1(pos, QuadColor.single(r.getValueFloat(), g.getValueFloat(), b.getValueFloat(), 1), (int) width.getValue());
				}
			}
		}
	}

	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}
