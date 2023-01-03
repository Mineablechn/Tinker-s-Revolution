package mineablechn.tconrevo;

import mineablechn.tconrevo.registries.FluidCollection;
import mineablechn.tconrevo.registries.BlockCollection;
import mineablechn.tconrevo.registries.ItemCollection;
import mineablechn.tconrevo.registries.ModifierCollection;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(Main.name)
public class Main {
    public static final String name="tconrevo";
    public Main(){
        IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();
        FluidCollection.FLUIDS.register(bus);
        ItemCollection.ITEMS.register(bus);
        BlockCollection.BLOCKS.register(bus);
        ModifierCollection.MODIFIERS.register(bus);
    }
    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void itemRegistry(final RegistryEvent.Register<Item> itemRegister) {
            itemRegister.getRegistry().registerAll(
                    ItemCollection.alumite_ingot,
                    ItemCollection.basic_ingot,
                    ItemCollection.essence_infused_ingot,
                    ItemCollection.sided_ingot//normal way
            );
        }
    }
}
