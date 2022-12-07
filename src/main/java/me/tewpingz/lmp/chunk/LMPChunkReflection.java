package me.tewpingz.lmp.chunk;

import net.minecraft.world.level.material.MaterialMapColor;

public class LMPChunkReflection {

    private static MaterialMapColor.a[] enumValues;

    static {
        for (Class<?> declaredClass : MaterialMapColor.class.getDeclaredClasses()) {
            if (declaredClass.isEnum()) {
                enumValues = (MaterialMapColor.a[]) declaredClass.getEnumConstants();
            }
        }
    }

    public static MaterialMapColor.a getEnumValue(int index) {
        return enumValues[index];
    }
}
