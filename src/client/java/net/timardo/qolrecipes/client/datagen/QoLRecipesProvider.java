package net.timardo.qolrecipes.client.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.timardo.qolrecipes.WarpStoneRepairRecipe;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QoLRecipesProvider extends FabricRecipeProvider {

    public QoLRecipesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                for (var pair : QoLRecipePairs.getBlastingPairs()) {
                    oreBlasting(
                            List.of(pair.first),
                            RecipeCategory.MISC,
                            CookingBookCategory.BLOCKS,
                            pair.second,
                            0.1f,
                            100,
                            "qol_blasting"
                    );
                }

                shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE)
                        .pattern("ggg")
                        .pattern("gag")
                        .pattern("ggg")
                        .define('g', Items.GOLD_INGOT)
                        .define('a', Items.GOLDEN_APPLE)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.GOLDEN_APPLE), has(Items.GOLDEN_APPLE))
                        .save(output);

                SpecialRecipeBuilder.special(WarpStoneRepairRecipe::new).save(output, "qol_warp_stone_repair");
            }
        };
    }
}
