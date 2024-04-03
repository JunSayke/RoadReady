package com.example.roadready.classes.general;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    public static File drawableToFile(Context context, int drawableId, String fileName) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        File file = new File(context.getCacheDir(), fileName);
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
