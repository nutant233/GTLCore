package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.client.renderer.cover.ConveyorCoverRenderer;
import com.gregtechceu.gtceu.client.renderer.cover.FluidRegulatorCoverRenderer;
import com.gregtechceu.gtceu.client.renderer.cover.PumpCoverRenderer;
import com.gregtechceu.gtceu.client.renderer.cover.RobotArmCoverRenderer;
import com.gregtechceu.gtceu.common.cover.ConveyorCover;
import com.gregtechceu.gtceu.common.cover.PumpCover;
import com.gregtechceu.gtceu.common.cover.RobotArmCover;
import com.gregtechceu.gtceu.common.data.GTCovers;

public class GTLCovers {
    public static final CoverDefinition ELECTRIC_PUMP_MAX = GTCovers.register(
            "pump.max",
            (def, coverable, side) -> new PumpCover(def, coverable, side, GTValues.MAX),
            PumpCoverRenderer.INSTANCE);

    public static final CoverDefinition FLUID_REGULATORS_ULV = GTCovers.register(
            "fluid_regulators.ulv",
            (def, coverable, side) -> new PumpCover(def, coverable, side, GTValues.ULV),
            FluidRegulatorCoverRenderer.INSTANCE);

    public static final CoverDefinition CONVEYOR_MODULE_MAX = GTCovers.register(
            "conveyor.max",
            (def, coverable, side) -> new ConveyorCover(def, coverable, side, GTValues.MAX),
            ConveyorCoverRenderer.INSTANCE);

    public static final CoverDefinition ROBOT_ARM_MAX = GTCovers.register(
            "robot_arm.max",
            (def, coverable, side) -> new RobotArmCover(def, coverable, side, GTValues.MAX),
            RobotArmCoverRenderer.INSTANCE);

    public static final CoverDefinition ROBOT_ARM_ULV = GTCovers.register(
            "robot_arm.ulv",
            (def, coverable, side) -> new RobotArmCover(def, coverable, side, GTValues.ULV),
            RobotArmCoverRenderer.INSTANCE);

    public static void init() {}
}
