package pyre.tinkerslevellingaddon.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class AbyssRelicItem extends Item {
    public AbyssRelicItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1));
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic.description").withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic.usage").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(itemstack, level, components, flag);
    }
}
