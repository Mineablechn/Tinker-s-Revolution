package mineablechn.tconevo.blocks;

import mineablechn.tconevo.tileentity.WopperEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class WopperInv extends InvWrapper {
    private final WopperEntity wopper;

    public WopperInv(WopperEntity wopperEntity) {
        super(wopperEntity);
        this.wopper=wopperEntity;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot,ItemStack stack,boolean simulate){
        if (simulate) {
            return super.insertItem(slot, stack, true);
        } else {
            boolean wasEmpty = getInv().isEmpty();
            int originalStackSize = stack.getCount();
            stack = super.insertItem(slot, stack, false);
            if (wasEmpty && originalStackSize > stack.getCount())
            {
                if (!wopper.mayTransfer())
                {
                    wopper.setCooldown(10);
                }
            }
            return stack;
        }
    }
}
