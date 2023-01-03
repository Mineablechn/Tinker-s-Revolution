package mineablechn.tconrevo.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class BloodyMary extends Modifier {
    public BloodyMary() {
        super(0xe60000);
    }
    @Override
    public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt){
        LivingEntity target=context.getLivingTarget();
        float maxhealth=target.getMaxHealth();
        float nowhealth=target.getHealth();
        target.hurt(DamageSource.MAGIC, (float) (damageDealt+maxhealth/nowhealth*0.2*level));
        return 0;
    }
}
