package mineablechn.tconevo.tileentity;

import mineablechn.tconevo.Main;
import mineablechn.tconevo.blocks.BlockCollection;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityCollection {
    public static final DeferredRegister<TileEntityType<?>> ENTITIES=DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.name);
    public static final RegistryObject<TileEntityType<WopperEntity>> wopper_entity=ENTITIES.register("wopper_entity",()->TileEntityType.Builder.of(WopperEntity::new, BlockCollection.Wopper.get()).build(null));
}
