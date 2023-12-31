package uk.ac.aston.cs3mdd.fitnessapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class BitmapGenerator {
    public static Bitmap getImageFormResponseBody(ResponseBody responseBody) {
        return BitmapFactory.decodeStream(responseBody.byteStream());
    }
}
