package com.transcriber.com.transcriber;

import android.content.Intent;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;
public class TextToSpeech extends AppCompatActivity {
    Button readbutton;
    EditText inputtext;
    TextView content;
    Button savebutton;
    Button speak;
    final static String path = Environment.getExternalStorageDirectory().toString()+"/E://ProjectCS5540//Transcriber-AndroidApp//app//src//main//res";
    android.speech.tts.TextToSpeech texttoaudioconversion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        content = (TextView) findViewById(R.id.content);
        inputtext = (EditText) findViewById(R.id.inputtext);
        readbutton = (Button) findViewById(R.id.readbutton);
        savebutton = (Button) findViewById(R.id.savebutton);
        speak = (Button) findViewById(R.id.button);
        readbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                content.setText(Filesinterface.ReadFile(TextToSpeech.this));
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Filesinterface.saveToFile(inputtext.getText().toString()))
                {
                    Toast.makeText(TextToSpeech.this, "File saved", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(TextToSpeech.this, "File not saved error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        texttoaudioconversion = new android.speech.tts.TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int checkingstatus)
            {
                if (checkingstatus != android.speech.tts.TextToSpeech.ERROR) {
                    texttoaudioconversion.setLanguage(Locale.US);
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String speaking = inputtext.getText().toString();
                Toast.makeText(getApplicationContext(), speaking, Toast.LENGTH_SHORT).show();
                texttoaudioconversion.speak(speaking, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    public void onPause() {
        if (texttoaudioconversion == null) {
            texttoaudioconversion.stop();
            texttoaudioconversion.shutdown();

        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Redirects to activity selected.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stt:
                Intent i = new Intent(getApplicationContext(), SpeechToText.class);
                startActivity(i);
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

