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

public class TitaniteScaleItem extends TitaniteBaseItem {
    public TitaniteScaleItem() {
        super(Rarity.RARE);
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+PritheeBeCareful.MOD_ID+".titanite_scale.description"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+PritheeBeCareful.MOD_ID+".titanite_scale.trivia"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
        components.add(Component.empty());
        components.addAll(TooltipHelper.cutTextComponent(Component.translatable("tooltip."+PritheeBeCareful.MOD_ID+".titanite_scale.usage"), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY), TooltipHelper.styleFromColor(ChatFormatting.DARK_GRAY)));
    }
}
