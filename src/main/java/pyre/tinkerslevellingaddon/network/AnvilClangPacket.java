package pyre.tinkerslevellingaddon.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

public class AnvilClangPacket {
    private final BlockPos blockPos;
    private final boolean isHitEffective;
    
    public AnvilClangPacket(BlockPos blockPos, boolean isHitEffective) {
        this.blockPos = blockPos;
        this.isHitEffective = isHitEffective;
    }

    public AnvilClangPacket(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();
        this.isHitEffective = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
        buffer.writeBoolean(this.isHitEffective);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                if (this.isHitEffective) {
                    for (int i = 0; i < 10; i++) {
                        double offsetX = (mc.level.random.nextDouble() - 0.5) * 0.1;
                        double offsetY = (mc.level.random.nextDouble() - 0.5) * 0.1;
                        double offsetZ = (mc.level.random.nextDouble() - 0.5) * 0.1;
                        
                        double x = this.blockPos.getX() + 0.5D + (mc.level.random.nextDouble() - 0.5) * 0.1;
                        double y = this.blockPos.getY() + 1.0D + (mc.level.random.nextDouble() - 0.5) * 0.1;
                        double z = this.blockPos.getZ() + 0.5D + (mc.level.random.nextDouble() - 0.5) * 0.1;
                        
                        mc.level.addParticle(
                            ParticleTypes.CRIT,
                            x, y, z,
                            offsetX, offsetY, offsetZ
                        );
                    }
                    
                    mc.level.playLocalSound(
                        this.blockPos.getX() + 0.5,
                        this.blockPos.getY() + 1.0,
                        this.blockPos.getZ() + 0.5,
                        SoundEvents.EXPERIENCE_ORB_PICKUP,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
                
                mc.level.playLocalSound(
                    this.blockPos.getX() + 0.5,
                    this.blockPos.getY() + 1.0,
                    this.blockPos.getZ() + 0.5,
                    SoundEvents.ANVIL_PLACE,
                    SoundSource.BLOCKS,
                    1.0F,
                    5.0F,
                    false
                );
            }
        });
        
        return true;
    }
}
