package pyre.tinkerslevellingaddon.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pyre.tinkerslevellingaddon.item.AbyssRelicItem;

public class DeepslateAncientRubbleBlockEntity extends BrushableBlockEntity {
    public DeepslateAncientRubbleBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        hackySetItem(AbyssRelicItem.rollAbyssRelic(pos.getY()));
    }
    
    private void hackySetItem(ItemStack stack) {
        this.load(createItemTag(stack));
    }
    
    private CompoundTag createItemTag(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        tag.put("item", stack.save(new CompoundTag()));
        return tag;
    }
}
