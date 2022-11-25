package mineablechn.tconevo.tileentity;

import mineablechn.tconevo.Main;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class WopperContainerType {
    private final static ResourceLocation id=new ResourceLocation(Main.name,"wopper");
    public static ContainerType<? extends Container> Wopper= IForgeContainerType.create((pWindowID, pInventory, pData) -> {
        BlockPos pos = pData.readBlockPos();
        return new WopperContainer(pWindowID, pInventory, pos);
    }).setRegistryName(id);
}
