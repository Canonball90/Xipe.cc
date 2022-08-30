package xipe.ui.screens.clickgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import xipe.Client;
import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.utils.Timer;

public class ClickGUI extends Screen {

	public static final ClickGUI INSTANCE = new ClickGUI();
	
	private List<Frame> frames;
	private ArrayList<Snow> _snowList;
	
	private ClickGUI() {
		super(Text.literal("Click GUI"));
		
		frames = new ArrayList();
		
		 this._snowList = new ArrayList<Snow>();
		           //20
		int offset = 60;
		for(Category category : Category.values()) {
			frames.add(new Frame(category, offset, 50, 140, 20));
			offset += 150;
		}
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		if(pTimer.hasTimeElapsed(100, true)) {
			particles.add(new Particle());
			particles.add(new Particle());
		}
		
		for(Frame frame : frames) {
			frame.render(matrices, mouseX, mouseY, delta);
			frame.updatePosition(mouseX, mouseY);
		}
		if (!this._snowList.isEmpty() && ModuleManager.INSTANCE.getModule(Gui.class).snow.isEnabled()) {
            this._snowList.forEach(snow -> snow.Update(matrices));
        }
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for(Frame frame : frames) {
			frame.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for(Frame frame : frames) {
			frame.mouseReleased(mouseX, mouseY, button);
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	class Particle {
		public int x, y;

		public Particle() {
			this.x = Client.INSTANCE.getRandomNumber(0, width);
			this.y = 0;
		}

		int yTicks = 0;
		public void render(MatrixStack matrices) {
			yTicks++;
			DrawableHelper.fill(matrices, x, y + yTicks, x + 10, y + 10 + yTicks, -1);
		}
	}
	List<Particle> particles = new ArrayList<>();
	Timer pTimer = new Timer();
	
	 @Override
	    public boolean shouldPause() {
	        return false;
	    }
	 private void load() {
	        final Random random = new Random();
	        for (int i = 0; i < 100; ++i) {
	            for (int y = 0; y < 3; ++y) {
	                final Snow snow = new Snow(25 * i, y * -50, random.nextInt(3) + 1, random.nextInt(2) + 1);
	                this._snowList.add(snow);
	            }
	        }
	 }
	 
	 @Override
		public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
			for (Frame frame : frames) {                      //5
				if (amount > 0) frame.setY((int) (frame.getY() + ModuleManager.INSTANCE.getModule(Gui.class).scrollSpeed.getValue()));
				else if (amount < 0) frame.setY((int) (frame.getY() - ModuleManager.INSTANCE.getModule(Gui.class).scrollSpeed.getValue()));
			}
			return super.mouseScrolled(mouseX, mouseY, amount);
		}
	 
	 public void categoryIcons() {
		 
	 }
}
