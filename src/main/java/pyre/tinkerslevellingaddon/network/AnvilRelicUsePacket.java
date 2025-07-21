package pyre.tinkerslevellingaddon.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;
import pyre.tinkerslevellingaddon.core.PbcSounds;

public class AnvilRelicUsePacket {
    private final BlockPos blockPos;
    
    public AnvilRelicUsePacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public AnvilRelicUsePacket(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                mc.level.playLocalSound(
                    this.blockPos.getX() + 0.5,
                    this.blockPos.getY() + 1.0,
                    this.blockPos.getZ() + 0.5,
                    SoundEvents.END_PORTAL_FRAME_FILL,
                    SoundSource.BLOCKS,
                    1.0F,
                    0.2F,
                    false
                );
                mc.level.playLocalSound(
                    this.blockPos.getX() + 0.5,
                    this.blockPos.getY() + 1.0,
                    this.blockPos.getZ() + 0.5,
                    PbcSounds.RELIC_WHISPERS.get(),
                    SoundSource.BLOCKS,
                    3.0F,
                    1.0F,
                    false
                );
            }
        });
        
        return true;
    }
}
