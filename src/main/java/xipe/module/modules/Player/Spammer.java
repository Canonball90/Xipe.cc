package xipe.module.modules.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import xipe.module.Mod;
import xipe.module.settings.NumberSetting;
import xipe.utils.world.TimerUtil;

public class Spammer  extends Mod {
    public NumberSetting delay = new NumberSetting("Delay", 0, 20.0, 5.0, 0.1);
    public int msgCount = 0;
    public Map<Integer, String> customMessages = new HashMap<>();
    public Map<Integer, String> randomShit = new HashMap<>();
    public Map<Integer, String> facts = new HashMap<>();
    public Map<Integer, String> allMessages = new HashMap<>();
    public static TimerUtil delayTimer = new TimerUtil();
    public File spammerFile = new File("spammer.txt");
    
    public Spammer() {
        super("Spammer", "Spams the chat with messages", Category.WORLD);
        this.addSettings(delay);
        
        try {
			spammerFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        facts.put(1, "Xipe.cc is proven to be 200% cooler than other clients");
        facts.put(2, "Xipe.cc has advanced auto crystal that will butt fuck you enemies");
        facts.put(3, "Xipe.cc is sexy");
        facts.put(4, "Xipe.cc is custom base!");
        facts.put(5, "Xipe.cc is best client ever");
        facts.put(5, "Xipe.cc is client with modules in it");
        
        facts.forEach(allMessages::put);
        randomShit.forEach(allMessages::put);
    }
    
    @Override
    public void onEnable() {
    	try {
    		loadCustomMessages();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	super.onEnable();
    }

    public void onTick() {
        if (delayTimer.hasTimeElapsed((long)delay.getValue() * 1000, true)) {
        	mc.player.sendChatMessage(facts.toString());
        }
        super.onTick();
    }
    
    public void loadCustomMessages() throws IOException {
    	customMessages.forEach(allMessages::remove);
    	BufferedReader reader = new BufferedReader(new FileReader(spammerFile));
    	String line = "";
    	
    	while ((line = reader.readLine()) != null) {
    		customMessages.put(customMessages.size(), line);
    	}
    	
    	reader.close();
    	customMessages.forEach(allMessages::put);
    }
    
    public void addCustomMessage(String message) {
    	customMessages.put(customMessages.size(), message);
    	allMessages.put(allMessages.size(), message);
    }
}
