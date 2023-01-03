package mineablechn.tconrevo.items;

import mineablechn.tconrevo.Main;
import mineablechn.tconrevo.registries.ItemCollection;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.SupplierItemGroup;

public class TConrevoGroup{
    public static final ItemGroup TAB_MATS=new SupplierItemGroup(Main.name,"materials",()->new ItemStack(ItemCollection.basic_ingot.getItem()));
}
