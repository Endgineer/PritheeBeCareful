package pyre.pritheebecareful.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

public class AnvilMulticlangPacket {
    private final BlockPos blockPos;
    
    public AnvilMulticlangPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public AnvilMulticlangPacket(FriendlyByteBuf buffer) {
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
                    SoundEvents.ANVIL_USE,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F,
                    false
                );
            }
        });
        
        return true;
    }
}
