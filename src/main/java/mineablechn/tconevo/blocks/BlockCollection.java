package mineablechn.tconevo.blocks;

import mineablechn.tconevo.Main;
import mineablechn.tconevo.fluid.FluidCollection;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockCollection {
    public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS, Main.name);
    public static final RegistryObject<FlowingFluidBlock> alumite_fluid=BLOCKS.register("alumite_fluid",
            ()->new FlowingFluidBlock(FluidCollection.alumite,Block.Properties.of(Material.LAVA).strength(100.0F).noDrops()));
    public static final RegistryObject<Block> Wopper=BLOCKS.register("wopper",()->
            new HopperBlock(Block.Properties.of(Material.WOOD).strength(3.0F).noOcclusion().sound(SoundType.WOOD)));
}
