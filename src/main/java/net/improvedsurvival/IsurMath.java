package net.improvedsurvival;

import net.minecraft.util.math.MathHelper;

public class IsurMath {
    public static float multiLerp(float delta, float... params){
        if(params.length == 0)
            return 0;

        if(delta <= 0)
            return params[0];

        if(delta >= params.length-1)
            return params[params.length - 1];

        int index = (int)delta;
        delta -= index;

        return MathHelper.lerp(delta, params[index], params[index+1]);
    }
}