package com.mulganov.lib.mg2d;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import java.io.IOException;
import java.io.InputStream;

public class Assets {
    private static LruCache<String, Bitmap> memoryCache;
    private static AssetManager am;

    public static void init(Activity activity){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        am = activity.getAssets();

        try {
            for (String text: am.list("img")){
                System.out.println(text);
                addBitmapToMemoryCache(text, loadImageFromAsset("img/" + text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Size: " + memoryCache.size());
    }

    private static Bitmap loadImageFromAsset(String name) {
        try {
            InputStream ims = am.open(name);
            return BitmapFactory.decodeStream(ims);
        }
        catch(IOException ex) {
            return null;
        }
    }

    private static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmap(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmap(String key) {
        return memoryCache.get(key);
    }

    public static String List() {
        String str = "";

        for (String s: memoryCache.snapshot().keySet()){
            str += s + " " +  memoryCache.snapshot().get(s) + "\n";
        }

        return str;
    }
}
