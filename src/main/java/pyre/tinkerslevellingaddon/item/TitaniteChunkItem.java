package pyre.tinkerslevellingaddon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.foundation.item.TooltipHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class TitaniteChunkItem extends TitaniteShardItem {
    public TitaniteChunkItem() {
        super(Rarity.UNCOMMON);
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_chunk.description"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_chunk.trivia"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_chunk.usage"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
    }
}
