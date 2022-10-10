package xipe.module.modules.Player;

import xipe.event.EventTarget;
import xipe.event.events.EventReceivePacket;
import xipe.event.events.EventSendPacket;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.utils.player.ChatUtil;

public class PacketLogger extends Mod{

	public BooleanSetting income = new BooleanSetting("Incoming", true);
	public BooleanSetting outgoing = new BooleanSetting("Outgoing", false);
	
	public PacketLogger() {
		super("Packet Logger", "Logs", Category.WORLD);
		addSettings(income,outgoing);
	}

}
