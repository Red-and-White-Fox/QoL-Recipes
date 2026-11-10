package net.timardo.qolrecipes;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QoLRecipes implements ModInitializer {
	public static final String MOD_ID = "qol-recipes";

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id("qol_warp_stone_repair"), WarpStoneRepairRecipe.SERIALIZER);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
