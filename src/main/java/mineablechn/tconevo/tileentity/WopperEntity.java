package mineablechn.tconevo.tileentity;

//Textures are used from TConstruct2.
//Code is copied form vanilla Minecraft,thanks to thecech12.
import mineablechn.tconevo.blocks.Wopper;
import mineablechn.tconevo.blocks.WopperInv;
import mineablechn.tconevo.utils.TileEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class WopperEntity extends LockableLootTileEntity implements IHopper, ITickableTileEntity {
    private NonNullList<ItemStack> items=NonNullList.withSize(1,ItemStack.EMPTY);
    public long tickedGameTime;
    private int cooldownTime=-1;
    public WopperEntity(){
        super(TileEntityType.WHOPPER);
        this.setItems(NonNullList.withSize(1, ItemStack.EMPTY));
    }
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_230337_2_)) {
            ItemStackHelper.loadAllItems(p_230337_2_, this.items);
        }

        this.cooldownTime = p_230337_2_.getInt("TransferCooldown");
    }
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        super.save(p_189515_1_);
        if (!this.trySaveLootTable(p_189515_1_)) {
            ItemStackHelper.saveAllItems(p_189515_1_, this.items);
        }
        p_189515_1_.putInt("TransferCooldown", this.cooldownTime);
        return p_189515_1_;
    }
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        this.unpackLootTable(null);
        return ItemStackHelper.removeItem(this.getItems(), p_70298_1_, p_70298_2_);
    }
    private static boolean canTakeItemFromContainer(IInventory p_174921_0_, ItemStack p_174921_1_, int p_174921_2_, Direction p_174921_3_) {
        return !(p_174921_0_ instanceof ISidedInventory) || ((ISidedInventory)p_174921_0_).canTakeItemThroughFace(p_174921_2_, p_174921_1_, p_174921_3_);
    }
    private static boolean tryTakeInItemFromSlot(IHopper p_174915_0_, IInventory p_174915_1_, int p_174915_2_, Direction p_174915_3_) {
        ItemStack itemstack = p_174915_1_.getItem(p_174915_2_);
        if (!itemstack.isEmpty() && canTakeItemFromContainer(p_174915_1_, itemstack, p_174915_2_, p_174915_3_)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = addItem(p_174915_1_, p_174915_0_, p_174915_1_.removeItem(p_174915_2_, 1), (Direction)null);
            if (itemstack2.isEmpty()) {
                p_174915_1_.setChanged();
                return true;
            }

            p_174915_1_.setItem(p_174915_2_, itemstack1);
        }

        return false;
    }
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        this.unpackLootTable(null);
        this.getItems().set(p_70299_1_, p_70299_2_);
        if (p_70299_2_.getCount() > this.getMaxStackSize()) {
            p_70299_2_.setCount(this.getMaxStackSize());
        }
    }
    @Override
    public double getLevelX() {
        return this.worldPosition.getX()+0.5D;
    }

    @Override
    public double getLevelY() {
        return this.worldPosition.getY()+0.5D;
    }

    @Override
    public double getLevelZ() {
        return this.worldPosition.getX()+0.5D;
    }
    @Nullable
    public static Boolean extractHook(IHopper dest)
    {
        return getItemHandler(dest, Direction.UP)
                .map(itemHandlerResult -> {
                    IItemHandler handler = itemHandlerResult.getKey();

                    for (int i = 0; i < handler.getSlots(); i++)
                    {
                        ItemStack extractItem = handler.extractItem(i, 1, true);
                        if (!extractItem.isEmpty())
                        {
                            for (int j = 0; j < dest.getContainerSize(); j++)
                            {
                                ItemStack destStack = dest.getItem(j);
                                if (dest.canPlaceItem(j, extractItem) && (destStack.isEmpty() || destStack.getCount() < destStack.getMaxStackSize() && destStack.getCount() < dest.getMaxStackSize() && ItemHandlerHelper.canItemStacksStack(extractItem, destStack)))
                                {
                                    extractItem = handler.extractItem(i, 1, false);
                                    if (destStack.isEmpty())
                                        dest.setItem(j, extractItem);
                                    else
                                    {
                                        destStack.grow(1);
                                        dest.setItem(j, destStack);
                                    }
                                    dest.setChanged();
                                    return true;
                                }
                            }
                        }
                    }

                    return false;
                })
                .orElse(null);
    }
    public static boolean suckInItems(IHopper p_145891_0_) {
        Boolean ret = extractHook(p_145891_0_);
        if (ret != null) return ret;
        IInventory iinventory = getSourceContainer(p_145891_0_);
        if (iinventory != null) {
            Direction direction = Direction.DOWN;
            return !isEmptyContainer(iinventory, direction) && getSlots(iinventory, direction).anyMatch((p_213971_3_) -> tryTakeInItemFromSlot(p_145891_0_, iinventory, p_213971_3_, direction));
        } else {
            for(ItemEntity itementity : getItemsAtAndAbove(p_145891_0_)) {
                if (addItem(p_145891_0_, itementity)) {
                    return true;
                }
            }

            return false;
        }
    }
    private static boolean isEmptyContainer(IInventory p_174917_0_, Direction p_174917_1_) {
        return getSlots(p_174917_0_, p_174917_1_).allMatch((p_213973_1_) -> p_174917_0_.getItem(p_213973_1_).isEmpty());
    }
    private static boolean canMergeItems(ItemStack p_145894_0_, ItemStack p_145894_1_) {
        if (p_145894_0_.getItem() != p_145894_1_.getItem()) {
            return false;
        } else if (p_145894_0_.getDamageValue() != p_145894_1_.getDamageValue()) {
            return false;
        } else if (p_145894_0_.getCount() > p_145894_0_.getMaxStackSize()) {
            return false;
        } else {
            return ItemStack.tagMatches(p_145894_0_, p_145894_1_);
        }
    }
    @Override
    @Nonnull
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems( NonNullList<ItemStack>  stacks) {
        this.items=stacks;
    }

    @Override
    @Nonnull
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.tconevo.wopper");
    }

    @Override
    @Nonnull
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return new WopperContainer(p_213906_1_,p_213906_2_,this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public void tick() {
        if (this.level != null && !this.level.isClientSide) {
            --this.cooldownTime;
            this.tickedGameTime = this.level.getGameTime();
            if (!this.isOnCooldown()) {
                this.setCooldown(0);
                this.tryMoveItems(() -> suckInItems(this));
            }
        }
    }
    private static boolean canPlaceItemInContainer(IInventory p_174920_0_, ItemStack p_174920_1_, int p_174920_2_, @Nullable Direction p_174920_3_) {
        if (!p_174920_0_.canPlaceItem(p_174920_2_, p_174920_1_)) {
            return false;
        } else {
            return !(p_174920_0_ instanceof ISidedInventory) || ((ISidedInventory)p_174920_0_).canPlaceItemThroughFace(p_174920_2_, p_174920_1_, p_174920_3_);
        }
    }
    public static ItemStack addItem(@Nullable IInventory p_174918_0_, IInventory p_174918_1_, ItemStack p_174918_2_, @Nullable Direction p_174918_3_) {
        if (p_174918_1_ instanceof ISidedInventory && p_174918_3_ != null) {
            ISidedInventory isidedinventory = (ISidedInventory)p_174918_1_;
            int[] aint = isidedinventory.getSlotsForFace(p_174918_3_);

            for(int k = 0; k < aint.length && !p_174918_2_.isEmpty(); ++k) {
                p_174918_2_ = tryMoveInItem(p_174918_0_, p_174918_1_, p_174918_2_, aint[k], p_174918_3_);
            }
        } else {
            int i = p_174918_1_.getContainerSize();

            for(int j = 0; j < i && !p_174918_2_.isEmpty(); ++j) {
                p_174918_2_ = tryMoveInItem(p_174918_0_, p_174918_1_, p_174918_2_, j, p_174918_3_);
            }
        }

        return p_174918_2_;
    }
    private static ItemStack tryMoveInItem(@Nullable IInventory p_174916_0_, IInventory p_174916_1_, ItemStack p_174916_2_, int p_174916_3_, @Nullable Direction p_174916_4_) {
        ItemStack itemstack = p_174916_1_.getItem(p_174916_3_);
        if (canPlaceItemInContainer(p_174916_1_, p_174916_2_, p_174916_3_, p_174916_4_)) {
            boolean flag = false;
            boolean flag1 = p_174916_1_.isEmpty();
            if (itemstack.isEmpty()) {
                p_174916_1_.setItem(p_174916_3_, p_174916_2_);
                p_174916_2_ = ItemStack.EMPTY;
                flag = true;
            } else if (canMergeItems(itemstack, p_174916_2_)) {
                int i = p_174916_2_.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(p_174916_2_.getCount(), i);
                p_174916_2_.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag) {
                if (flag1 && p_174916_1_ instanceof WopperEntity) {
                    WopperEntity hoppertileentity1 = (WopperEntity)p_174916_1_;
                    if (!hoppertileentity1.isOnCustomCooldown()) {
                        int k = 0;
                        if (p_174916_0_ instanceof WopperEntity) {
                            WopperEntity hoppertileentity = (WopperEntity) p_174916_0_;
                            if (hoppertileentity1.tickedGameTime >= hoppertileentity.tickedGameTime) {
                                k = 1;
                            }
                        }

                        hoppertileentity1.setCooldown(10 - k);
                    }
                }

                p_174916_1_.setChanged();
            }
        }

        return p_174916_2_;
    }
    public static boolean addItem(IInventory p_200114_0_, ItemEntity p_200114_1_) {
        boolean flag = false;
        ItemStack itemstack = p_200114_1_.getItem().copy();
        ItemStack itemstack1 = addItem((IInventory)null, p_200114_0_, itemstack, (Direction)null);
        if (itemstack1.isEmpty()) {
            flag = true;
            p_200114_1_.remove();
        } else {
            p_200114_1_.setItem(itemstack1);
        }

        return flag;
    }
    public boolean isOnCustomCooldown() {
        return this.cooldownTime > 10;
    }
    public void setCooldown(int num){this.cooldownTime=num;}
    public boolean isOnCooldown(){return cooldownTime>0;}
    public void entityInside(Entity p_200113_1_) {
        if (p_200113_1_ instanceof ItemEntity) {
            BlockPos blockpos = this.getBlockPos();
            if (VoxelShapes.joinIsNotEmpty(VoxelShapes.create(p_200113_1_.getBoundingBox().move(-blockpos.getX(), -blockpos.getY(), -blockpos.getZ())), this.getSuckShape(), IBooleanFunction.AND)) {
                this.tryMoveItems(() -> addItem(this, (ItemEntity)p_200113_1_));
            }
        }

    }
    public boolean mayTransfer() {
        return this.cooldownTime > 10;
    }
    public static List<ItemEntity> getItemsAtAndAbove(IHopper p_200115_0_) {
        return p_200115_0_.getSuckShape().toAabbs().stream().flatMap((p_200110_1_) -> {
            return p_200115_0_.getLevel().getEntitiesOfClass(ItemEntity.class, p_200110_1_.move(p_200115_0_.getLevelX() - 0.5D, p_200115_0_.getLevelY() - 0.5D, p_200115_0_.getLevelZ() - 0.5D), EntityPredicates.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }
    @Nullable
    public static IInventory getContainerAt(World p_195484_0_, BlockPos p_195484_1_) {
        return getContainerAt(p_195484_0_, (double)p_195484_1_.getX() + 0.5D, (double)p_195484_1_.getY() + 0.5D, (double)p_195484_1_.getZ() + 0.5D);
    }
    @Nullable
    public static IInventory getContainerAt(World p_145893_0_, double p_145893_1_, double p_145893_3_, double p_145893_5_) {
        IInventory iinventory = null;
        BlockPos blockpos = new BlockPos(p_145893_1_, p_145893_3_, p_145893_5_);
        BlockState blockstate = p_145893_0_.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof ISidedInventoryProvider) {
            iinventory = ((ISidedInventoryProvider)block).getContainer(blockstate, p_145893_0_, blockpos);
        } else if (blockstate.hasTileEntity()) {
            TileEntity tileentity = p_145893_0_.getBlockEntity(blockpos);
            if (tileentity instanceof IInventory) {
                iinventory = (IInventory)tileentity;
                if (iinventory instanceof ChestTileEntity && block instanceof ChestBlock) {
                    iinventory = ChestBlock.getContainer((ChestBlock)block, blockstate, p_145893_0_, blockpos, true);
                }
            }
        }

        if (iinventory == null) {
            List<Entity> list = p_145893_0_.getEntities((Entity)null, new AxisAlignedBB(p_145893_1_ - 0.5D, p_145893_3_ - 0.5D, p_145893_5_ - 0.5D, p_145893_1_ + 0.5D, p_145893_3_ + 0.5D, p_145893_5_ + 0.5D), EntityPredicates.CONTAINER_ENTITY_SELECTOR);
            if (!list.isEmpty()) {
                iinventory = (IInventory)list.get(p_145893_0_.random.nextInt(list.size()));
            }
        }

        return iinventory;
    }
    @Override
    @Nonnull
    protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
        return new WopperInv(this);
    }
    private static Optional<Pair<IItemHandler, Object>> getItemHandler(IHopper hopper, Direction hopperFacing)
    {
        double x = hopper.getLevelX() + (double) hopperFacing.getStepX();
        double y = hopper.getLevelY() + (double) hopperFacing.getStepY();
        double z = hopper.getLevelZ() + (double) hopperFacing.getStepZ();
        return getItemHandler(hopper.getLevel(), x, y, z, hopperFacing.getOpposite());
    }
    public static Optional<Pair<IItemHandler, Object>> getItemHandler(World worldIn, double x, double y, double z, final Direction side)
    {
        int i = MathHelper.floor(x);
        int j = MathHelper.floor(y);
        int k = MathHelper.floor(z);
        BlockPos blockpos = new BlockPos(i, j, k);
        net.minecraft.block.BlockState state = worldIn.getBlockState(blockpos);

        if (state.hasTileEntity())
        {
            TileEntity tileentity = worldIn.getBlockEntity(blockpos);
            if (tileentity != null)
            {
                return tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
                        .map(capability -> ImmutablePair.<IItemHandler, Object>of(capability, tileentity));
            }
        }

        return Optional.empty();
    }
    @Nullable
    private IInventory getAttachedContainer() {
        Direction direction = this.getBlockState().getValue(Wopper.FACING);
        return getContainerAt(this.getLevel(), this.worldPosition.relative(direction));
    }
    private static boolean isFull(IItemHandler itemHandler)
    {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++)
        {
            ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
            if (stackInSlot.isEmpty() || stackInSlot.getCount() < itemHandler.getSlotLimit(slot))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean insertHook(WopperEntity hopper)
    {
        Direction hopperFacing = hopper.getBlockState().getValue(Wopper.FACING);
        return getItemHandler(hopper, hopperFacing)
                .map(destinationResult -> {
                    IItemHandler itemHandler = destinationResult.getKey();
                    Object destination = destinationResult.getValue();
                    if (isFull(itemHandler))
                    {
                        return false;
                    }
                    else
                    {
                        for (int i = 0; i < hopper.getContainerSize(); ++i)
                        {
                            if (!hopper.getItem(i).isEmpty())
                            {
                                ItemStack originalSlotContents = hopper.getItem(i).copy();
                                ItemStack insertStack = hopper.removeItem(i, 1);
                                ItemStack remainder = putStackInInventoryAllSlots(hopper, destination, itemHandler, insertStack);

                                if (remainder.isEmpty())
                                {
                                    return true;
                                }

                                hopper.setItem(i, originalSlotContents);
                            }
                        }

                        return false;
                    }
                })
                .orElse(false);
    }
    private static boolean isEmpty(IItemHandler itemHandler)
    {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++)
        {
            ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
            if (stackInSlot.getCount() > 0)
            {
                return false;
            }
        }
        return true;
    }
    private static ItemStack insertStack(TileEntity source, Object destination, IItemHandler destInventory, ItemStack stack, int slot)
    {
        ItemStack itemstack = destInventory.getStackInSlot(slot);

        if (destInventory.insertItem(slot, stack, true).isEmpty())
        {
            boolean insertedItem = false;
            boolean inventoryWasEmpty = isEmpty(destInventory);

            if (itemstack.isEmpty())
            {
                destInventory.insertItem(slot, stack, false);
                stack = ItemStack.EMPTY;
                insertedItem = true;
            }
            else if (ItemHandlerHelper.canItemStacksStack(itemstack, stack))
            {
                int originalSize = stack.getCount();
                stack = destInventory.insertItem(slot, stack, false);
                insertedItem = originalSize < stack.getCount();
            }

            if (insertedItem)
            {
                if (inventoryWasEmpty && destination instanceof HopperTileEntity)
                {
                    HopperTileEntity destinationHopper = (HopperTileEntity)destination;

                    if (!destinationHopper.isOnCustomCooldown())
                    {
                        int k = 0;
                        if (source instanceof HopperTileEntity)
                        {
                            if (destinationHopper.getLastUpdateTime() >= ((HopperTileEntity) source).getLastUpdateTime())
                            {
                                k = 1;
                            }
                        }
                        destinationHopper.setCooldown(8 - k);
                    }
                }
            }
        }

        return stack;
    }
    private static ItemStack putStackInInventoryAllSlots(TileEntity source, Object destination, IItemHandler destInventory, ItemStack stack)
    {
        for (int slot = 0; slot < destInventory.getSlots() && !stack.isEmpty(); slot++)
        {
            stack = insertStack(source, destination, destInventory, stack, slot);
        }
        return stack;
    }
    @Nullable
    public static IInventory getSourceContainer(IHopper p_145884_0_) {
        return getContainerAt(p_145884_0_.getLevel(), p_145884_0_.getLevelX(), p_145884_0_.getLevelY() + 1.0D, p_145884_0_.getLevelZ());
    }
    private boolean ejectItems() {
        if (insertHook(this)) return true;
        IInventory iinventory = this.getAttachedContainer();
        if (iinventory == null) {
            return false;
        } else {
            Direction direction = this.getBlockState().getValue(Wopper.FACING).getOpposite();
            if (this.isFullContainer(iinventory, direction)) {
                return false;
            } else {
                for(int i = 0; i < this.getContainerSize(); ++i) {
                    if (!this.getItem(i).isEmpty()) {
                        ItemStack itemstack = this.getItem(i).copy();
                        ItemStack itemstack1 = addItem(this, iinventory, this.removeItem(i, 1), direction);
                        if (itemstack1.isEmpty()) {
                            iinventory.setChanged();
                            return true;
                        }

                        this.setItem(i, itemstack);
                    }
                }

                return false;
            }
        }
    }
    private boolean isFullContainer(IInventory p_174919_1_, Direction p_174919_2_) {
        return getSlots(p_174919_1_, p_174919_2_).allMatch((p_213970_1_) -> {
            ItemStack itemstack = p_174919_1_.getItem(p_213970_1_);
            return itemstack.getCount() >= itemstack.getMaxStackSize();
        });
    }
    private static IntStream getSlots(IInventory p_213972_0_, Direction p_213972_1_) {
        return p_213972_0_ instanceof ISidedInventory ? IntStream.of(((ISidedInventory)p_213972_0_).getSlotsForFace(p_213972_1_)) : IntStream.range(0, p_213972_0_.getContainerSize());
    }
    private boolean tryMoveItems(Supplier<Boolean> p_200109_1_) {
        if (this.level != null && !this.level.isClientSide) {
            if (!this.isOnCooldown() && this.getBlockState().getValue(Wopper.ENABLED)) {
                boolean flag = false;
                if (!this.isEmpty()) {
                    flag = this.ejectItems();
                }

                if (!this.inventoryFull()) {
                    flag |= p_200109_1_.get();
                }

                if (flag) {
                    this.setCooldown(10);
                    this.setChanged();
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
    private boolean inventoryFull() {
        for(ItemStack itemstack : this.items) {
            if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }
}