package xipe.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import xipe.module.Mod.Category;
import xipe.module.modules.TestModule;
import xipe.module.modules.Combat.*;
import xipe.module.modules.Player.*;
import xipe.module.modules.Render.*;
import xipe.module.modules.exploit.*;
import xipe.module.modules.hud.*;
import xipe.module.modules.movement.*;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.KeybindSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.module.settings.Setting;


public class ModuleManager {

	public static final ModuleManager INSTANCE = new ModuleManager();
	private List<Mod> modules =  new ArrayList<>();
	
	public ModuleManager() {
		addModules();
	}
	
	public List<Mod> getEnabledModules(){
		List<Mod> enabled = new ArrayList<>();
		for (Mod module : modules) {
			if(module.isEnabled()) enabled.add(module);
		}
		
		return enabled;
	}
	
	public List<Mod> getModules() {
		return modules;
	}
	
	 public int getAmountPerCat(Mod.Category category) {
	        int moduleTot = 0;
	        for (Mod module : getModules()) {
	            if (module.getCategory() == category) {
	                moduleTot++;
	            }
	        }
	        return moduleTot;
	    }
	
	@SuppressWarnings("unchecked")
	public <T extends Mod> T getModule(Class<T> clazz) { 
	    return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null); 
	}
	  
	public List<Mod> getModulesInCategory(Category category){
		List<Mod> categoryModules = new ArrayList<>();
		
		for (Mod mod : modules) {
			if(mod.getCategory() == category) {
				categoryModules.add(mod);
			}
		}
		
		return categoryModules;
	}
	
	private void addModules() {
		modules.add(new Flight());
		modules.add(new Sprint());
		modules.add(new KillAura());
		modules.add(new FakePlayer());
		modules.add(new CrystalAura());
		modules.add(new Offhand());
		modules.add(new Gui());
		modules.add(new Nuker());
		modules.add(new Step());
		modules.add(new ESP());
		modules.add(new Criticals());
		modules.add(new ClientsideGameMode());
		modules.add(new XRay());
		modules.add(new HUD());
		modules.add(new Nofall());
		modules.add(new Scaffold());
		modules.add(new Surround());
		modules.add(new AutoAnchor());
		modules.add(new Tracers());
		modules.add(new Strafe());
		modules.add(new TargetHud());
		modules.add(new HoleESP());
		modules.add(new Helper());
		modules.add(new Trigger());
		modules.add(new NoSlowDown());
		modules.add(new AimAssist());
		modules.add(new FastUse());
		modules.add(new HoleTP());
		modules.add(new AutoLog());
		modules.add(new Jesus());
		modules.add(new Blink());
		modules.add(new SpinCrash());
		modules.add(new Timer());
		modules.add(new AirJump());
		modules.add(new ElytraReplace());
		modules.add(new AutoArmor());
		modules.add(new Confetti());
		modules.add(new LiquidBounce());
		modules.add(new Disabler());
		modules.add(new FullBright());
		modules.add(new ItemViewModel());
		modules.add(new Landing());
		modules.add(new MountBypass());
		modules.add(new SessionInfo());
		modules.add(new OnlineTime());
		modules.add(new TestModule());
		modules.add(new BlockOutline());
		modules.add(new FastBreak());
		modules.add(new StorageESP());
		modules.add(new AutoWalk());
		modules.add(new EntityFly());
		modules.add(new SlotRandomizer());
		modules.add(new ElytraFly());
		modules.add(new LongJump());
		modules.add(new KillEffect());
		modules.add(new LSD());
		modules.add(new Ambience());
		modules.add(new DamageTint());
		modules.add(new Speed());
		modules.add(new TrueSight());
		modules.add(new Velocity());
		modules.add(new Indicators());
		modules.add(new Notifications());
		modules.add(new Warning());
		modules.add(new Coords());
		modules.add(new ChestStealer());
		modules.add(new PacketLogger());
		modules.add(new AntiAim());
		modules.add(new Chams());
		modules.add(new FastProjectile());
		modules.add(new FastPlace());
		modules.add(new SelfFill());
		modules.add(new NoRender());
		modules.add(new TridentPlus());
		modules.add(new PotionHud());
		modules.add(new Spammer());
		modules.add(new Quiver());
		modules.add(new ClickTP());
		modules.add(new FastStop());
		modules.add(new ArrowJuke());
		modules.add(new VerticalPhase());
		modules.add(new BoatPhase());
		modules.add(new MoonGravity());
		modules.add(new AutoTool());
		modules.add(new TNTimer());
		modules.add(new Search());
		modules.add(new HighJump());
		modules.add(new Trail());
		}
	
	public Mod getModuleByName(String moduleName) {
		for(Mod mod : modules) {
			if ((mod.getName().trim().equalsIgnoreCase(moduleName)) || (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
				return mod;
			}
		}
		return null;
	}
	
	public ArrayList<Mod> getAllModules() {
		ArrayList<Mod> mods = new ArrayList<>();
		for (Mod mod : modules) {
			mods.add(mod);
		}
		return mods;
	}
}
