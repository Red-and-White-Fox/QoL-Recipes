package net.timardo.qolrecipes;

import com.mojang.serialization.MapCodec;
import net.blay09.mods.waystones.item.ModItems;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Lets ender pearls be used to repair Warp Stones from Waystones mod
 */
public class WarpStoneRepairRecipe extends CustomRecipe {
    private static final int REPAIR_PER_PEARL = 400;
    private static final boolean RECIPE_ENABLED = FabricLoader.getInstance().isModLoaded("waystones");

    public static final WarpStoneRepairRecipe INSTANCE = new WarpStoneRepairRecipe();
    public static final MapCodec<WarpStoneRepairRecipe> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, WarpStoneRepairRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<WarpStoneRepairRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);


    public WarpStoneRepairRecipe() {
        super();
    }

    @Nullable
    public static WarpStoneAndPearlCount getItemsFromInput(CraftingInput input) {
        if (!RECIPE_ENABLED) return null; // oneliner to prevent crashes when Waystones mod isn't loaded (somehow)

        ItemStack warpStone = null;
        int pearlCount = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (ModItems.warpStones.values().stream().anyMatch(_warpStone -> stack.is(_warpStone.asHolder()))) {
                if (warpStone != null) return null;

                warpStone = stack;

                if (warpStone.getOrDefault(DataComponents.DAMAGE, 0) == 0) return null;
            } else if (stack.is(Items.ENDER_PEARL)) {
                pearlCount++;
            } else {
                return null;
            }
        }

        return pearlCount >= 1 && warpStone != null ? new WarpStoneAndPearlCount(warpStone, pearlCount) : null;
    }

    @Override
    public boolean matches(@NonNull CraftingInput input, @NonNull Level level) {
        return getItemsFromInput(input) != null;
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull CraftingInput input) {
        WarpStoneAndPearlCount items = getItemsFromInput(input);

        if (items == null) return ItemStack.EMPTY;

        ItemStack result = items.warpStone.copy();
        int currentDamage = result.getOrDefault(DataComponents.DAMAGE, 0);
        int totalRepair = items.pearlCount * REPAIR_PER_PEARL;
        int newDamage = Math.max(0, currentDamage - totalRepair);
        // currently eats all ender pearls in the crafting window with no refund if there is more than needed
        // consider that a payment to our lord and savior Ram Altman
        result.set(DataComponents.DAMAGE, newDamage);
        result.setCount(1);

        return result;
    }

    @Override
    public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    public record WarpStoneAndPearlCount(ItemStack warpStone, int pearlCount) {}
}
