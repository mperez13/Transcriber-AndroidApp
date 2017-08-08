package com.transcriber.com.transcriber;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final int ACTIVITY_RECORD_SOUND = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case R.id.record:
                Intent k = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(k, ACTIVITY_RECORD_SOUND);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
