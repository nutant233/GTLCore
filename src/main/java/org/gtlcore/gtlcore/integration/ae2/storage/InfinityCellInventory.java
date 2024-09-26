package org.gtlcore.gtlcore.integration.ae2.storage;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.integration.ae2.InfinityCell;
import org.gtlcore.gtlcore.utils.StorageManager;

import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import appeng.core.AELog;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

public class InfinityCellInventory implements StorageCell {

    private final ISaveProvider container;
    private final AEKeyType keyType;
    @Getter
    private long storedItemCount;
    private Object2LongMap<AEKey> storedAmounts;
    private final ItemStack stack;
    private boolean isPersisted = true;

    public InfinityCellInventory(AEKeyType keyType, ItemStack stack, ISaveProvider saveProvider) {
        this.stack = stack;
        this.container = saveProvider;
        this.keyType = keyType;
        this.storedAmounts = null;
        initData();
    }

    private InfinityCellDataStorage getDiskStorage() {
        if (getDiskUUID() != null)
            return getStorageInstance().getOrCreateDisk(getDiskUUID());
        else
            return InfinityCellDataStorage.EMPTY;
    }

    private void initData() {
        if (hasDiskUUID()) {
            this.storedItemCount = getDiskStorage().itemCount;
        } else {
            this.storedItemCount = 0;
            getCellItems();
        }
    }

    @Override
    public CellState getStatus() {
        if (this.getStoredItemCount() == 0) {
            return CellState.EMPTY;
        }
        if (this.getFreeBytes() > 0) {
            return CellState.NOT_EMPTY;
        }
        return CellState.FULL;
    }

    @Override
    public double getIdleDrain() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void persist() {
        if (this.isPersisted) {
            return;
        }

        if (storedItemCount == 0) {
            if (hasDiskUUID()) {
                getStorageInstance().removeDisk(getDiskUUID());
                if (stack.getTag() != null) {
                    stack.getTag().remove("diskuuid");
                    stack.getTag().remove("count");
                }
                initData();
            }
            return;
        }

        long itemCount = 0;

        LongArrayList amounts = new LongArrayList(storedAmounts.size());
        ListTag keys = new ListTag();

        for (Object2LongMap.Entry<AEKey> entry : this.storedAmounts.object2LongEntrySet()) {
            long amount = entry.getLongValue();

            if (amount > 0) {
                itemCount += amount;
                keys.add(entry.getKey().toTagGeneric());
                amounts.add(amount);
            }
        }

        if (keys.isEmpty()) {
            getStorageInstance().updateDisk(getDiskUUID(), new InfinityCellDataStorage());
        } else {
            getStorageInstance().modifyDisk(getDiskUUID(), keys, amounts.toArray(new long[0]), itemCount);
        }

        this.storedItemCount = itemCount;
        stack.getOrCreateTag().putLong("count", itemCount);

        this.isPersisted = true;
    }

    @Override
    public Component getDescription() {
        return null;
    }

    public static InfinityCellInventory createInventory(ItemStack stack, ISaveProvider saveProvider) {
        Objects.requireNonNull(stack, "Cannot create cell inventory for null itemstack");

        if (!(stack.getItem() instanceof InfinityCell cellType)) {
            return null;
        }

        return new InfinityCellInventory(cellType.getKeyType(), stack, saveProvider);
    }

    public boolean hasDiskUUID() {
        return stack.hasTag() && stack.getOrCreateTag().contains("diskuuid");
    }

    public static boolean hasDiskUUID(ItemStack disk) {
        if (disk.getItem() instanceof InfinityCell) {
            return disk.hasTag() && disk.getOrCreateTag().contains("diskuuid");
        }
        return false;
    }

    public UUID getDiskUUID() {
        if (hasDiskUUID())
            return stack.getOrCreateTag().getUUID("diskuuid");
        else
            return null;
    }

    private boolean isStorageCell(AEItemKey key) {
        InfinityCell type = getStorageCell(key);
        return type != null;
    }

