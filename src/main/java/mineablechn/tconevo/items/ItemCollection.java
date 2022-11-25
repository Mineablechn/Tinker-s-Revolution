package mineablechn.tconevo.items;

import mineablechn.tconevo.Main;
import mineablechn.tconevo.blocks.BlockCollection;
import mineablechn.tconevo.fluid.FluidCollection;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemCollection {
    public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS, Main.name);
    public static Item alumite_ingot=new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName("alumite_ingot");
    public static final RegistryObject<Item> alumite_bucket=ITEMS.register("alumite_bucket",()-> new BucketItem(FluidCollection.alumite,new Item.Properties().tab(ItemGroup.TAB_MISC).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> wopper=ITEMS.register("wopper",()->new BlockItem(BlockCollection.Wopper.get(),new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
}
