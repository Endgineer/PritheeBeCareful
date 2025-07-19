package pyre.tinkerslevellingaddon.menu;

import javax.annotation.Nullable;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import pyre.tinkerslevellingaddon.core.PbcMenus;
import pyre.tinkerslevellingaddon.entity.ForgeChamberBlockEntity;
import pyre.tinkerslevellingaddon.menu.slotitemhandler.FuelSlotItemHandler;
import pyre.tinkerslevellingaddon.menu.slotitemhandler.ReinforceSlotItemHandler;
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
    public boolean clickMenuButton(Player player, int id) {
        return false;
    }

    public double getFuelPercentage() {
        return this.entity.getFuelPercentage();
    }
}
