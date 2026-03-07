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

public class TitaniteScaleItem extends TitaniteShardItem {
    public TitaniteScaleItem() {
        super(Rarity.RARE);
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_scale.description"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_scale.trivia"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_scale.usage"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
    }
}
