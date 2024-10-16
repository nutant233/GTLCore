package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.common.data.GTLMachines;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;

import net.minecraft.network.chat.Component;

public class MachineModify {

    public static void init() {
        MachineDefinition hpca_computation_component = GTResearchMachines.HPCA_COMPUTATION_COMPONENT;
        hpca_computation_component.setTooltipBuilder(hpca_computation_component.getTooltipBuilder().andThen((itemStack, components) -> components.set(3, Component.translatable("gtceu.machine.hpca.component_type.computation_cwut", 8))));
        hpca_computation_component.setTooltipBuilder(hpca_computation_component.getTooltipBuilder().andThen(GTLMachines.GTL_MODIFY));

        MachineDefinition hpca_advanced_computation_component = GTResearchMachines.HPCA_ADVANCED_COMPUTATION_COMPONENT;
        hpca_advanced_computation_component.setTooltipBuilder(hpca_advanced_computation_component.getTooltipBuilder().andThen((itemStack, components) -> components.set(3, Component.translatable("gtceu.machine.hpca.component_type.computation_cwut", 32))));
        hpca_advanced_computation_component.setTooltipBuilder(hpca_advanced_computation_component.getTooltipBuilder().andThen(GTLMachines.GTL_MODIFY));

        MachineDefinition steamExportBus = GTMachines.STEAM_EXPORT_BUS;
        steamExportBus.setTooltipBuilder(steamExportBus.getTooltipBuilder().andThen(GTLMachines.GTL_MODIFY));
        MachineDefinition steamImportBus = GTMachines.STEAM_IMPORT_BUS;
        steamImportBus.setTooltipBuilder(steamImportBus.getTooltipBuilder().andThen(GTLMachines.GTL_MODIFY));

        MachineDefinition steamOven = GTMachines.STEAM_OVEN;
        steamOven.setTooltipBuilder(steamOven.getTooltipBuilder().andThen(GTLMachines.GTL_MODIFY));
    }
}
