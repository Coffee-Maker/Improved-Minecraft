// package net.agricultureimprovements.config;

// import java.util.List;
// import java.util.Optional;

// import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.gui.DrawableHelper;
// import net.minecraft.client.gui.Element;

// public class GlazingItemDefinition extends AbstractConfigListEntry<GlazingPairData> {

//     private GlazingPairData value;
    
//     public GlazingItemDefinition(String fieldName, boolean requiresRestart) {
//         super(fieldName, requiresRestart);
//     }

//     @Override
//     public List<? extends Element> children() {
//         return null;
//     }

//     @Override
//     public GlazingPairData getValue() {
//         return value;
//     }

//     @Override
//     public Optional<GlazingPairData> getDefaultValue() {
//         return null;
//     }

//     @Override
//     public void save() {

//     }

//     @Override
//     public void render(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, float var9) {
//         DrawableHelper.fill(0, 50, 300, 150, var8 ? 0xFFFFFFFF : 0xFFAAAAAA);
//         MinecraftClient.getInstance().textRenderer.draw("Test", 0, 50, 0XFF000000);
//     }

// }