package endgineer.pritheebecareful.core;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.client.render.ReinforcementAnvilBlockEntityRenderer;
import endgineer.pritheebecareful.entity.ForgeChamberBlockEntity;
import endgineer.pritheebecareful.entity.ForgeHearthBlockEntity;
import endgineer.pritheebecareful.entity.ForgeThroatBlockEntity;
import endgineer.pritheebecareful.entity.ReinforcementAnvilBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;

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
