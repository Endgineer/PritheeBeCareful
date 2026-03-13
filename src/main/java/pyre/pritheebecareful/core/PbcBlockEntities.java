package pyre.pritheebecareful.core;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.minecraftforge.eventbus.api.IEventBus;
import pyre.pritheebecareful.PritheeBeCareful;
import pyre.pritheebecareful.client.render.ReinforcementAnvilBlockEntityRenderer;
import pyre.pritheebecareful.entity.ForgeChamberBlockEntity;
import pyre.pritheebecareful.entity.ForgeHearthBlockEntity;
import pyre.pritheebecareful.entity.ForgeThroatBlockEntity;
import pyre.pritheebecareful.entity.ReinforcementAnvilBlockEntity;

public class PbcBlockEntities {
    private static final CreateRegistrate REGISTRATE = PritheeBeCareful.getRegistrate();

    public static final BlockEntityEntry<ReinforcementAnvilBlockEntity> REINFORCEMENT_ANVIL_BLOCK_ENTITY = REGISTRATE
        .blockEntity("reinforcement_anvil_block_entity", ReinforcementAnvilBlockEntity::new)
        .validBlocks(PbcBlocks.REINFORCEMENT_ANVIL)
        .renderer(() -> ReinforcementAnvilBlockEntityRenderer::new)
        .register();

    public static final BlockEntityEntry<ForgeChamberBlockEntity> FORGE_CHAMBER_BLOCK_ENTITY = REGISTRATE
        .blockEntity("forge_chamber_block_entity", ForgeChamberBlockEntity::new)
        .validBlocks(PbcBlocks.FORGE_CHAMBER)
        .register();

    public static final BlockEntityEntry<ForgeThroatBlockEntity> FORGE_THROAT_BLOCK_ENTITY = REGISTRATE
        .blockEntity("forge_throat_block_entity", ForgeThroatBlockEntity::new)
        .validBlocks(PbcBlocks.FORGE_THROAT)
        .register();

    public static final BlockEntityEntry<ForgeHearthBlockEntity> FORGE_HEARTH_BLOCK_ENTITY = REGISTRATE
        .blockEntity("forge_hearth_block_entity", ForgeHearthBlockEntity::new)
        .validBlocks(PbcBlocks.FORGE_HEARTH)
        .register();
    
    public static void register(IEventBus eventBus) {}
}
