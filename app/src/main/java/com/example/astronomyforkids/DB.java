package com.example.astronomyforkids;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageSwitcher;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DB {
    public static String URLStorage = "gs://astronomydb-2b25f.appspot.com";
    private ArrayList<String> topicList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();

    /**
     * Повертає колекцію з БД
     * @param valueMap
     * @param child
     * @return
     */
    public ArrayList<String> getCollectFromDB(Map<String, Object> valueMap, String child) {
        HashMap<Long, String> collect = new HashMap<>();
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            Map valueDBMap = (Map) entry.getValue();
            collect.put((long) valueDBMap.get("id"), String.valueOf(valueDBMap.get(child)));
        }
        Map<Long, String> map = new TreeMap<>(collect);
        return new ArrayList<>(map.values());
    }

    /**
     * Форматує текст в html для завантаження в webView
     * @param string
     * @return
     */
    public String toHtmlFormat(String string){
        return "<html><head>"
                + "<style type=\"text/css\">body{color: #f5f5f5; background-color: #031F3C; text-align: justify;}"
                + "</style></head>"
                + "<body>"
                + string
                + "</body></html>";
    }

    /**
     * Задає зображення для галереї із сховище
     * @param storageReference
     * @param child
     * @param i
     * @param imageSwitcher
     */
    public void getImageUriList(StorageReference storageReference, String child, int i, final ImageSwitcher imageSwitcher) {
        try {
            final File localFile = File.createTempFile("images", "jpg");
            System.out.println("path: " + child + "/" + i + ".jpg");

            storageReference.child(child).child(++i + ".jpg").getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            imageSwitcher.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeFile(localFile.getAbsolutePath())));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {}
            });
        } catch (IOException e) {
            System.out.println("e: " + e);
        }
    }

    /**
     * Змінює позицію вперед для галереї зображень
     * @param position
     * @return
     */
    public int setPositionNext(int position) {
        position++;
        if (position > 3) {
            position = 0;
        }
        return position;
    }

    /**
     * Змінює позицію назад для галереї зображень
     * @param position
     * @return
     */
    public int setPositionBack(int position) {
        position--;
        if (position < 0) {
            position = 3;
        }
        return position;
    }
}
