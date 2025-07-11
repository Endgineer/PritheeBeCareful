package pyre.tinkerslevellingaddon.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class Messages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(TinkersLevellingAddon.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(LevelUpPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LevelUpPacket::new)
                .encoder(LevelUpPacket::toBytes)
                .consumerMainThread(LevelUpPacket::handle)
                .add();
        
        net.messageBuilder(AnvilClangPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AnvilClangPacket::new)
                .encoder(AnvilClangPacket::toBytes)
                .consumerMainThread(AnvilClangPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendAnvilClang(Level level, BlockPos blockPos, boolean isHitEffective) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPos)), new AnvilClangPacket(blockPos, isHitEffective));
    }
}
