package pyre.tinkerslevellingaddon.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class FireTitaniteShardItem extends TitaniteShardItem {
    public FireTitaniteShardItem() {
        super();
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, components, flag);
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".titanite_shard.fire_infused").withStyle(Style.EMPTY.withColor(0xFF5E00)));
    }
}
