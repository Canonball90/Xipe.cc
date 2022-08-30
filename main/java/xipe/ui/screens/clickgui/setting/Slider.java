package xipe.ui.screens.clickgui.setting;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.settings.NumberSetting;
import xipe.module.settings.Setting;
import xipe.ui.screens.clickgui.ModuleButton;
import xipe.utils.FontRenderer;
import xipe.utils.RenderUtil;

public class Slider extends Component{

	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	public NumberSetting numSet = (NumberSetting)setting;
	
	public int color;
	public int color2;
	
	private boolean sliding = false;
	
	public Slider(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
		this.numSet= (NumberSetting)setting;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		this.color = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 150).getRGB();
		this.color2 = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 255).getRGB();
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, rainbow(300));
						}else if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
							DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, color2);
						}else
		DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, Color.pink.getRGB());
			}
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("OldSchool")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset -1, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height +1, Color.gray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Test")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.blue.getRGB());
			}
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Akuna")) {
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(94,255,175).getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}
		
		double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
		int renderWidth = (int)(parent.parent.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
				DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset +13, parent.parent.x + renderWidth + 1, parent.parent.y + parent.offset + offset + parent.parent.height -5, color2);
					}else if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
						DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset +13, parent.parent.x + renderWidth + 1, parent.parent.y + parent.offset + offset + parent.parent.height -5, rainbow(300));
					}else
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +13, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -5, Color.pink.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("OldSchool")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +12, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -4, Color.red.getRGB());	
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +12, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -4, Color.blue.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Test")) {
			RenderUtil.renderRoundedQuad(matrices, Color.blue, parent.parent.x, parent.parent.y + parent.offset + offset +13,parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -5, 1, 5);
			//DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +13, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -5, Color.blue.getRGB());
			}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Akuna")) {
				RenderUtil.renderRoundedQuad(matrices, new Color(94,255,175), parent.parent.x, parent.parent.y + parent.offset + offset +13,parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -5, 1, 5);
				//DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +13, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height -5, Color.blue.getRGB());
				}
		
		if(sliding) {
			if(diff == 0) {
				numSet.setValue(numSet.getMin());
			} else {
				numSet.setValue(roundToPlace(((diff / parent.parent.width) * (numSet.getMax() - numSet.getMin()) + numSet.getMin()), 2));
			}
		}
		
		int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
			nuitofont.draw(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2), parent.parent.x + textOffset, parent.parent.y-6 + parent.offset + offset + textOffset - 3, -1, false);
		}else {
		mc.textRenderer.drawWithShadow(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset -3, -1);
		}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(isHovered(mouseX, mouseY)) sliding = true;
		super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if(isHovered(mouseX, mouseY)) sliding = false;
		super.mouseReleased(mouseX, mouseY, button);
	}
	
	private double roundToPlace(double value, int place) {
		if(place < 0) {
			return value;
		}
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(place, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
	

}
