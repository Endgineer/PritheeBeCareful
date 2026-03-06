package pyre.tinkerslevellingaddon.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class LargeTitaniteShardItem extends TitaniteShardItem {
    public LargeTitaniteShardItem() {
        super(Rarity.COMMON);
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".large_titanite_shard.description").withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".large_titanite_shard.trivia").withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".large_titanite_shard.usage").withStyle(ChatFormatting.DARK_GRAY));
    }
}
