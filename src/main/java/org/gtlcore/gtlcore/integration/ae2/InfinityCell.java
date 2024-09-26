package org.gtlcore.gtlcore.integration.ae2;

import org.gtlcore.gtlcore.integration.ae2.storage.InfinityCellHandler;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.AEKeyType;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.UpgradeInventories;
import appeng.hooks.AEToolItem;
import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class InfinityCell extends Item implements ICellWorkbenchItem, AEToolItem {

    private final AEKeyType keyType;

    public InfinityCell(AEKeyType keyType) {
        super(new Properties().stacksTo(1).fireResistant());
        this.keyType = keyType;
    }

    @Override
    public boolean isEditable(ItemStack is) {
        return true;
    }

    @Override
    public FuzzyMode getFuzzyMode(final ItemStack is) {
        final String fz = is.getOrCreateTag().getString("FuzzyMode");
        if (fz.isEmpty()) {
            return FuzzyMode.IGNORE_ALL;
        }
        try {
            return FuzzyMode.valueOf(fz);
        } catch (final Throwable t) {
            return FuzzyMode.IGNORE_ALL;
        }
    }

    @Override
    public void setFuzzyMode(final ItemStack is, final FuzzyMode fzMode) {
        is.getOrCreateTag().putString("FuzzyMode", fzMode.name());
    }

    @Override
    public IUpgradeInventory getUpgrades(ItemStack is) {
        return UpgradeInventories.forItem(is, 2);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        Preconditions.checkArgument(stack.getItem() == this);
        InfinityCellHandler.INSTANCE.addCellInformationToTooltip(stack, tooltip);
    }
}
