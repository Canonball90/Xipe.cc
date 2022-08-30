package xipe.module.modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;
import xipe.utils.RenderUtil;

public class BreadCrumbs extends Mod{

	private Color currentColor;
	private Color color;
	//private RainbowColor rainbowColor;
	
	NumberSetting hue = new NumberSetting("",0,0,0,0);
	NumberSetting effectSpeed = new NumberSetting("",0,0,0,0);
	
	
	private float timer = 10;
	private float currentTick = 0;
	private List<Vec3d> positions = new ArrayList<Vec3d>();
	
	public BreadCrumbs() {
		super("BreadCrumbs", "Draws Line behind u", Category.RENDER);
	}
	
	@Override
	public void onEnable() {
		super.onTick();
		super.onEnable();
	}
	
	@Override
	public void onTick() {
		currentTick++;
		if(timer == currentTick) {
			currentTick = 0;
			positions.add(mc.player.getPos());
		}
	}

	public void onRender(MatrixStack matrixStack, float partialTicks) {
		for(int i = 0; i < this.positions.size() - 1; i++) {
			RenderUtil.drawLine3D(matrixStack, this.positions.get(i), this.positions.get(i + 1), this.currentColor);
		}
	}

}
