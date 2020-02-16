package net.improvedsurvival;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class HeatSource {

    public static Map<Block, HeatSource> HEAT_SOURCES = ImmutableMap.of(Blocks.FIRE, new HeatSource(5, 12), Blocks.CAMPFIRE, new HeatSource(10,60), Blocks.LAVA, new HeatSource(5, 100), Blocks.LANTERN, new HeatSource(3, 20));

    public float range;
    public float temperature;

    public HeatSource(final float range, final float temperature){
        this.range = range;
        this.temperature = temperature;
    }

    public static boolean tryGetHeatSources(BlockPos pos, World world, ArrayList<BlockPos> heatSources){
        Iterator<BlockPos> var2 = BlockPos.iterate(pos.add(-10, -3, -10), pos.add(10, 3, 10)).iterator();

        while(var2.hasNext()){
            BlockPos blockPos = (BlockPos)var2.next();
            if(HeatSource.HEAT_SOURCES.containsKey(world.getBlockState(blockPos).getBlock())){
                heatSources.add(new BlockPos(blockPos));
            }
        }

        return heatSources.size() > 0;
    }

    public static float getAccumulatedTemperature(BlockPos pos, World world){
        ArrayList<BlockPos> heatSources = new ArrayList<BlockPos>();

        float heatSourceTemp = 0;

        if (HeatSource.tryGetHeatSources(pos, world, heatSources)) {
            for (BlockPos p : heatSources) {
                HeatSource source = HeatSource.HEAT_SOURCES.get(world.getBlockState(p).getBlock());
                if (source == null)
                continue;
                heatSourceTemp += (1 - MathHelper.clamp(p.getSquaredDistance(pos) / (source.range * source.range), 0, 1)) * source.temperature;
            }
        }

        return heatSourceTemp;
    }
}