package org.gtlcore.gtlcore.client.gui;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author EasterFG on 2024/9/13
 */
@Data
@AllArgsConstructor
public class ModifyData {
    private ModifyType type;
    private int amount;
}
