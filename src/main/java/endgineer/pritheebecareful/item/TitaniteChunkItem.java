package endgineer.pritheebecareful.item;

import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.foundation.item.TooltipHelper;

import endgineer.pritheebecareful.PritheeBeCareful;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class TitaniteChunkItem extends TitaniteBaseItem {
    public TitaniteChunkItem() {
        super(Rarity.UNCOMMON);
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+PritheeBeCareful.MOD_ID+".titanite_chunk.description"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+PritheeBeCareful.MOD_ID+".titanite_chunk.trivia"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+PritheeBeCareful.MOD_ID+".titanite_chunk.usage"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
    }
}
