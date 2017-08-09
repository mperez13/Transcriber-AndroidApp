package com.transcriber.com.transcriber;


import android.content.Context;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mounika on 8/8/2017.
 */
public class TextToSpeech extends AppCompatActivity {
    //Button readbutton;
    EditText inputtext;
    TextView content;
    Button savebutton;
    Button speak;
    Button loadbutton;
    RecyclerView recyclerView;
    private LinearLayoutManager llm;
    ListAdapter listAdapter;
    Context context;
    final static String path = Environment.getExternalStorageDirectory().toString() + "/ProjectCS5540/Transcriber-AndroidApp/";
    android.speech.tts.TextToSpeech texttoaudioconversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        context = this;
        content = (TextView) findViewById(R.id.content);
        inputtext = (EditText) findViewById(R.id.inputtext);
        //readbutton = (Button) findViewById(R.id.readbutton);
        savebutton = (Button) findViewById(R.id.savebutton);
        loadbutton = (Button) findViewById(R.id.listbutton);
        speak = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.rv_listOfFiles);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /*readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setText(Filesinterface.ReadFile(TextToSpeech.this));
            }
        });*/
        loadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = readSDCard();
                if (list != null && list.size() > 0) {
                    listAdapter = new ListAdapter(context, list, texttoaudioconversion);
                    recyclerView.setAdapter(listAdapter);
                } else {
                    Toast.makeText(context, "No text files exited in " + path, Toast.LENGTH_LONG).show();
                }
            }
        });


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Filesinterface.saveToFile(inputtext.getText().toString())) {
                    Toast.makeText(TextToSpeech.this, "File saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TextToSpeech.this, "File not saved error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        texttoaudioconversion = new android.speech.tts.TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int checkingstatus) {
                if (checkingstatus != android.speech.tts.TextToSpeech.ERROR) {
                    texttoaudioconversion.setLanguage(Locale.US);
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String speaking = inputtext.getText().toString();
                Toast.makeText(getApplicationContext(), speaking, Toast.LENGTH_SHORT).show();
                texttoaudioconversion.speak(speaking, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    private List<String> readSDCard() {
        List<String> list = new ArrayList<>();
        //It have to be matched with the directory in SDCard
        File f = new File(Filesinterface.path);

        File[] files = f.listFiles();

        if (files != null) {
            Log.e("Files  ", files.length + "");
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
      /*It's assumed that all file in the path are in supported type*/
                String filePath = file.getPath();
                if (filePath.endsWith(".txt")) // Condition to check .txt file extension
                    list.add(filePath);
            }
        }
        return list;
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
     *
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