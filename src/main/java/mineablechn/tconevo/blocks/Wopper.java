package mineablechn.tconevo.blocks;

import mineablechn.tconevo.tileentity.WopperEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Wopper extends HopperBlock {
    protected Wopper(Properties p_i48446_1_) {
        super(p_i48446_1_);
    }
    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {return new WopperEntity();}
    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof WopperEntity) {
                ((WopperEntity)tileentity).setCustomName(stack.getHoverName());
            }
        }

    }
    @Override
    @Nonnull
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult result) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof WopperEntity) {
                entity.openMenu((WopperEntity)tileentity);
                entity.awardStat(Stats.INSPECT_HOPPER);
            }
            return ActionResultType.CONSUME;
        }
    }
    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState state1, boolean flag) {
        if (!state.is(state1.getBlock())) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof WopperEntity) {
                InventoryHelper.dropContents(worldIn, pos, (WopperEntity)tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, state1, flag);
        }
    }
    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entity) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof WopperEntity) {
            ((WopperEntity)tileentity).entityInside(entity);
        }

    }
}
