package xipe.module;

import java.util.ArrayList;
import java.util.List;

import xipe.module.Mod.Category;
import xipe.module.modules.Combat.AimAssist;
import xipe.module.modules.Combat.AutoAnchor;
import xipe.module.modules.Combat.AutoArmor;
import xipe.module.modules.Combat.AutoTotem;
import xipe.module.modules.Combat.BedAura;
import xipe.module.modules.Combat.Criticals;
import xipe.module.modules.Combat.CrystalAura;
import xipe.module.modules.Combat.KillAura;
import xipe.module.modules.Combat.Surround;
import xipe.module.modules.Combat.Trigger;
import xipe.module.modules.Player.AutoLog;
import xipe.module.modules.Player.Blink;
import xipe.module.modules.Player.ClientsideGameMode;
import xipe.module.modules.Player.ElytraReplace;
import xipe.module.modules.Player.FakePlayer;
import xipe.module.modules.Player.FastXP;
import xipe.module.modules.Player.Freecam;
import xipe.module.modules.Player.Scaffold;
import xipe.module.modules.Player.Velocity;
import xipe.module.modules.Player.WaterClutch;
import xipe.module.modules.Render.*;
import xipe.module.modules.exploit.Nuker;
import xipe.module.modules.exploit.SpinCrash;
import xipe.module.modules.exploit.Timer;
import xipe.module.modules.hud.*;
import xipe.module.modules.movement.*;


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
		modules.add(new AutoTotem());
		modules.add(new Gui());
		modules.add(new BedAura());
		modules.add(new Nuker());
		modules.add(new Step());
		modules.add(new ESP());
		modules.add(new Velocity());
		modules.add(new Criticals());
		modules.add(new NoSlowDown());
		modules.add(new ClientsideGameMode());
		modules.add(new XRay());
		modules.add(new HUD());
		modules.add(new Nofall());
		modules.add(new Scaffold());
		modules.add(new Surround());
		modules.add(new AutoAnchor());
		modules.add(new BreadCrumbs());
		modules.add(new ElytraFly());
		modules.add(new Tracers());
		modules.add(new Strafe());
		modules.add(new TargetHud());
		modules.add(new HoleESP());
		modules.add(new Helper());
		modules.add(new Trigger());
		modules.add(new AimAssist());
		modules.add(new FastXP());
		modules.add(new HoleTP());
		modules.add(new AutoLog());
		modules.add(new Dolphin());
		modules.add(new Freecam());
		modules.add(new Blink());
		modules.add(new SpinCrash());
		modules.add(new Timer());
		modules.add(new AirJump());
		modules.add(new GappleCount());
		modules.add(new ElytraReplace());
		modules.add(new AutoArmor());
		modules.add(new Confetti());
		modules.add(new WaterClutch());
		modules.add(new LavaBounce());
		modules.add(new SneakBoost());
		}
	
	public Mod getModuleByName(String moduleName) {
		for(Mod mod : modules) {
			if ((mod.getName().trim().equalsIgnoreCase(moduleName)) || (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
				return mod;
			}
		}
		return null;
	}
}
