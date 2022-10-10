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
import xipe.utils.font.FontRenderer;

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
					DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width + 1, parent.parent.y + parent.offset + offset + parent.parent.height, rainbow(300));
						}else if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
							DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, color2);
						}else
							if (parent.parent.category.name == "Movement") {
								DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(61,29,158).getRGB());
								}else if (parent.parent.category.name == "Combat") {
						        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(213,100,100).getRGB());
						        }else if (parent.parent.category.name == "Render") {
						        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(158,28,158).getRGB());
						        }else if (parent.parent.category.name == "Exploit") {
						        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(43,169,102).getRGB());
						        }else if (parent.parent.category.name == "World") {
						        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.orange.getRGB());
						        }else if (parent.parent.category.name == "Hud") {
						        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(255,35,35).getRGB());
						        }
				}
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(52,52,52).getRGB());
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +3, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height - 4,ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled() ? color : new Color(151, 83, 211).getRGB());
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +4, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height - 5, Color.gray.getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0,0,0,190).getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.lightGray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
		}
		double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
		int renderWidth = (int)(parent.parent.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
				DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +4, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height - 5, color2);
					}else if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
						DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +4, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height - 5, rainbow(300));
					}else
						DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +4, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height - 5, new Color(151, 83, 211).getRGB());
			}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
				DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +4, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height - 5, new Color(171, 103, 231).getRGB());
			}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
				DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset +4, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height - 5, Color.blue.getRGB());
			}
		
		if(sliding) {
			if(diff == 0) {
				numSet.setValue(numSet.getMin());
			} else {
				numSet.setValue(roundToPlace(((diff / parent.parent.width) * (numSet.getMax() - numSet.getMin()) + numSet.getMin()), 2));
			}
		}
		
		int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		//nuitofont.draw(matrices, "|", parent.parent.x + textOffset + 125, parent.parent.y-6 + parent.offset + offset + textOffset, -1, false);
		if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
			nuitofont.draw(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2), parent.parent.x + textOffset, parent.parent.y-6 + parent.offset + offset + textOffset, -1, false);
		}else {
			mc.textRenderer.drawWithShadow(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2), parent.parent.x + textOffset, parent.parent.y + 3 + parent.offset + offset + textOffset -3, -1);
		}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	 @Override
	    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
	        if (this.isHovered(mouseX, mouseY)) {
	            this.sliding = true;
	        }
	        super.mouseClicked(mouseX, mouseY, button);
	    }
	    
	    @Override
	    public void mouseReleased(final double mouseX, final double mouseY, final int button) {
	        this.sliding = false;
	        super.mouseReleased(mouseX, mouseY, button);
	    }
	    
	    private double roundToPlace(final double value, final int place) {
	        if (place < 0) {
	            return value;
	        }
	        BigDecimal bd = new BigDecimal(value);
	        bd = bd.setScale(place, RoundingMode.HALF_UP);
	        return bd.doubleValue();
	    }
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
	

}
