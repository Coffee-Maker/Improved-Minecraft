package net.improvedsurvival.blocks;

import java.util.Random;

import net.improvedsurvival.mixin.FarmlandBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;

public class SoilFarmland extends FarmlandBlock {
    public static final BooleanProperty WET;

    public SoilFarmland(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(WET, false));
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.GRAVEL;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean waterNearby = FarmlandBlockAccessor.isWaterNearby(world, pos);
        if(waterNearby){
            if(!state.get(WET))
                world.setBlockState(pos, state.with(WET, true));
        }
        
        if(!waterNearby && !state.get(WET) && !FarmlandBlockAccessor.hasCrop(world, pos))
            setToDirt(state, world, pos);

        if(state.get(WET) && !waterNearby)
            world.setBlockState(pos, state.with(WET, false));

        BlockState blockState = world.getBlockState(pos.add(0,1,0));

        if(blockState.getBlock() instanceof CropBlock){
            CropBlock crop = (CropBlock)blockState.getBlock();
            int limit = random.nextInt(15) + 5;
            for(int i = 0; i < limit; i++)
                crop.scheduledTick(blockState, world, pos.add(0,1,0), random);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
        builder.add(WET);
     }

     static{
         WET = BooleanProperty.of("wet");
     }
}