package mineablechn.tconevo.utils;

import com.mojang.datafixers.types.Type;
import mineablechn.tconevo.blocks.BlockCollection;
import mineablechn.tconevo.tileentity.WopperEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;


public class TileEntityType<T extends TileEntity> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.tileentity.TileEntityType<?>>{
    public static final net.minecraft.tileentity.TileEntityType<WopperEntity> WHOPPER=register(net.minecraft.tileentity.TileEntityType.Builder.of(WopperEntity::new, BlockCollection.Wopper.get()));
    private static <T extends TileEntity> net.minecraft.tileentity.TileEntityType<T> register(net.minecraft.tileentity.TileEntityType.Builder<T> p_200966_1_) {
        Type<?> type = Util.fetchChoiceType(TypeReferences.BLOCK_ENTITY, "wopper");
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, "wopper", p_200966_1_.build(null));
    }
}
