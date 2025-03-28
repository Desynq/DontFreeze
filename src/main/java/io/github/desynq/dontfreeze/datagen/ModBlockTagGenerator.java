package io.github.desynq.dontfreeze.datagen;

import io.github.desynq.dontfreeze.DontFreeze;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, DontFreeze.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Blocks.GLOWSTONE);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Blocks.GLOWSTONE);

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(Blocks.POWDER_SNOW);
    }
}