    private static InfinityCell getStorageCell(AEItemKey itemKey) {
        if (itemKey.getItem() instanceof InfinityCell infinityCell) {
            return infinityCell;
        }

        return null;
    }

    private static boolean isCellEmpty(InfinityCellInventory inv) {
        if (inv != null) {
            return inv.getAvailableStacks().isEmpty();
        }
        return true;
    }

    protected Object2LongMap<AEKey> getCellItems() {
        if (this.storedAmounts == null) {
            this.storedAmounts = new Object2LongOpenHashMap<>();
            this.loadCellItems();
        }

        return this.storedAmounts;
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        for (Object2LongMap.Entry<AEKey> entry : this.getCellItems().object2LongEntrySet()) {
            out.add(entry.getKey(), entry.getLongValue());
        }
    }

    private void loadCellItems() {
        boolean corruptedTag = false;

        if (!stack.hasTag()) {
            return;
        }

        long[] amounts = getDiskStorage().stackAmounts;
        ListTag tags = getDiskStorage().stackKeys;
        if (amounts.length != tags.size()) {
            AELog.warn("Loading storage cell with mismatched amounts/tags: %d != %d",
                    amounts.length, tags.size());
        }

        for (int i = 0; i < amounts.length; i++) {
            long amount = amounts[i];
            AEKey key = AEKey.fromTagGeneric(tags.getCompound(i));

            if (amount <= 0 || key == null) {
                corruptedTag = true;
            } else {
                storedAmounts.put(key, amount);
            }
        }

        if (corruptedTag) {
            this.saveChanges();
        }
    }

    private StorageManager getStorageInstance() {
        return GTLCore.STORAGE_INSTANCE;
    }

    protected void saveChanges() {
        this.storedItemCount = 0;
        for (long storedAmount : this.storedAmounts.values()) {
            this.storedItemCount += storedAmount;
        }

        this.isPersisted = false;
        if (this.container != null) {
            this.container.saveChanges();
        } else {
            this.persist();
        }
    }

    public long getRemainingItemCount() {
        return this.getFreeBytes() > 0 ? this.getFreeBytes() : 0;
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (amount == 0 || !keyType.contains(what)) {
            return 0;
        }

        if (what instanceof AEItemKey itemKey && this.isStorageCell(itemKey)) {
            InfinityCellInventory meInventory = createInventory(itemKey.toStack(), null);
            if (!isCellEmpty(meInventory)) {
                return 0;
            }
        }

        if (!hasDiskUUID()) {
            stack.getOrCreateTag().putUUID("diskuuid", UUID.randomUUID());
            getStorageInstance().getOrCreateDisk(getDiskUUID());
            loadCellItems();
        }

        long currentAmount = this.getCellItems().getLong(what);
        long remainingItemCount = getRemainingItemCount();

        if (amount > remainingItemCount) {
            amount = remainingItemCount;
        }

        if (mode == Actionable.MODULATE) {
            getCellItems().put(what, currentAmount + amount);
            this.saveChanges();
        }

        return amount;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        long extractAmount = Math.min(Integer.MAX_VALUE, amount);
        long currentAmount = getCellItems().getLong(what);
        if (currentAmount > 0) {
            if (extractAmount >= currentAmount) {
                if (mode == Actionable.MODULATE) {
                    getCellItems().remove(what, currentAmount);
                    this.saveChanges();
                }

                return currentAmount;
            } else {
                if (mode == Actionable.MODULATE) {
                    getCellItems().put(what, currentAmount - extractAmount);
                    this.saveChanges();
                }

                return extractAmount;
            }
        }

        return 0;
    }

    public long getFreeBytes() {
        return Long.MAX_VALUE - this.getStoredItemCount();
    }

    public long getNbtItemCount() {
        if (hasDiskUUID()) {
            if (stack.getTag() != null) {
                return stack.getTag().getLong("count");
            }
        }
        return 0;
    }
}
