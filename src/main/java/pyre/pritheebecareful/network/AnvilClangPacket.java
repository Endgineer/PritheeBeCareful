package pyre.pritheebecareful.network;

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
    private final int hitEffectiveness;
    private final int takenXpPoints;
    
    public AnvilClangPacket(BlockPos blockPos, int hitEffectiveness, int takenXpPoints) {
        this.blockPos = blockPos;
        this.hitEffectiveness = hitEffectiveness;
        this.takenXpPoints = takenXpPoints;
    }

    public AnvilClangPacket(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();
        this.hitEffectiveness = buffer.readInt();
        this.takenXpPoints = buffer.readInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
        buffer.writeInt(this.hitEffectiveness);
        buffer.writeInt(this.takenXpPoints);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                if (this.hitEffectiveness > 0) {
                    for (int i = 0; i < hitEffectiveness/2; i++) {
                        double xPos = this.blockPos.getX() + 0.5;
                        double yPos = this.blockPos.getY() + 1.015625;
                        double zPos = this.blockPos.getZ() + 0.5;

                        double xVel = mc.level.random.nextDouble();
                        double yVel = mc.level.random.nextDouble();
                        double zVel = mc.level.random.nextDouble();
                        
                        mc.level.addParticle(ParticleTypes.CRIT, xPos, yPos, zPos, xVel, yVel, zVel);
                        mc.level.addParticle(ParticleTypes.CRIT, xPos, yPos, zPos, -xVel, yVel, -zVel);
                    }
                    
                    mc.level.playLocalSound(
                        this.blockPos.getX() + 0.5,
                        this.blockPos.getY() + 1.0,
                        this.blockPos.getZ() + 0.5,
                        SoundEvents.ANVIL_PLACE,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.25F,
                        false
                    );
                    
                    for (int j = 0; j < this.takenXpPoints; j++) {
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
                } else {
                    mc.level.playLocalSound(
                        this.blockPos.getX() + 0.5,
                        this.blockPos.getY() + 1.0,
                        this.blockPos.getZ() + 0.5,
                        SoundEvents.ANVIL_PLACE,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.75F,
                        false
                    );
                }
            }
        });
        
        return true;
    }
}
