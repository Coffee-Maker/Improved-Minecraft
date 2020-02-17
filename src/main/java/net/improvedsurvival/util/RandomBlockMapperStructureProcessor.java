package net.improvedsurvival.util;

import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.JsonOps;

import net.improvedsurvival.Isur;
import net.minecraft.block.BlockState;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class RandomBlockMapperStructureProcessor extends StructureProcessor {
    private static final Gson gson = new Gson();
    private final BlockState sourceState;
    private final BlockState targetState;
    private final BlockState backupState;
    private final float probability;
 
    public RandomBlockMapperStructureProcessor(BlockState sourceState, BlockState targetState, float integrity) {
        this(sourceState, targetState, integrity, sourceState);
    }
 
    public RandomBlockMapperStructureProcessor(BlockState sourceState, BlockState targetState, float integrity, BlockState backupState) {
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.backupState = backupState;
        this.probability = integrity;
    }
 
    public RandomBlockMapperStructureProcessor(Dynamic<?> dynamic) {
        this(
            BlockState.deserialize(new Dynamic<JsonElement>(JsonOps.INSTANCE, gson.fromJson(dynamic.get("source_state").asString().get(), JsonElement.class))),
            BlockState.deserialize(new Dynamic<JsonElement>(JsonOps.INSTANCE, gson.fromJson(dynamic.get("target_state").asString().get(), JsonElement.class))),
            dynamic.get("probability").asFloat(1.0f),
            BlockState.deserialize(new Dynamic<JsonElement>(JsonOps.INSTANCE, gson.fromJson(dynamic.get("backup_state").asString().get(), JsonElement.class)))
        );
    }

    @Override
    public StructureBlockInfo process(WorldView worldView, BlockPos pos, StructureBlockInfo structureBlockInfo, StructureBlockInfo structureBlockInfo2, StructurePlacementData placementData) {
        Random random = placementData.getRandom(structureBlockInfo2.pos);
        return this.sourceState == structureBlockInfo2.state
            ? (random.nextFloat() < probability
                ? new StructureBlockInfo(structureBlockInfo2.pos, this.targetState, null)
                : new StructureBlockInfo(structureBlockInfo2.pos, this.backupState, null))
            : structureBlockInfo2;
    }
    
    @Override
    protected StructureProcessorType getType() {
        return Isur.RANDOM_BLOCK_MAPPER;
    }

    @Override
    protected <T> Dynamic<T> method_16666(DynamicOps<T> dynamicOps) {
        return new Dynamic<T>(dynamicOps, dynamicOps.createMap(ImmutableMap.of(
            dynamicOps.createString("source_state"), dynamicOps.createString(gson.toJson(BlockState.serialize(NbtOps.INSTANCE, this.sourceState).asString().get())),
            dynamicOps.createString("target_state"), dynamicOps.createString(gson.toJson(BlockState.serialize(JsonOps.INSTANCE, this.targetState).asString().get())),
            dynamicOps.createString("probability"), dynamicOps.createFloat(this.probability),
            dynamicOps.createString("backup_state"), dynamicOps.createString(gson.toJson(BlockState.serialize(JsonOps.INSTANCE, this.backupState).asString().get()))
        )));
    }
    
}