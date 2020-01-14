package com.example.productivity.stuff;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.productivity.Training.Training;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SaveData {

    private Context context;
    private final String key = "com.example.productivity.SavaData";

    public SaveData(Context context) {
        this.context = context;
    }

    public Object read(String readKey) {
        Object result = null;
        try {
            FileInputStream fis = context.openFileInput(readKey);
            ObjectInputStream is = new ObjectInputStream(fis);
            result = is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result;
    }

    public boolean write(String writeKey, Object data) {
        try {
            FileOutputStream fos = context.openFileOutput(writeKey, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
