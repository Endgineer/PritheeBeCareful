package pyre.tinkerslevellingaddon.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec.MaterialReinforceSpec;

public class ReinforceItem extends Item {
    public static enum ReinforceStatus { UNTOUCHED, FORGING, FINISHED };
    
    public static final String MATERIAL = TinkersLevellingAddon.MOD_ID+".material";
    public static final String GEAR = TinkersLevellingAddon.MOD_ID+".gear";
    public static final String COUNT = TinkersLevellingAddon.MOD_ID+".count";
    public static final String REINFORCE = TinkersLevellingAddon.MOD_ID+".reinforce";
    public static final String TEMPERATURE = TinkersLevellingAddon.MOD_ID+".temperature";
    public static final String PROGRESS = TinkersLevellingAddon.MOD_ID+".progress";
    public static final String EXPERIENCE = TinkersLevellingAddon.MOD_ID+".experience";
    public static final String STATUS = TinkersLevellingAddon.MOD_ID+".status";
    public static final String CLOCK = TinkersLevellingAddon.MOD_ID+".clock";
    
    public ReinforceItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ReinforceItem.addMetalStats(components, itemstack);
        super.appendHoverText(itemstack, level, components, flag);
    }

    public static boolean isValidReinforceItem(ItemStack stack) {
        if (!(stack.getItem() instanceof ReinforceItem)) return false;
        if (!stack.hasTag()) return false;

        CompoundTag tag = stack.getTag();
        if (!tag.contains(MATERIAL)) return false;
        if (!tag.contains(GEAR)) return false;
        if (!tag.contains(COUNT)) return false;
        if (!tag.contains(REINFORCE)) return false;
        if (!tag.contains(TEMPERATURE)) return false;
        if (!tag.contains(PROGRESS)) return false;
        if (!tag.contains(EXPERIENCE)) return false;
        if (!tag.contains(STATUS)) return false;
        if (!tag.contains(CLOCK)) return false;

        return true;
    }
    
    public static boolean addMetalStats(List<Component> tooltip, ItemStack itemstack) {
        if (!ReinforceItem.isValidReinforceItem(itemstack)) return false;
        
        CompoundTag tag = itemstack.getTag();
        String material = tag.getString(ReinforceItem.MATERIAL);
        int count = tag.getInt(ReinforceItem.COUNT);
        int reinforce = tag.getInt(ReinforceItem.REINFORCE);
        double temperature = tag.getDouble(ReinforceItem.TEMPERATURE);
        int progress = tag.getInt(ReinforceItem.PROGRESS);
        double experience = tag.getDouble(ReinforceItem.EXPERIENCE);

        MaterialReinforceSpec reinforceSpec = ForgingMaterialSpec.getMaterialReinforceSpec(material, reinforce);
        if (reinforceSpec == null) return false;
        
        int maxProgress = reinforceSpec.getExperienceCostTotal(count);
        double maxTemperature = reinforceSpec.getBreakdownPoint();
        
        String progressPercentage = String.valueOf((int) (100 * (maxProgress - progress) / maxProgress))+"%";
        String temperaturePercentage = String.valueOf((int) (100 * temperature / maxTemperature))+"%";
        String malleabilityPercentage = String.valueOf((int) (100 * reinforceSpec.getMalleability(temperature)))+"%";
        
        double workingPoint = reinforceSpec.getWorkingPoint();
        double quenchingPoint = reinforceSpec.getQuenchingPoint();
        double foldingPoint = reinforceSpec.getFoldingPoint();
        double breakdownPoint = reinforceSpec.getBreakdownPoint();
        double meltingPoint = reinforceSpec.getMeltingPoint();
        
        tooltip.add(
            Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".reinforce_item.progress").append(":").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(" ")
                .append(String.valueOf(maxProgress - progress))
                .append(" / ")
                .append(String.valueOf(maxProgress))
                .append(" ("+String.valueOf(progressPercentage)+")")
                .withStyle(ChatFormatting.WHITE))
        );
        
        tooltip.add(
            Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".reinforce_item.temperature").append(":").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(" ")
                .append(String.valueOf((int) temperature))
                .append(" / ")
                .append(String.valueOf((int) maxTemperature))
                .append(" ("+temperaturePercentage+")")
                .withStyle(ChatFormatting.WHITE))
        );
        
        tooltip.add(
            Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".reinforce_item.experience").append(":").withStyle(ChatFormatting.GREEN)
                .append(Component.literal(" ")
                .append(String.valueOf((int) Math.ceil(experience)))
                .withStyle(ChatFormatting.WHITE))
        );
        
        tooltip.add(
            Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".reinforce_item.malleability").append(":").withStyle(ChatFormatting.LIGHT_PURPLE)
                .append(Component.literal(" ")
                .append(malleabilityPercentage)
                .withStyle(ChatFormatting.WHITE))
        );
        
        tooltip.add(
            Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".reinforce_item.points").append(":").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(" ").append(String.valueOf((int) workingPoint)+" \u00B0C").withStyle(temperature >= workingPoint ? ChatFormatting.DARK_RED : ChatFormatting.DARK_GRAY))
                .append(Component.literal(" |").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(" ").append(String.valueOf((int) quenchingPoint)+" \u00B0C").withStyle(temperature >= quenchingPoint ? ChatFormatting.RED : ChatFormatting.DARK_GRAY))
                .append(Component.literal(" |").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(" ").append(String.valueOf((int) foldingPoint)+" \u00B0C").withStyle(temperature >= foldingPoint ? ChatFormatting.GOLD : ChatFormatting.DARK_GRAY))
                .append(Component.literal(" |").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(" ").append(String.valueOf((int) breakdownPoint)+" \u00B0C").withStyle(temperature >= breakdownPoint ? ChatFormatting.YELLOW : ChatFormatting.DARK_GRAY))
                .append(Component.literal(" |").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(" ").append(String.valueOf((int) meltingPoint)+" \u00B0C").withStyle(temperature >= meltingPoint ? ChatFormatting.WHITE : ChatFormatting.DARK_GRAY))
        );
        
        return true;
    }
    
    @Override
    public Component getName(ItemStack itemstack) {
        CompoundTag tag = itemstack.getTag();

        if(tag == null) {
            return Component.translatable(("item."+TinkersLevellingAddon.MOD_ID+".reinforce_item"));
        }
        
        String material = tag.getString(ReinforceItem.MATERIAL);
        String gear = tag.getString(ReinforceItem.GEAR);
        String reinforce = "+"+String.valueOf(tag.getInt(ReinforceItem.REINFORCE));
        String status = String.valueOf(tag.getInt(ReinforceItem.STATUS));
        
        return Component.literal(
            I18n.get(
                "item."+TinkersLevellingAddon.MOD_ID+".reinforce_item." + status,
                Component.translatable("forging."+TinkersLevellingAddon.MOD_ID+".material."+material).getString(),
                Component.translatable("forging."+TinkersLevellingAddon.MOD_ID+".gear."+gear).getString(),
                reinforce
            )
        );
    }
}
