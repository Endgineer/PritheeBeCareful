package endgineer.pritheebecareful.menu;

import javax.annotation.Nullable;

import endgineer.pritheebecareful.core.PbcMenus;
import endgineer.pritheebecareful.entity.ForgeChamberBlockEntity;
import endgineer.pritheebecareful.menu.slotitemhandler.FuelSlotItemHandler;
import endgineer.pritheebecareful.menu.slotitemhandler.ReinforceSlotItemHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.shared.inventory.TriggeringBaseContainerMenu;

public class ForgeContainerMenu extends TriggeringBaseContainerMenu<ForgeChamberBlockEntity> {
    private final ForgeChamberBlockEntity entity;
    private final Slot itemSlot;
    private final Slot fuelSlot;
    
    public ForgeContainerMenu(int id, @Nullable Inventory inventory, @Nullable ForgeChamberBlockEntity entity) {
        super(PbcMenus.FORGE_MENU.get(), id, inventory, entity);

        this.entity = entity;
        
        IItemHandler handler = entity.getItemHandler();
        this.itemSlot = this.addSlot(new ReinforceSlotItemHandler(handler, 0, 80, 17));
        this.fuelSlot = this.addSlot(new FuelSlotItemHandler(handler, 1, 80, 53));
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, row*9 + col + 9, col * 18 + 8, row * 18 + 84));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, col * 18 + 8, 142));
        }
    }
    
    public ForgeContainerMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(id, inventory, getTileEntityFromBuf(buffer, ForgeChamberBlockEntity.class));
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack oldstack = ItemStack.EMPTY;
        
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack newstack = slot.getItem();
            oldstack = newstack.copy();
            
            if (index < 2) {
                if (!this.moveItemStackTo(newstack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.itemSlot.mayPlace(newstack) && this.moveItemStackTo(newstack, 0, 1, false)) {
                    return oldstack;
                }
                
                if (this.fuelSlot.mayPlace(newstack) && this.moveItemStackTo(newstack, 1, 2, false)) {
                    return oldstack;
                }
                
                if (index > 1 && index < 29) {
                    if (!this.moveItemStackTo(newstack, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index > 28 && index < 38) {
                    if (!this.moveItemStackTo(newstack, 2, 29, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            
            if (newstack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            this.entity.setChanged();
            
            if (newstack.getCount() == oldstack.getCount()) {
                return ItemStack.EMPTY;
            }
        }
        
        return oldstack;
    }
    
    @Override
    public boolean clickMenuButton(Player player, int id) {
        return false;
    }

    public double getFuelPercentage() {
        return this.entity.getFuelPercentage();
    }
}
