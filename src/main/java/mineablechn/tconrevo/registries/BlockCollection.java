package mineablechn.tconrevo.registries;

import mineablechn.tconrevo.Main;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockCollection {
    public static final DeferredRegister<Block> BLOCKS=DeferredRegister.create(ForgeRegistries.BLOCKS, Main.name);
    public static final RegistryObject<FlowingFluidBlock> alumite_fluid=BLOCKS.register("alumite_fluid",
            ()->new FlowingFluidBlock(FluidCollection.alumite,Block.Properties.of(Material.LAVA).strength(100.0F).noDrops()));
    public static final RegistryObject<FlowingFluidBlock> desh_fluid=BLOCKS.register("desh_fluid",
            ()->new FlowingFluidBlock(FluidCollection.desh, AbstractBlock.Properties.of(Material.LAVA).strength(100.0F).noDrops()));
    public static final RegistryObject<FlowingFluidBlock> essence_metal_block_fluid=FluidModel("essence_metal_fluid",FluidCollection.essence_metal);
    public static RegistryObject<FlowingFluidBlock> FluidModel(String name, RegistryObject<ForgeFlowingFluid.Source> source){
        return BLOCKS.register(name,()->new FlowingFluidBlock(source,AbstractBlock.Properties.of(Material.LAVA).strength(100.0F).noDrops()));
    }


}
