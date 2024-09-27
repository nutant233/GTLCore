package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.common.item.*;
import org.gtlcore.gtlcore.integration.ae2.InfinityCell;
import org.gtlcore.gtlcore.utils.TextUtil;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.ElectricStats;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import appeng.api.stacks.AEKeyType;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.definitions.ItemDefinition;
import appeng.core.localization.GuiText;
import appeng.items.materials.MaterialItem;
import appeng.items.materials.StorageComponentItem;
import appeng.items.storage.BasicStorageCell;
import appeng.items.storage.StorageTier;
import appeng.items.tools.powered.PortableCellItem;
import appeng.menu.me.common.MEStorageMenu;
import com.hepdd.gtmthings.data.CreativeModeTabs;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import java.util.List;
import java.util.Locale;

import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.hepdd.gtmthings.common.registry.GTMTRegistration.GTMTHINGS_REGISTRATE;
import static org.gtlcore.gtlcore.api.registries.GTLRegistration.REGISTRATE;

public class GTLItems {

    public static void init() {}

    static {
        REGISTRATE.creativeModeTab(() -> GTLCreativeModeTabs.GTL_CORE);
    }

    private static ItemEntry<StorageComponentItem> registerStorageComponentItem(int tier) {
        return REGISTRATE
                .item("cell_component_" + tier + "m", p -> new StorageComponentItem(p, 1048576 * tier))
                .register();
    }

    private static ItemEntry<BasicStorageCell> registerStorageCell(int tier,
                                                                   ItemEntry<StorageComponentItem> StorageComponent,
                                                                   boolean isItem) {
        ItemDefinition<MaterialItem> CELL_HOUSING = isItem ? AEItems.ITEM_CELL_HOUSING : AEItems.FLUID_CELL_HOUSING;
        return REGISTRATE
                .item((isItem ? "item" : "fluid") + "_storage_cell_" + tier + "m", p -> new BasicStorageCell(
                        p.stacksTo(1),
                        StorageComponent,
                        CELL_HOUSING,
                        3 + 0.5 * Math.log(tier) / Math.log(4),
                        1024 * tier,
                        1,
                        isItem ? 63 : 18,
                        isItem ? AEKeyType.items() : AEKeyType.fluids()))
                .register();
    }

    public static final ItemEntry<StorageComponentItem> CELL_COMPONENT_1M = registerStorageComponentItem(1);
    public static final ItemEntry<StorageComponentItem> CELL_COMPONENT_4M = registerStorageComponentItem(4);
    public static final ItemEntry<StorageComponentItem> CELL_COMPONENT_16M = registerStorageComponentItem(16);
    public static final ItemEntry<StorageComponentItem> CELL_COMPONENT_64M = registerStorageComponentItem(64);
    public static final ItemEntry<StorageComponentItem> CELL_COMPONENT_256M = registerStorageComponentItem(256);

    public static final ItemEntry<BasicStorageCell> ITEM_CELL_1M = registerStorageCell(1, CELL_COMPONENT_1M, true);
    public static final ItemEntry<BasicStorageCell> ITEM_CELL_4M = registerStorageCell(4, CELL_COMPONENT_4M, true);
    public static final ItemEntry<BasicStorageCell> ITEM_CELL_16M = registerStorageCell(16, CELL_COMPONENT_16M, true);
    public static final ItemEntry<BasicStorageCell> ITEM_CELL_64M = registerStorageCell(64, CELL_COMPONENT_64M, true);
    public static final ItemEntry<BasicStorageCell> ITEM_CELL_256M = registerStorageCell(256, CELL_COMPONENT_256M,
            true);

    public static final ItemEntry<BasicStorageCell> FLUID_CELL_1M = registerStorageCell(1, CELL_COMPONENT_1M, false);
    public static final ItemEntry<BasicStorageCell> FLUID_CELL_4M = registerStorageCell(4, CELL_COMPONENT_4M, false);
    public static final ItemEntry<BasicStorageCell> FLUID_CELL_16M = registerStorageCell(16, CELL_COMPONENT_16M, false);
    public static final ItemEntry<BasicStorageCell> FLUID_CELL_64M = registerStorageCell(64, CELL_COMPONENT_64M, false);
    public static final ItemEntry<BasicStorageCell> FLUID_CELL_256M = registerStorageCell(256, CELL_COMPONENT_256M,
            false);

    public static final ItemEntry<PortableCellItem> SUPER_PORTABLE_ITEM_CELL = REGISTRATE
            .item("super_portable_item_storage_cell", p -> new PortableCellItem(AEKeyType.items(),
                    64,
                    MEStorageMenu.PORTABLE_ITEM_CELL_TYPE,
                    new StorageTier(100, "super", Integer.MAX_VALUE, 100, WETWARE_MAINFRAME_UHV),
                    p.stacksTo(1), 0xDDDDDD))
            .register();

