package mineablechn.tconrevo.modifiers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class Elements extends Modifier {
    public Elements(){
        super(0x999999);
    }
    @Override
    public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt){
        LivingEntity target=context.getLivingTarget();
        LivingEntity attacker=context.getAttacker();
        Vector3d vector3d=attacker.getLookAngle();
        FireballEntity entity=new FireballEntity(attacker.level, attacker,vector3d.x*100,vector3d.y*100,vector3d.z*100);
        entity.setPos(attacker.getX()+vector3d.x*4.0D,attacker.getY(0.5D)+0.5D,entity.getZ()+vector3d.z*100);
        entity.explosionPower=100;
        context.getAttacker().level.addFreshEntity(entity);
        if(target.isOnFire()){
            return level*target.getRemainingFireTicks()*10;
        }
        else{
            target.setSecondsOnFire(20);
            return level;
        }
    }
    @Override
    public ActionResultType onToolUse(IModifierToolStack tool, int level, World world, PlayerEntity player, Hand hand, EquipmentSlotType slot) {
        if (player.isOnFire()) player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE,2,200));
        else if(player.isSwimming()) player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE,2,200));
        else if(player.fallDistance>=20) player.addEffect(new EffectInstance(Effects.SLOW_FALLING,2,200));
        else player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED,2,200));
        return ActionResultType.SUCCESS;
    }
}
