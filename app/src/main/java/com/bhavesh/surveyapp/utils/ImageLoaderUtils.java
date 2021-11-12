package com.bhavesh.surveyapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageLoaderUtils {
    public static void load(@NotNull String url, @NotNull ImageView view) {
        Picasso.get().load(url).into(view);
    }

    public static void load(@NotNull int res, @NotNull ImageView view) {
        Picasso.get().load(res).into(view);
    }

    public static void load(@NotNull String url, @NotNull ImageView view, int placeHolder) {
        Picasso.get().load(url).placeholder(placeHolder).into(view);
    }

    public static void load(@NotNull String url, @NotNull ImageView view, @NotNull Transformation transformation) {
        Picasso.get().load(url).transform(transformation).into(view);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap resizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return "data:image/png;base64," + imgString;
    }

    public static long getImageSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }

    public static Bitmap decodeImageFromFiles(String path, int width, int height) {
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, scaleOptions);
        int scale = 1;
        while (scaleOptions.outWidth / scale / 2 >= width
                && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2;
        }
        // decode with the sample size
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        return BitmapFactory.decodeFile(path, outOptions);
    }

    /**
     * compress the file/photo from @param <b>path</b> to a private location on the current device and return the compressed file.
     *
     * @param path    = The original image path
     * @param context = Current android Context
     */
    public static File getCompressed(Context context, String path) throws IOException {
        if (context == null)
            throw new NullPointerException();
        //getting device external cache directory, might not be available on some devices,
        // so our code fall back to internal storage cache directory, which is always available but in smaller quantity
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null)
            //fall back
            cacheDir = context.getCacheDir();
        String rootDir = cacheDir.getAbsolutePath() + "/ImageCompressor";
        File root = new File(rootDir);
        //Create ImageCompressor folder if it doesnt already exists.
        if (!root.exists())
            root.mkdirs();
        //decode and resize the original bitmap from @param path.
        Bitmap bitmap = decodeImageFromFiles(path, /* your desired width*/250, /*your desired height*/ 250);
        //create placeholder for the compressed image file
        File compressed = new File(root, System.currentTimeMillis() + ".jpg" /*Your desired format*/);
        //convert the decoded bitmap to stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /*compress bitmap into byteArrayOutputStream
        Bitmap.compress(Format, Quality, OutputStream)
        Where Quality ranges from 1–100.
        */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        /*
        Right now, we have our bitmap inside byteArrayOutputStream Object, all we need next is to write it to the compressed file we created earlier,
        java.io.FileOutputStream can help us do just That!
        */
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(compressed);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //File written, return to the caller. Done!
        return compressed;
    }
}
