package net.improvedsurvival.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Map;

public class HeatSource {
	public static final Map<Block, HeatSource> HEAT_SOURCES = ImmutableMap.of(
			Blocks.FIRE, new HeatSource(5, 12),
			Blocks.CAMPFIRE, new HeatSource(10, 60),
			Blocks.LAVA, new HeatSource(5, 100),
			Blocks.LANTERN, new HeatSource(3, 20)
	);
	
	public final float range;
	public final float temperature;
	
	public HeatSource(final float range, final float temperature) {
		this.range = range;
		this.temperature = temperature;
	}
	
	public static boolean tryGetHeatSources(BlockPos pos, World world, ArrayList<BlockPos> heatSources) {
		for(BlockPos blockPos : BlockPos.iterate(pos.add(-10, -3, -10), pos.add(10, 3, 10))) {
			if(HeatSource.HEAT_SOURCES.containsKey(world.getBlockState(blockPos).getBlock())) {
				heatSources.add(new BlockPos(blockPos));
			}
		}
		
		return heatSources.size() > 0;
	}
	
	public static float getAccumulatedTemperature(BlockPos pos, World world) {
		ArrayList<BlockPos> heatSources = new ArrayList<>();
		
		float heatSourceTemp = 0;
		
		if(HeatSource.tryGetHeatSources(pos, world, heatSources)) {
			for(BlockPos p : heatSources) {
				HeatSource source = HeatSource.HEAT_SOURCES.get(world.getBlockState(p).getBlock());
				if(source == null)
					continue;
				heatSourceTemp += (1 - MathHelper.clamp(p.getSquaredDistance(pos) / (source.range * source.range), 0, 1)) * source.temperature;
			}
		}
		
		return heatSourceTemp;
	}
}