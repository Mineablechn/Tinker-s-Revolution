package mineablechn.tconrevo.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class Apocalypse extends Modifier {

    public Apocalypse() {
        super(0xFFCCFF);
    }
    @Override
    public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt){
        LivingEntity target=context.getLivingTarget();
        if(target!=null&&target.isAlive()){
            int lvl=-1;
            EffectInstance effect=target.getEffect(Effects.WITHER);
            if (effect!=null) lvl=effect.getAmplifier();
            lvl= Math.min(3,lvl+1);
            target.addEffect(new EffectInstance(Effects.WITHER,130,lvl));
        }
        return 0;
    }
}
