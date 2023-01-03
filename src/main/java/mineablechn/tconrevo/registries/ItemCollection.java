package mineablechn.tconrevo.registries;

import mineablechn.tconrevo.Main;
import mineablechn.tconrevo.items.TConrevoGroup;
import net.minecraft.item.*;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemCollection {
    public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS, Main.name);
    public static Item alumite_ingot=new Item(new Item.Properties().tab(TConrevoGroup.TAB_MATS)).setRegistryName("alumite_ingot");
    public static Item basic_ingot=new Item(new Item.Properties().tab(TConrevoGroup.TAB_MATS)).setRegistryName("basic_ingot");
    public static Item essence_infused_ingot=new Item(new Item.Properties().tab(TConrevoGroup.TAB_MATS)).setRegistryName("essence_infused_ingot");
    public static Item sided_ingot=new Item(new Item.Properties().tab(TConrevoGroup.TAB_MATS)).setRegistryName("sided_ingot");
    public static final RegistryObject<Item> alumite_bucket=bucket_model("alumite_bucket",FluidCollection.alumite);
    public static final RegistryObject<Item> desh_bucket=bucket_model("desh_bucket",FluidCollection.desh);
    public static final RegistryObject<Item> essence_metal_bucket=bucket_model("essence_metal_bucket",FluidCollection.essence_metal);
    public static RegistryObject<Item> bucket_model(String name, RegistryObject<ForgeFlowingFluid.Source> source){
        return ITEMS.register(name,()->new BucketItem(source,new Item.Properties().tab(ItemGroup.TAB_MISC).craftRemainder(Items.BUCKET).stacksTo(1)));
    }
}
