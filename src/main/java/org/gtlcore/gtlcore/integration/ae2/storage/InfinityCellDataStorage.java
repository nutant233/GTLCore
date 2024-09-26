package org.gtlcore.gtlcore.integration.ae2.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class InfinityCellDataStorage {

    public static final InfinityCellDataStorage EMPTY = new InfinityCellDataStorage();

    public ListTag stackKeys;
    public long[] stackAmounts;
    public long itemCount;

    public InfinityCellDataStorage() {
        stackKeys = new ListTag();
        stackAmounts = new long[0];
        itemCount = 0;
    }

    public InfinityCellDataStorage(ListTag stackKeys, long[] stackAmounts, long itemCount) {
        this.stackKeys = stackKeys;
        this.stackAmounts = stackAmounts;
        this.itemCount = itemCount;
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("keys", stackKeys);
        nbt.putLongArray("amounts", stackAmounts);
        if (itemCount != 0) {
            nbt.putLong("count", itemCount);
        }
        return nbt;
    }

    public static InfinityCellDataStorage fromNbt(CompoundTag nbt) {
        long itemCount = 0;
        ListTag stackKeys = nbt.getList("keys", Tag.TAG_COMPOUND);
        long[] stackAmounts = nbt.getLongArray("amounts");
        if (nbt.contains("count")) {
            itemCount = nbt.getLong("count");
        }
        return new InfinityCellDataStorage(stackKeys, stackAmounts, itemCount);
    }
}
