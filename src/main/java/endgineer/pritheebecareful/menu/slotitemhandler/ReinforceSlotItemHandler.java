package endgineer.pritheebecareful.menu.slotitemhandler;

import endgineer.pritheebecareful.item.ReinforceItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ReinforceSlotItemHandler extends SlotItemHandler {
    public ReinforceSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public boolean mayPlace(ItemStack stack) {
        return ReinforceItem.isValidReinforceItem(stack);
    }
}
