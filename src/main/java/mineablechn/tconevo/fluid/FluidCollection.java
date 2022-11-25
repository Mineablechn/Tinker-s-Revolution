package mineablechn.tconevo.fluid;


import mineablechn.tconevo.Main;
import mineablechn.tconevo.blocks.BlockCollection;
import mineablechn.tconevo.items.ItemCollection;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidCollection {
   //protected static final FluidDeferredRegister FLUIDS=new FluidDeferredRegister(Main.name);
   public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(ForgeRegistries.FLUIDS,Main.name);
   public static final RegistryObject<ForgeFlowingFluid.Source> alumite=FLUIDS.register("alumite",()-> new ForgeFlowingFluid.Source(FluidCollection.Prop));
   public static final RegistryObject<ForgeFlowingFluid.Flowing> alumitef=FLUIDS.register("alumitef",()-> new ForgeFlowingFluid.Flowing(FluidCollection.Prop));
   public static ForgeFlowingFluid.Properties Prop=new ForgeFlowingFluid.Properties(alumite,alumitef,
           FluidAttributes.builder(FluidIcon.Hot_Liquid,FluidIcon.Flowing_Lava).color(0xFFFFC0CB).luminosity(2).viscosity(250).density(300).temperature(1000).overlay(FluidIcon.Hot_Liquid))
           .bucket(ItemCollection.alumite_bucket).block(BlockCollection.alumite_fluid);
  //public static final FluidObject<ForgeFlowingFluid> alumite=FLUIDS.register("alumite",hotBuilder().temperature(2000), Material.LAVA,10);
   //private static FluidAttributes.Builder hotBuilder() {
   //   return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA);
   //}
}