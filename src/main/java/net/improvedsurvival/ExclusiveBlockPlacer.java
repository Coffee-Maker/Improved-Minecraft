package net.improvedsurvival;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;

public class ExclusiveBlockPlacer extends SimpleBlockPlacer{

    private final Block[] blocks;

    public ExclusiveBlockPlacer(Block... blocks){
        this.blocks = blocks;
    }

    @Override
    public void method_23403(IWorld iWorld, BlockPos blockPos, BlockState blockState, Random random) {
        if(Arrays.stream(blocks).anyMatch(b -> iWorld.getBlockState(blockPos.down()).getBlock() == b)){
            iWorld.setBlockState(blockPos.down(), blockState, 2);
        }
    }
}