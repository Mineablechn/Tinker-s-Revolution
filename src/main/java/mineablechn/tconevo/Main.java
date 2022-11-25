package mineablechn.tconevo;

import mineablechn.tconevo.blocks.BlockCollection;
import mineablechn.tconevo.blocks.cilent.WopperScreen;
import mineablechn.tconevo.fluid.FluidCollection;
import mineablechn.tconevo.items.ItemCollection;

import mineablechn.tconevo.tileentity.TileEntityCollection;
import mineablechn.tconevo.tileentity.WopperContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static mineablechn.tconevo.tileentity.WopperContainerType.Wopper;

@Mod(Main.name)
public class Main {
    public static final String name="tconevo";
    public Main(){
        IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();
        FluidCollection.FLUIDS.register(bus);
        ItemCollection.ITEMS.register(bus);
        BlockCollection.BLOCKS.register(bus);
        TileEntityCollection.ENTITIES.register(bus);
    }
    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void itemRegistry(final RegistryEvent.Register<Item> itemRegister) {
            itemRegister.getRegistry().registerAll(
                    ItemCollection.alumite_ingot//normal way
            );
        }
        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(Wopper);
        }
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onClientRegister(FMLClientSetupEvent event) {
            ScreenManager.register((ContainerType<WopperContainer>) Wopper, WopperScreen::new);
        }
    }
}
