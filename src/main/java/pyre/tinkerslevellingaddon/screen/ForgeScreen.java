package pyre.tinkerslevellingaddon.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.menu.ForgeContainerMenu;
import slimeknights.tconstruct.library.client.GuiUtil;

public class ForgeScreen extends AbstractContainerScreen<ForgeContainerMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(TinkersLevellingAddon.MOD_ID, "textures/gui/forge.png");
    
    public ForgeScreen(ForgeContainerMenu menu, Inventory inventory, Component name) {
        super(menu, inventory, name);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, x, y, partialTicks);
        this.renderTooltip(graphics, x, y);
    }
    
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBackground(graphics, this, BACKGROUND);

        int flameHeight = (int) Math.ceil(menu.getFuelPercentage() * 13);
        graphics.blit(BACKGROUND, leftPos+81, topPos+50-flameHeight, 176, 13 - flameHeight, 14, flameHeight);
    }
}
