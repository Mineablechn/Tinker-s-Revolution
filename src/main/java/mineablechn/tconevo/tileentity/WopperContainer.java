package mineablechn.tconevo.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class WopperContainer extends Container {
    private final IInventory wopperEntity;
    protected WopperContainer(int id, PlayerInventory inv,WopperEntity entity) {
        super(WopperContainerType.Wopper, id);
        this.wopperEntity = entity;
        checkContainerSize(entity, 1);
        entity.startOpen(inv.player);
        int i = 51;
        this.addSlot(new Slot(entity,0,80, 20));//numbers here:thecech12
        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1, 8 + i1 * 18, 109));
        }
    }
    public WopperContainer(int id, PlayerInventory playerInventoryIn, BlockPos pos) {
        this(id, playerInventoryIn, (WopperEntity) playerInventoryIn.player.level.getBlockEntity(pos));
    }
    @Override
    @Nonnull
    public ItemStack quickMoveStack(@Nonnull PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.wopperEntity.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.wopperEntity.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.wopperEntity.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
    @Override
    public void removed(@Nonnull PlayerEntity playerIn) {
        super.removed(playerIn);
        this.wopperEntity.stopOpen(playerIn);
    }
    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return this.wopperEntity.stillValid(playerIn);
    }
}
