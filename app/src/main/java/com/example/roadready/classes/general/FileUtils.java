package com.example.roadready.classes.general;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

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

    public static File uriToFile(Context context, Uri uri) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);

            if (inputStream != null) {
                File tempFile = File.createTempFile("image_", ".png");

                inputStream.close();

                return tempFile;
            } else {
                Log.e("FileUtils", "Input stream is null");
            }
        } catch (IOException e) {
            Log.e("FileUtils", "Error reading file from Uri: " + e.getMessage());
        }

        return null;
    }

}
