package pyre.tinkerslevellingaddon.core;

import net.endgineer.curseoftheabyss.common.Abyss;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.DeepslateAncientRubbleBlock;
import pyre.tinkerslevellingaddon.block.DeepslateTitaniteOreBlock;
import pyre.tinkerslevellingaddon.item.AbyssRelicItem;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.data.ModifierIds;

@EventBusSubscriber(modid = TinkersLevellingAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PbcEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        LevelAccessor accessor = event.getLevel();
        if (accessor.isClientSide()) return;
        
        BlockState state = event.getState();
        
        ItemStack mainstack = event.getPlayer().getMainHandItem();
        if (!mainstack.isCorrectToolForDrops(state)) return;
        
        boolean isTitanite = state.is(PbcBlocks.DEEPSLATE_TITANITE_ORE.get());
        boolean isRelic = state.is(PbcBlocks.DEEPSLATE_ANCIENT_RUBBLE.get());
        
        BlockPos pos = event.getPos();
        ServerLevel level = (ServerLevel) accessor;
        
        if (isTitanite) {
            event.setCanceled(true);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            
            int dropCount = 1;
            
            int fortuneLevel = 0;
            if (mainstack.is(TinkerTags.Items.MODIFIABLE)) {
                fortuneLevel = ToolStack.from(mainstack).getModifierLevel(ModifierIds.luck);
            } else {
                fortuneLevel = EnchantmentHelper.getEnchantments(mainstack).getOrDefault(Enchantments.BLOCK_FORTUNE, 0);
            }
            
            if (fortuneLevel > 0) {
                RandomSource random = level.getRandom();
                
                if (random.nextDouble() <= fortuneLevel/(2.0 + fortuneLevel)) {
                    dropCount *= (2 + random.nextInt(fortuneLevel)) * (ModList.get().isLoaded("curseoftheabyss") ? Abyss.expected_field(pos.getY()) : 1);
                }
            }
            
            DeepslateTitaniteOreBlock block = (DeepslateTitaniteOreBlock) state.getBlock();
            Block.popResource(level, pos, new ItemStack(PbcItems.TITANITE_SHARD.get(), Math.max(1, dropCount)));
            block.popExperience(level, pos, block.getExpDrop(state, level, level.random, pos, fortuneLevel, 0));
        } else if (isRelic) {
            event.setCanceled(true);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            
            ItemStack relicstack = new ItemStack(PbcItems.ABYSS_RELIC.get());
            CompoundTag tag = new CompoundTag();
            tag.putInt(AbyssRelicItem.SIZE, AbyssRelicItem.rollSize(ModList.get().isLoaded("curseoftheabyss") ? Abyss.layer(pos.getY()) : 7));
            relicstack.setTag(tag);
            
            DeepslateAncientRubbleBlock block = (DeepslateAncientRubbleBlock) state.getBlock();
            Block.popResource(level, pos, relicstack.copy());
            block.popExperience(level, pos, block.getExpDrop(state, level, level.random, pos, 0, 0));
        }
    }
}
