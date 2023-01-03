package mineablechn.tconrevo.registries;


import mineablechn.tconrevo.Main;
import mineablechn.tconrevo.fluid.FluidIcon;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FluidCollection {
   public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(ForgeRegistries.FLUIDS,Main.name);
   public static final RegistryObject<ForgeFlowingFluid.Source> alumite=FLUIDS.register("alumite",()-> new ForgeFlowingFluid.Source(FluidCollection.Prop));
   public static final RegistryObject<ForgeFlowingFluid.Flowing> alumitef=FLUIDS.register("alumitef",()-> new ForgeFlowingFluid.Flowing(FluidCollection.Prop));
   public static final RegistryObject<ForgeFlowingFluid.Source> desh=FLUIDS.register("desh",()->new ForgeFlowingFluid.Source(FluidCollection.Prop2));
   public static final RegistryObject<ForgeFlowingFluid.Flowing> deshf=FLUIDS.register("deshf",()->new ForgeFlowingFluid.Flowing(FluidCollection.Prop2));
   public static final RegistryObject<ForgeFlowingFluid.Source> essence_metal=FLUIDS.register("essence_metal",()->new ForgeFlowingFluid.Source(FluidCollection.Prop3));
   public static final RegistryObject<ForgeFlowingFluid.Flowing> essence_metalf=FLUIDS.register("essence_metalf",()->new ForgeFlowingFluid.Flowing(FluidCollection.Prop3));
   public static ForgeFlowingFluid.Properties Prop=new ForgeFlowingFluid.Properties(alumite,alumitef,
           FluidAttributes.builder(FluidIcon.Hot_Liquid,FluidIcon.Flowing_Lava).color(0xFFFFC0CB).luminosity(2).viscosity(250).density(300).temperature(1000).overlay(FluidIcon.Hot_Liquid))
           .bucket(ItemCollection.alumite_bucket).block(BlockCollection.alumite_fluid);
   public static ForgeFlowingFluid.Properties Prop2=new ForgeFlowingFluid.Properties(desh,deshf,FluidAttributes.builder(FluidIcon.Hot_Liquid,FluidIcon.Flowing_Lava).color(0xFFFF751A).luminosity(2)
           .viscosity(350).temperature(2000).overlay(FluidIcon.Hot_Liquid)).bucket(ItemCollection.desh_bucket).block(BlockCollection.desh_fluid);
   public static ForgeFlowingFluid.Properties Prop3=Propmodel(1500,1200,ItemCollection.essence_metal_bucket,BlockCollection.essence_metal_block_fluid,essence_metal,essence_metalf,0xFF009933);
   public static ForgeFlowingFluid.Properties Propmodel(int visc, int temp, Supplier<? extends Item> bucket, Supplier<? extends FlowingFluidBlock> block, RegistryObject<ForgeFlowingFluid.Source> still, RegistryObject<ForgeFlowingFluid.Flowing> flowing, int color){
      return new ForgeFlowingFluid.Properties(still,flowing,FluidAttributes.builder(FluidIcon.Hot_Liquid,FluidIcon.Flowing_Lava).color(color).luminosity(2).viscosity(visc).temperature(temp).overlay(FluidIcon.Hot_Liquid)
              ).bucket(bucket).block(block);
   }
}