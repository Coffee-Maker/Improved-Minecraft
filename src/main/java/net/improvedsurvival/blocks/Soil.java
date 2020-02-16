package net.improvedsurvival.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;

public class Soil extends Block {
public static final BooleanProperty WET;

    public Soil(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WET, false));
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.GRAVEL;
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(WET);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.up()).getBlock() instanceof SaplingBlock){{
            SaplingBlock block = (SaplingBlock)world.getBlockState(pos.up()).getBlock();
            for(int i = 0; i < 10; i++)
                if(!(world.getBlockState(pos.up()).getBlock() instanceof SaplingBlock))
                    break;
                block.scheduledTick(world.getBlockState(pos.up()), world, pos.up(), random);
            }
        }
    }

    static{
        WET = BooleanProperty.of("wet");
    }
}