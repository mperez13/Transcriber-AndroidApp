package com.transcriber.com.transcriber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mounika on 8/8/2017.
 */


public class Filesinterface extends Activity {

    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ProjectCS5540/Transcriber-AndroidApp";
    final static String TAG = Filesinterface.class.getName();

    //    List<String> fileName = new ArrayList<String>();
// populate above list from your desired path
//    Spinner spinner = (Spinner) findViewById(R.id.spinner);
//    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, filenames);
//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//spinner.setAdapter(adapter);
    public static String ReadFile(Context context, String path) {
        String textentered = null;

        try {
            FileInputStream fileinput = new FileInputStream(new File(path));
            InputStreamReader inputread = new InputStreamReader(fileinput);
            BufferedReader readingbuffer = new BufferedReader(inputread);
            StringBuilder builderstring = new StringBuilder();

            while ((textentered = readingbuffer.readLine()) != null) {
                builderstring.append(textentered );
            }
            fileinput.close();
            textentered = builderstring.toString();

            readingbuffer.close();
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return textentered;
    }

    public static boolean saveToFile(String data) {
        try {
            Calendar c = Calendar.getInstance();
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ProjectCS5540/Transcriber-AndroidApp/");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file_name = new File(dir, System.currentTimeMillis() + ".txt");
            if (!file_name.exists()) {
                file_name.createNewFile();
            }
            FileOutputStream opstream = new FileOutputStream(file_name, true);
            opstream.write(data.getBytes());
//            FileOutputStream opstream = new FileOutputStream(file_name,true);
//            opstream.write((data + System.getProperty("textentered.separator")).getBytes());

            return true;
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return false;


    }
//    public void list(String c)
//    {
//
//        Log.d("Files", "Path: " + path);
//        File directory = new File(path);
//        File[] files = directory.listFiles();
//        Log.d("Files", "Size: "+ files.length);
//        for (int i = 0; i < files.length; i++)
//        {
//            Log.d("Files", "FileName:" + files[i].getName());
//        }
//    }
//    public void openFolder()
//    {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/D");
//        intent.setDataAndType(uri, "text");
//        startActivity(Intent.createChooser(intent, "Open folder"));
//    }
}
