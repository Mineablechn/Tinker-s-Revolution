package mineablechn.tconrevo.registries;

import mineablechn.tconrevo.Main;
import mineablechn.tconrevo.modifiers.Apocalypse;
import mineablechn.tconrevo.modifiers.BloodyMary;
import mineablechn.tconrevo.modifiers.DiceKing;
import mineablechn.tconrevo.modifiers.Elements;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class ModifierCollection {
    public static final DeferredRegister<Modifier> MODIFIERS=DeferredRegister.create(Modifier.class,Main.name);
    public static final RegistryObject<Apocalypse> wither=MODIFIERS.register("apocalypse", Apocalypse::new);
    public static final RegistryObject<BloodyMary> bloodymary=MODIFIERS.register("bloodymary",BloodyMary::new);
    public static final RegistryObject<DiceKing> dice=MODIFIERS.register("dice",DiceKing::new);
    public static final RegistryObject<Elements> elements=MODIFIERS.register("elements",Elements::new);
    //public static final RegistryObject<GaiaWraith> gaia_wraith=MODIFIERS.register("gaia_wraith",GaiaWraith::new);
}
