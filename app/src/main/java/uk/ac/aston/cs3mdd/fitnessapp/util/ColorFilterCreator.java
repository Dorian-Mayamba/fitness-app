package uk.ac.aston.cs3mdd.fitnessapp.util;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class ColorFilterCreator {

    public static ColorFilter createColorFilter(float brightness){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                brightness, 0,0,0,0, // Red component
                0,brightness,0,0,0, // Green component
                0,0,brightness,0,0, // Blue component
                0,0,0,1,0 // Alpha component
        });
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        return colorFilter;
    }
}
