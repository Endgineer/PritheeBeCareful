package pyre.pritheebecareful.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class TitaniteBaseItem extends Item {
    public TitaniteBaseItem() {
        this(Rarity.COMMON);
    }
    
    public TitaniteBaseItem(Rarity rarity) {
        super(new Item.Properties().rarity(rarity).fireResistant());
    }
}