    public static final ItemEntry<PortableCellItem> SUPER_PORTABLE_FLUID_CELL = REGISTRATE
            .item("super_portable_fluid_storage_cell", p -> new PortableCellItem(AEKeyType.fluids(),
                    64,
                    MEStorageMenu.PORTABLE_ITEM_CELL_TYPE,
                    new StorageTier(100, "super", Integer.MAX_VALUE, 100, WETWARE_MAINFRAME_UHV),
                    p.stacksTo(1), 0xFF6D36))
            .register();

    public static final ItemEntry<InfinityCell> ITEM_INFINITY_CELL = REGISTRATE.item("item_infinity_cell", p -> new InfinityCell(AEKeyType.items())).register();
    public static final ItemEntry<InfinityCell> FLUID_INFINITY_CELL = REGISTRATE.item("fluid_infinity_cell", p -> new InfinityCell(AEKeyType.fluids())).register();

    public static void InitUpgrades() {
        String storageCellGroup = GuiText.StorageCells.getTranslationKey();
        String portableCellGroup = GuiText.PortableCells.getTranslationKey();

        var itemCells = List.of(
                ITEM_CELL_1M, ITEM_CELL_4M, ITEM_CELL_16M, ITEM_CELL_64M,
                ITEM_CELL_256M);
        for (var itemCell : itemCells) {
            Upgrades.add(AEItems.FUZZY_CARD, itemCell, 1, storageCellGroup);
            Upgrades.add(AEItems.INVERTER_CARD, itemCell, 1, storageCellGroup);
            Upgrades.add(AEItems.EQUAL_DISTRIBUTION_CARD, itemCell, 1, storageCellGroup);
            Upgrades.add(AEItems.VOID_CARD, itemCell, 1, storageCellGroup);
        }

        var fluidCells = List.of(
                FLUID_CELL_1M, FLUID_CELL_4M, FLUID_CELL_16M, FLUID_CELL_64M,
                FLUID_CELL_256M);
        for (var fluidCell : fluidCells) {
            Upgrades.add(AEItems.INVERTER_CARD, fluidCell, 1, storageCellGroup);
            Upgrades.add(AEItems.EQUAL_DISTRIBUTION_CARD, fluidCell, 1, storageCellGroup);
            Upgrades.add(AEItems.VOID_CARD, fluidCell, 1, storageCellGroup);
        }

        Upgrades.add(AEItems.FUZZY_CARD, SUPER_PORTABLE_ITEM_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.INVERTER_CARD, SUPER_PORTABLE_ITEM_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.EQUAL_DISTRIBUTION_CARD, SUPER_PORTABLE_ITEM_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.VOID_CARD, SUPER_PORTABLE_ITEM_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.ENERGY_CARD, SUPER_PORTABLE_ITEM_CELL, 2, portableCellGroup);

        Upgrades.add(AEItems.INVERTER_CARD, SUPER_PORTABLE_FLUID_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.EQUAL_DISTRIBUTION_CARD, SUPER_PORTABLE_FLUID_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.VOID_CARD, SUPER_PORTABLE_FLUID_CELL, 1, portableCellGroup);
        Upgrades.add(AEItems.ENERGY_CARD, SUPER_PORTABLE_FLUID_CELL, 2, portableCellGroup);
    }

