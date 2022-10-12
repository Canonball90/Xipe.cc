package xipe.module.modules.Render;

import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Vec3d;
import xipe.event.EventTarget;
import xipe.event.events.EventReceivePacket;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;

import java.awt.*;

public class Confetti extends Mod {
    //Thank you Walaryne for cool module
    public Confetti() {
    	super("Confetti", "Changes the color of totem particles", Category.RENDER); 
    	addSettings(rainbow,red,green,blue);
    }

    public static final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    public static final NumberSetting red = new NumberSetting("Red", 0,255,160,0.1);
	public static final NumberSetting green = new NumberSetting("Green", 0,255,160,0.1);
	public static final NumberSetting blue = new NumberSetting("Blue", 0,255,160,0.1);
	
	public static final Color colorOne = new Color(red.getValueInt(), green.getValueInt(), blue.getValueInt(), 255);
	public static final Color colorTwo = new Color(red.getValueInt(), green.getValueInt(), blue.getValueInt(), 255);
	
    public static Vec3d getColorOne(){
        return getDoubleVectorColor(colorOne);
    }

    public static Vec3d getColorTwo(){
        return getDoubleVectorColor(colorTwo);
    }

    public static Vec3d getRainbowColor(){
        return getDoubleVectorColor(new Color(rainbow(300)));
    }

    public static Vec3d getDoubleVectorColor(Color color) {
        return new Vec3d((double) color.getRed() / 255, (double) color.getGreen() / 255, (double) color.getBlue() / 255);
    }

    public static Boolean isRainbow(){
        return rainbow.isEnabled();
    }
    /*
    @EventTarget
    public void eventParticle(final EventParticle.Normal event) {
        if (event.getParticle() instanceof ExplosionLargeParticle) {
            event.setCancelled(true);
        }
    }
    */
    @EventTarget
    private void onPacket(EventReceivePacket event){
        Packet<?> packet = event.getPacket();
        if(packet instanceof ExplosionS2CPacket) event.cancel();
    }


	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 1f, 1f).getRGB();
    }
}
