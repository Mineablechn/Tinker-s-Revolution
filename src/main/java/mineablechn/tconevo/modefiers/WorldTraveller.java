package mineablechn.tconevo.modefiers;

import net.minecraft.entity.LivingEntity;
import org.apache.http.util.EntityUtils;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class WorldTraveller extends Modifier {
    public WorldTraveller(int color) {
        super(0xFFFFCCFF);
    }
    @Override
    public float getEntityDamage(IModifierToolStack toolStack, int level, ToolAttackContext context,float base,float finaldamage){
        LivingEntity target=context.getLivingTarget();
        return base+10.0F;
    }
}
