package net.timardo.qolrecipes.client.datagen;

import com.ibm.icu.impl.Pair;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ColorCollection;

import java.util.ArrayList;
import java.util.List;

public class QoLRecipePairs {
    public static List<Pair<ItemLike, ItemLike>> getBlastingPairs() {
        final ArrayList<Pair<ItemLike, ItemLike>> pairs = new ArrayList<>(List.of(
                Pair.of(Items.COBBLESTONE, Items.STONE),
                Pair.of(Items.STONE, Items.SMOOTH_STONE),
                Pair.of(Items.STONE_BRICKS, Items.CRACKED_STONE_BRICKS),
                Pair.of(Items.COBBLED_DEEPSLATE, Items.DEEPSLATE),
                Pair.of(Items.DEEPSLATE_BRICKS, Items.CRACKED_DEEPSLATE_BRICKS),
                Pair.of(Items.DEEPSLATE_TILES, Items.CRACKED_DEEPSLATE_TILES),
                Pair.of(Items.NETHERRACK, Items.NETHER_BRICK),
                Pair.of(Items.NETHER_BRICKS, Items.CRACKED_NETHER_BRICKS),
                Pair.of(Items.POLISHED_BLACKSTONE_BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS),
                Pair.of(Items.CLAY_BALL, Items.BRICK),
                Pair.of(Items.CLAY, Items.TERRACOTTA),
                Pair.of(Items.SAND, Items.GLASS),
                Pair.of(Items.RED_SAND, Items.GLASS),
                Pair.of(Items.SANDSTONE, Items.SMOOTH_SANDSTONE),
                Pair.of(Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE),
                Pair.of(Items.BASALT, Items.SMOOTH_BASALT),
                Pair.of(Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ)
        ));

        ColorCollection.zipApply(Items.DYED_TERRACOTTA, Items.GLAZED_TERRACOTTA, (dyed, glazed) -> pairs.add(Pair.of(dyed, glazed)));

        return pairs;
    }
}
