package com.ashaxolotl.partytrick.datagen;

import com.ashaxolotl.partytrick.item.PartyItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.enjarai.trickster.item.ModItems;
import io.vavr.Tuple2;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


// DATA GEN IS EVIl. It may whisper to you "You won't have to write json" or "You'll save so much time". Do not trust its lies you will get lost in it. Your family and friends will slowly leave you while you scream just one fix, and it will all work, just one more tweak, just one more!
public class PartyModelGenerator extends FabricModelProvider {
    public PartyModelGenerator(FabricDataOutput output) {
        super(output);
    }

    // Map holding all variants used for generating the models and getting the max amount of variants
    public final static Map<Item, Tuple2<Model, Integer>> variants = new HashMap<>(Map.of(
            ModItems.WAND, new Tuple2<>(Models.HANDHELD, 5),
            ModItems.LEASH, new Tuple2<>(Models.GENERATED, 2),
            ModItems.MIRROR_OF_EVALUATION, new Tuple2<>(Models.GENERATED, 2)
    ));
    static {
        Arrays.stream(ModItems.COLORED_SCROLLS_AND_QUILLS).toList().forEach(scrollAndQuillItem -> variants.put(scrollAndQuillItem, new Tuple2<>(Models.GENERATED, 10)));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        itemModelGenerator.register(PartyItems.PEPERNOOT, Models.GENERATED);

        // gen for variants
        variants.forEach((item, tuple) -> {
            Model model = tuple._1;
            int amount = tuple._2;
            // gen the main item model
            model.upload(
                    ModelIds.getItemModelId(item),
                    TextureMap.layer0(item),
                    itemModelGenerator.writer,
                    ((id, textures) -> createVariantsJson(id, textures, amount, model))
            );

            // gen the item model for all the variants
            for (int i = 1; i <= amount; i++) {
                itemModelGenerator.register(item, "_" + i, model);
            }
            // gen the extra last one so that it returns to normal if you go over the amount
            model.upload((
                    ModelIds.getItemSubModelId(item, "_" + (amount+1))),
                    TextureMap.layer0(item),
                    itemModelGenerator.writer,
                    (model::createJson)
            );

        });
    }

    private JsonObject createVariantsJson(Identifier id, Map<TextureKey, Identifier> textures, int amount, Model model) {
        JsonObject jsonObject = model.createJson(id, textures);

        JsonArray overridesArray = new JsonArray();
        for (int i = 1; i <= amount+1; i++) {
            JsonObject overrideObject = new JsonObject();
            JsonObject CMDObject = new JsonObject();

            CMDObject.addProperty("custom_model_data", i);

            overrideObject.add("predicate", CMDObject);
            overrideObject.addProperty("model", id.getNamespace() + ":" + id.getPath() + "_" + i);

            overridesArray.add(overrideObject);
        }
        jsonObject.add("overrides", overridesArray);
        return jsonObject;
    }
}
