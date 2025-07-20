package pyre.tinkerslevellingaddon.setup;

import com.simibubi.create.api.behaviour.BlockSpoutingBehaviour;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.QuenchingBasinBlock;
import pyre.tinkerslevellingaddon.core.PbcBlocks;

public class Spouting extends BlockSpoutingBehaviour {
    @Override
    public int fillBlock(Level level, BlockPos pos, SpoutBlockEntity spout, FluidStack fluid, boolean simulate) {
        if (!fluid.getFluid().isSame(Fluids.WATER)) return 0;
        
        BlockState state = level.getBlockState(pos);
        if (state == null || !state.is(PbcBlocks.QUENCHING_BASIN.get())) return 0;
        
        int water = state.getValue(QuenchingBasinBlock.LEVEL);
        if (water == 4) return 0;

        if (!simulate) {
            level.setBlockAndUpdate(pos, state.setValue(QuenchingBasinBlock.LEVEL, water+1));
            level.playSound((Player) null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.5F);
            level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
        }
        
        return 250;
    }
    
    public static void addCustomSpoutInteraction() {
        BlockSpoutingBehaviour.addCustomSpoutInteraction(new ResourceLocation(TinkersLevellingAddon.MOD_ID, "spouting"), new Spouting());
    }
}
