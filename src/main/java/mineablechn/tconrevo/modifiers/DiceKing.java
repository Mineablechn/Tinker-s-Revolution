package mineablechn.tconrevo.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Random;

public class DiceKing extends Modifier {
    public DiceKing() {super(0xcccc00);}
    @Override
    public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        double damage=Math.random();
        LivingEntity target=context.getLivingTarget();
        target.hurt(DamageSource.MAGIC,(float) (damage*8.0D+level));
        return 0;
    }
}
