package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import org.gtlcore.gtlcore.common.data.GTLItems;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CraftingComponentAddition {

    public static void init() {
        CraftingComponent.PUMP.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ELECTRIC_PUMP_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.CONVEYOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.CONVEYOR_MODULE_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.MOTOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ELECTRIC_MOTOR_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.PISTON.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ELECTRIC_PISTON_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.EMITTER.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.EMITTER_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.SENSOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.SENSOR_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.FIELD_GENERATOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.FIELD_GENERATOR_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.ROBOT_ARM.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ROBOT_ARM_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
    }
}
