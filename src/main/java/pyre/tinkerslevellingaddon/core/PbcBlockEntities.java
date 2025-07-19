package pyre.tinkerslevellingaddon.core;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.entity.ForgeChamberBlockEntity;
import pyre.tinkerslevellingaddon.entity.ReinforcementAnvilBlockEntity;

public class PbcBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TinkersLevellingAddon.MOD_ID);

    public static final RegistryObject<BlockEntityType<ReinforcementAnvilBlockEntity>> REINFORCEMENT_ANVIL_BLOCK_ENTITY = BLOCKENTITIES.register("reinforcement_anvil_block_entity",
        () -> BlockEntityType.Builder.of(ReinforcementAnvilBlockEntity::new, PbcBlocks.REINFORCEMENT_ANVIL.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ForgeChamberBlockEntity>> FORGE_CHAMBER_BLOCK_ENTITY = BLOCKENTITIES.register("forge_chamber_block_entity",
        () -> BlockEntityType.Builder.of(ForgeChamberBlockEntity::new, PbcBlocks.FORGE_CHAMBER.get()).build(null)
    );
    
    public static void register(IEventBus eventBus) {
        BLOCKENTITIES.register(eventBus);
    }
}
