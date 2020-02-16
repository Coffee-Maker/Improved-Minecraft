// package net.agricultureimprovements.config;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.function.Function;

// import io.github.prospector.modmenu.api.ModMenuApi;
// import me.shedaniel.clothconfig2.api.ConfigBuilder;
// import me.shedaniel.clothconfig2.api.ConfigCategory;
// import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
// import net.agricultureimprovements.isur;
// import net.minecraft.client.gui.screen.Screen;
// import net.minecraft.entity.effect.StatusEffect;

// public class Config implements ModMenuApi{

//     public static Map<String, StatusEffect> GLAZINGS = new HashMap<String, StatusEffect>();

//     public static String testConfig = "";
//     public static List<String> glazings;

//     public Config(){
//         glazings = new ArrayList<>();
//         glazings.add("minecraft:honey_bottle->minecraft:speed");
//         glazings.add("minecraft:glowstone_dust->minecraft:glowing");
//     }
    
//     @Override
//     public String getModId(){
//         return isur.MODID;
//     }

//     @Override
//     public Function<Screen, ? extends Screen> getConfigScreenFactory() {
//         return parent -> {
//             ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle("title.isur.config");
//             ConfigCategory general = builder.getOrCreateCategory("General");

//             ConfigEntryBuilder entryBuilder = builder.entryBuilder();

//             general.addEntry(entryBuilder.startStrList("option.isur.glazingCombinations", glazings).setDefaultValue(new ArrayList<String>()).setTooltip("[Glazing Item]->[Status Effect]").setSaveConsumer(toValue -> glazings = toValue).build());

//             general.addEntry(new GlazingItemDefinition("name", false));

//             return builder.build();
//         };
//     }
// }