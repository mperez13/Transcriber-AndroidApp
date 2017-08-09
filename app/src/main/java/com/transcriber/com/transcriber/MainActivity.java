package com.transcriber.com.transcriber;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.Context;

import java.io.File;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mainActivity";

    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.main_view);

        checkPermissionGranted(view);
        createFolder();
        Toast.makeText(MainActivity.this, getApplicationContext().getApplicationInfo().dataDir, Toast.LENGTH_LONG).show();

    }

    /**
     * Creates folder to keep text files
     */
    public static void createFolder() {

        /* Folder for the audio*/
            /* getExternalStorageDirectory Returns the primary shared/external storage directory*/
        File folderAudio = new File(Environment.getExternalStorageDirectory() + File.separator + "Audio");
        //Folder for the text
        File folderText = new File(Environment.getExternalStorageDirectory() + File.separator + "Text");

        //if folders do not exist create them
        if (!(folderAudio.exists())) {
                /*mkdirs() - creates dir named by path name; includes parent directories*/
            folderAudio.mkdirs();
        }
        if (!(folderText.exists())) {
            folderAudio.mkdirs();
        }
    }
    /*check if required permission are granted*/
    private void checkPermissionGranted(View view){
        int audio_permission = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        int storage_permission = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        boolean permissions = audio_permission == PackageManager.PERMISSION_GRANTED && storage_permission == PackageManager.PERMISSION_GRANTED;

        if(!permissions){
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, 200);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stt:
                Intent i = new Intent(getApplicationContext(), SpeechToText.class);
                startActivity(i);
                return true;
            case R.id.tts:
                Intent j = new Intent(getApplicationContext(), TextToSpeech.class);
                startActivity(j);
                return true;
            case R.id.main:
                Intent k = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