    public static ItemEntry<ComponentItem> REALLY_ULTIMATE_BATTERY = REGISTRATE
            .item("really_max_battery", ComponentItem::create)
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("§7填满就能通关GregTechCEu Modern")))))
            .onRegister(modelPredicate(GTCEu.id("battery"), ElectricStats::getStoredPredicate))
            .onRegister(attach(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UEV)))
            .register();
    public static ItemEntry<ComponentItem> TRANSCENDENT_ULTIMATE_BATTERY = REGISTRATE
            .item("transcendent_max_battery", ComponentItem::create)
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("§7填满就能通关GregTech Leisure")))))
            .onRegister(modelPredicate(GTCEu.id("battery"), ElectricStats::getStoredPredicate))
            .onRegister(attach(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UIV)))
            .register();
    public static ItemEntry<ComponentItem> EXTREMELY_ULTIMATE_BATTERY = REGISTRATE
            .item("extremely_max_battery", ComponentItem::create)
            .onRegister(
                    attach(new TooltipBehavior(lines -> lines.add(Component.literal("§7有生之年将它填满")))))
            .onRegister(modelPredicate(GTCEu.id("battery"), ElectricStats::getStoredPredicate))
            .onRegister(attach(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.UXV)))
            .register();
    public static ItemEntry<ComponentItem> INSANELY_ULTIMATE_BATTERY = REGISTRATE
            .item("insanely_max_battery", ComponentItem::create)
            .onRegister(
                    attach(new TooltipBehavior(
                            lines -> lines.add(Component.literal(TextUtil.dark_purplish_red("填满也就图一乐"))))))
            .onRegister(modelPredicate(GTCEu.id("battery"), ElectricStats::getStoredPredicate))
            .onRegister(attach(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.OpV)))
            .register();
    public static ItemEntry<ComponentItem> MEGA_ULTIMATE_BATTERY = REGISTRATE
            .item("mega_max_battery", ComponentItem::create)
            .onRegister(
                    attach(new TooltipBehavior(
                            lines -> lines.add(Component.literal(TextUtil.full_color("填满电池 机械飞升"))))))
            .onRegister(modelPredicate(GTCEu.id("battery"), ElectricStats::getStoredPredicate))
            .onRegister(attach(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, GTValues.MAX)))
            .register();

    public static ItemEntry<Item> ELECTRIC_MOTOR_MAX = REGISTRATE.item("max_electric_motor", Item::new).register();

    public static ItemEntry<ComponentItem> ELECTRIC_PUMP_MAX = REGISTRATE
            .item("max_electric_pump", ComponentItem::create)
            .onRegister(attach(new CoverPlaceBehavior(GTLCovers.ELECTRIC_PUMP_MAX)))
            .onRegister(attach(new TooltipBehavior(lines -> {
                lines.add(Component.translatable("item.gtceu.electric.pump.tooltip"));
                lines.add(Component.translatable("gtceu.universal.tooltip.fluid_transfer_rate",
                        1280 * 64 * 64 * 4 / 20));
            })))
            .register();

    public static ItemEntry<ComponentItem> CONVEYOR_MODULE_MAX = REGISTRATE
            .item("max_conveyor_module", ComponentItem::create)
            .onRegister(attach(new CoverPlaceBehavior(GTLCovers.CONVEYOR_MODULE_MAX)))
            .onRegister(attach(new TooltipBehavior(lines -> {
                lines.add(Component.translatable("item.gtceu.conveyor.module.tooltip"));
                lines.add(Component.translatable("gtceu.universal.tooltip.item_transfer_rate_stacks", 16));
            })))
            .register();

    public static ItemEntry<ComponentItem> ROBOT_ARM_MAX = REGISTRATE.item("max_robot_arm", ComponentItem::create)
            .onRegister(attach(new CoverPlaceBehavior(GTLCovers.ROBOT_ARM_MAX)))
            .onRegister(attach(new TooltipBehavior(lines -> {
                lines.add(Component.translatable("item.gtceu.robot.arm.tooltip"));
                lines.add(Component.translatable("gtceu.universal.tooltip.item_transfer_rate_stacks", 16));
            })))
            .register();

    public static ItemEntry<Item> ELECTRIC_PISTON_MAX = register("max_electric_piston", true);
    public static ItemEntry<Item> FIELD_GENERATOR_MAX = register("max_field_generator", true);
    public static ItemEntry<Item> EMITTER_MAX = register("max_emitter", true);
    public static ItemEntry<Item> SENSOR_MAX = register("max_sensor", true);

    public static ItemEntry<ComponentItem> PRIMITIVE_ROBOT_ARM = REGISTRATE
            .item("primitive_robot_arm", ComponentItem::create)
            .onRegister(attach(new CoverPlaceBehavior(GTLCovers.ROBOT_ARM_ULV)))
            .onRegister(attach(new TooltipBehavior(lines -> {
                lines.add(Component.translatable("item.gtceu.robot.arm.tooltip"));
                lines.add(Component.translatable("gtceu.universal.tooltip.item_transfer_rate_stacks", 33554431));
            })))
            .register();

    public static ItemEntry<ComponentItem> PRIMITIVE_FLUID_REGULATOR = REGISTRATE
            .item("primitive_fluid_regulator", ComponentItem::create)
            .onRegister(attach(new CoverPlaceBehavior(GTLCovers.FLUID_REGULATORS_ULV)))
            .onRegister(attach(new TooltipBehavior(lines -> {
                lines.add(Component.translatable("item.gtceu.fluid.regulator.tooltip"));
                lines.add(Component.translatable("gtceu.universal.tooltip.fluid_transfer_rate", Integer.MAX_VALUE));
            })))
            .register();

    private static ItemEntry<ComponentItem> registerTieredCover(int amperage) {
        ItemEntry<ComponentItem> cover = GTMTHINGS_REGISTRATE
                .item(GTValues.VN[GTValues.MAX].toLowerCase(Locale.ROOT) + "_" +
                        (amperage == 1 ? "" : amperage + "a_") + "wireless_energy_receive_cover", ComponentItem::create)
                .onRegister(attach(new TooltipBehavior(lines -> {
                    lines.add(Component.translatable("item.gtmthings.wireless_energy_receive_cover.tooltip.1"));
                    lines.add(Component.translatable("item.gtmthings.wireless_energy_receive_cover.tooltip.2"));
                    lines.add(Component.translatable("item.gtmthings.wireless_energy_receive_cover.tooltip.3",
                            GTValues.V[GTValues.MAX] * amperage));
                }), new CoverPlaceBehavior(amperage == 1 ? GTLCovers.MAX_WIRELESS_ENERGY_RECEIVE :
                        GTLCovers.MAX_WIRELESS_ENERGY_RECEIVE_4A)))
                .register();
        GTMTHINGS_REGISTRATE.setCreativeTab(cover, CreativeModeTabs.WIRELESS_TAB);
        return cover;
    }

    public static ItemEntry<ComponentItem> WIRELESS_ENERGY_RECEIVE_COVER_MAX = registerTieredCover(1);

    public static ItemEntry<ComponentItem> WIRELESS_ENERGY_RECEIVE_COVER_MAX_4A = registerTieredCover(4);

    public static final ItemEntry<ComponentItem> DEBUG_STRUCTURE_WRITER = REGISTRATE
            .item("debug_structure_writer", ComponentItem::create)
            .onRegister(GTItems.attach(StructureWriteBehavior.INSTANCE))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> DEBUG_PATTERN_TEST = REGISTRATE
            .item("debug_pattern_test", ComponentItem::create)
            .onRegister(GTItems.attach(PatternTestBehavior.INSTANCE))
            .model(NonNullBiConsumer.noop())
            .register();

    public static final ItemEntry<ComponentItem> PATTERN_MODIFIER = REGISTRATE
            .item("pattern_modifier", ComponentItem::create)
            .onRegister(GTItems.attach(PatternModifier.INSTANCE))
            .model(NonNullBiConsumer.noop())
            .register();

    public static ItemEntry<ComponentItem> CFG_COPY = REGISTRATE
            .item("cfg_copy", ComponentItem::create)
            .onRegister(attach(ConfigurationCopyBehavior.INSTANCE))
            .model(NonNullBiConsumer.noop())
            .register();

    private static ItemEntry<Item> register(String id, boolean defaultModel) {
        return defaultModel ? REGISTRATE.item(id, Item::new).register() : REGISTRATE.item(id, Item::new).model(NonNullBiConsumer.noop()).register();
    }

    public static ItemEntry<Item> INFINITE_CELL_COMPONENT = register("infinite_cell_component", true);
    public static ItemEntry<Item> PROTONATED_FULLERENE_SIEVING_MATRIX = register("protonated_fullerene_sieving_matrix", true);
    public static ItemEntry<Item> SATURATED_FULLERENE_SIEVING_MATRIX = register("saturated_fullerene_sieving_matrix", true);
    public static ItemEntry<Item> MICROFOCUS_X_RAY_TUBE = register("microfocus_x_ray_tube", true);
    public static ItemEntry<Item> SEPARATION_ELECTROMAGNET = register("separation_electromagnet", true);
    public static ItemEntry<Item> HIGHLY_INSULATING_FOIL = register("highly_insulating_foil", false);
    public static ItemEntry<Item> STERILIZED_PETRI_DISH = register("sterilized_petri_dish", false);
    public static ItemEntry<Item> ELECTRICALY_WIRED_PETRI_DISH = register("electricaly_wired_petri_dish", false);
    public static ItemEntry<Item> CONTAMINATED_PETRI_DISH = register("contaminated_petri_dish", true);
    public static ItemEntry<Item> BREVIBACTERIUM_PETRI_DISH = register("brevibacterium_petri_dish", false);
    public static ItemEntry<Item> BIFIDOBACTERIUMM_PETRI_DISH = register("bifidobacteriumm_petri_dish", false);
    public static ItemEntry<Item> ESCHERICIA_PETRI_DISH = register("eschericia_petri_dish", false);
    public static ItemEntry<Item> STREPTOCOCCUS_PETRI_DISH = register("streptococcus_petri_dish", false);
    public static ItemEntry<Item> CUPRIAVIDUS_PETRI_DISH = register("cupriavidus_petri_dish", false);
    public static ItemEntry<Item> SHEWANELLA_PETRI_DISH = register("shewanella_petri_dish", false);
}
