package xipe.ui.screens.clickgui.setting;

import java.awt.Color;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.Setting;
import xipe.ui.screens.clickgui.ModuleButton;
import xipe.utils.FontRenderer;

public class CheckBox extends Component{

	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	private BooleanSetting boolset = (BooleanSetting)setting;
	public int color;
	public int color2;
	
	public CheckBox(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
		this.boolset = (BooleanSetting)setting;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		this.color = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 255).getRGB();
		this.color2 = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 150).getRGB();
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, color);
				}else if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, rainbow(300));
				}else
		DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, Color.pink.getRGB());
			}
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("OldSchool")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset -1, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height +1, Color.gray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Akuna")) {
			DrawableHelper.fill(matrices, parent.parent.x - 1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width + 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(94,255,175).getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Test")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
			DrawableHelper.fill(matrices, parent.parent.x - 1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width + 1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.blue.getRGB());
			}
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}

		int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		//mc.textRenderer.drawWithShadow(matrices, boolset.getName() + ": " + boolset.isEnabled(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, -1);
		
		 this.renderCheckBox(matrices, ((BooleanSetting) setting).isEnabled(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset);
		
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(isHovered(mouseX, mouseY) && button == 0) {
			boolset.toggle();
		}
		super.mouseClicked(mouseX, mouseY, button);
	}
	
	 public void renderCheckBox(MatrixStack matrixStack, Boolean checked, int x, int y){
		 
		 int offsetY = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		 
		 if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
			 nuitofont.draw(matrixStack, boolset.getName(), parent.parent.x + offsetY + 14, parent.parent.y-6 + parent.offset + offset + offsetY + 1, -1, false);
		 }else {
	        mc.textRenderer.drawWithShadow(matrixStack, boolset.getName(), x + 14, y + 1, new Color(255,255,255).getRGB(), false);
		 }
	        DrawableHelper.fill(matrixStack, x, y, x + 10, y + 10,  Color.gray.getRGB());
	        if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Test") && checked) {
	        	
	        	DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8,  Color.blue.getRGB());
	        	
	        }else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original") && checked) {
	        	
	        	if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
	        		DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8, color);
	        	}else if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
	        		DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8, rainbow(300));
	        	}else
	        	DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8,  Color.pink.getRGB());
	        	
	        }else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("OldSchool")) {
	        	
	        	DrawableHelper.fill(matrixStack, x, y, x + 10, y + 10,  Color.darkGray.getRGB());
	        	if(checked) {
	        	DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8,  Color.red.getRGB());
	        	}
	        }else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
	        	
	        	DrawableHelper.fill(matrixStack, x, y, x + 10, y + 10,  Color.darkGray.getRGB());
	        	if(checked) {
	        	DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8,  Color.blue.getRGB());
	        	}
	        }else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Akuna") && checked) {
	        	DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8,  new Color(94,255,175).getRGB());
	        }
	       // if(checked) DrawableHelper.fill(matrixStack, x + 2, y + 2, x + 8, y + 8,  Color.pink.getRGB());
	    }
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
	

}
