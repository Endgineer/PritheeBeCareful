package pyre.tinkerslevellingaddon.core;

import java.util.concurrent.ThreadLocalRandom;

import net.endgineer.curseoftheabyss.common.Abyss;
import net.endgineer.curseoftheabyss.core.ModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
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
import pyre.tinkerslevellingaddon.block.DeepslateTitaniteOreBlock;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.data.ModifierIds;

@EventBusSubscriber(modid = TinkersLevellingAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PbcEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        LevelAccessor accessor = event.getLevel();
        if (accessor.isClientSide()) return;
        
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        if (player.gameMode.isCreative()) return;
        
        BlockState state = event.getState();
        
        ItemStack mainstack = player.getMainHandItem();
        if (!mainstack.isCorrectToolForDrops(state)) return;
        
        boolean isTitanite = state.is(PbcBlocks.DEEPSLATE_TITANITE_ORE.get());
        
        BlockPos pos = event.getPos();
        ServerLevel level = (ServerLevel) accessor;
        
        if (isTitanite) {
            event.setCanceled(true);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            double layerspan = ModVariables.ABYSS.SPAN/7.0;
            
            double titanite_shard_chance = 0.5870 * Math.min(Abyss.pressure(pos.getY()), 2) / 2.0;
            double large_titanite_shard_chance = 0.2456 * Math.min(Abyss.pressure(pos.getY()+2*layerspan), 2) / 2.0;
            double titanite_chunk_chance = 0.1036 * Math.min(Abyss.pressure(pos.getY()+4*layerspan), 2) / 2.0;
            double titanite_scale_chance = 0.0623 * Math.min(Abyss.pressure(pos.getY()+5*layerspan), 1);
            double titanite_slab_chance = 0.0015 * Math.min(Abyss.pressure(pos.getY()+6*layerspan), 1);
            
            double total_chance = titanite_shard_chance+large_titanite_shard_chance+titanite_chunk_chance+titanite_scale_chance+titanite_slab_chance;
            
            Item titanite_variant = total_chance == 0 ? PbcItems.TITANITE_SHARD.get() : null;
            while (titanite_variant == null) {
                titanite_shard_chance /= total_chance;
                large_titanite_shard_chance /= total_chance;
                titanite_chunk_chance /= total_chance;
                titanite_scale_chance /= total_chance;
                titanite_slab_chance /= total_chance;

                total_chance = 0;
                double roll = ThreadLocalRandom.current().nextDouble();
                
                total_chance += titanite_shard_chance;
                if (roll <= total_chance) {
                    titanite_variant = PbcItems.TITANITE_SHARD.get();
                    break;
                }
                
                total_chance += large_titanite_shard_chance;
                if (roll <= total_chance) {
                    titanite_variant = PbcItems.LARGE_TITANITE_SHARD.get();
                    break;
                }
                
                total_chance += titanite_chunk_chance;
                if (roll <= total_chance) {
                    titanite_variant = PbcItems.TITANITE_CHUNK.get();
                    break;
                }
                
                total_chance += titanite_scale_chance;
                if (roll <= total_chance) {
                    titanite_variant = PbcItems.TITANITE_SCALE.get();
                    break;
                }
                
                total_chance += titanite_slab_chance;
                if (roll <= total_chance) {
                    titanite_variant = PbcItems.TITANITE_SLAB.get();
                    break;
                }
            }
            
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
            Block.popResource(level, pos, new ItemStack(titanite_variant, Math.max(1, dropCount)));
            block.popExperience(level, pos, block.getExpDrop(state, level, level.random, pos, fortuneLevel, 0));
        }
    }
}
