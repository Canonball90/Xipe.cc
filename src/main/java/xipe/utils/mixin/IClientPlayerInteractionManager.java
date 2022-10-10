package xipe.utils.mixin;

import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;

public interface IClientPlayerInteractionManager
{
    void setBlockBreakProgress(final float p0);
    
    void setBlockBreakingCooldown(final int p0);
    
    void _sendSequencedPacket(final ClientWorld p0, final SequencedPacketCreator p1);
    
    float getBlockBreakProgress();
    
    void syncSelected();
}
