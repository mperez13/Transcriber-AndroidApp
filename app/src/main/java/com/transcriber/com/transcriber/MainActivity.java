package com.transcriber.com.transcriber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
<<<<<<< HEAD
        getMenuInflater().inflate(R.menu.menu, menu);
=======
        getMenuInflater().inflate(R.menu.main, menu);
>>>>>>> 956bb2a8b1475005994e39504dd4ffdf4e2cf7bf
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
