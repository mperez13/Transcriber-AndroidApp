package com.transcriber.com.transcriber;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class SpeechToText extends AppCompatActivity {
    private static final String TAG = "speechToTextActivity";
    protected static final int RESULT_SPEECH = 1;
    public SharedPreferences sharedPreferences;

    public static StringBuilder tempTextResult;
    private ImageButton btnSpeak;
    private TextView txtText;
    private Long tempFileNameLong;
    final private String audioFileExtension = ".mp3";
    final private String textFileExtension = ".mp3.txt";


    public ArrayList<String> audioArrayList = new ArrayList<>();
    public ArrayList<String> textArrayList = new ArrayList<>();

    //recyclerview
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        tempTextResult = new StringBuilder();


       /* //RECYCLERVIEW
        mRecyclerView. findViewById(R.id.recyclerView); //link to activity_main.xml
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        // Getting the files to list in recyclerView
        mAdapter = new RecyclerViewAdapter(getAllFiles());
        mRecyclerView.setAdapter(mAdapter);
        refreshRecyclerView();*/

        btnSpeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                speakButtonOnClick(v);
            }
        });

    }
    // when the speak button is clicked
    public void speakButtonOnClick(View view) {
        Log.i(TAG, "Record Button Touched");
        //Toast.makeText(SpeechToText.this, "Recording...", Toast.LENGTH_LONG).show();
        startRecording();
    }

/*    *//**
     * Refresh the RecyclerView
     *//*
    private void refreshRecyclerView(){
        mAdapter.notifyDataSetChanged();
    }*/

    /**
     * startRecording() - start Recording audio that will stop when user presses stop. Will use Google Speech Recognition API.
     *
     */
    private void startRecording(){
        //Toast.makeText(SpeechToText.this, "startRecording()", Toast.LENGTH_LONG).show();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(SpeechToText.this, "speech to text run", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

                intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
                intent.putExtra("android.speech.extra.GET_AUDIO", true);
                //intent.putExtra("android.speech.extra.DICTATION_MODE", true);


                /* startActivityForResult will start a new activity and expect a result (the audio and text)*/
                try{
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");

                }catch(ActivityNotFoundException e){
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Device Does Not Support Speech Recognition", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        };
        Thread sttThread = new Thread(r);
        sttThread.run();
    }


    public void onPause(){
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    // array of the recognition results when performing ACTION_RECOGNIZE_SPEECH
                    ArrayList<String> textOutput = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    // Set the directory for the audio
                    String audio_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Audio";

                    //Set the directory for the text
                    String text_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Text";

                    // get text; delete any data in tempText first
                    tempTextResult.delete(0, tempTextResult.length());
                    tempTextResult.append(textOutput.get(0));

                    txtText.setText(tempTextResult);

                    // Retrieve Audio and place appropriate folder
                    try {
                        Uri audioUri = data.getData();
                        ContentResolver contentResolver = getContentResolver();

                        // Set the file as the current time
                        tempFileNameLong = System.currentTimeMillis();
                        String audioFileName = Long.toString(tempFileNameLong) + audioFileExtension;

                        //Create file named as stated above
                        File audiofileName = new File(audio_dir, audioFileName);
                        InputStream audioFileStream = contentResolver.openInputStream(audioUri);
                        OutputStream out = new FileOutputStream(audiofileName);
                        audioArrayList.add(0, audiofileName + audioFileExtension);

                        try {
                            byte[] buffer = new byte[4 * 1024];
                            int read;
                            while ((read = audioFileStream.read(buffer)) != -1) {
                                out.write(buffer, 0, read);
                            }
                            out.flush();
                            out.close();
                        } finally {
                            out.close();
                            audioFileStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Retrieve Text and place appropriate folder
                    String textFileName = Long.toString(tempFileNameLong) + textFileExtension;
                    File outputFile = new File(text_dir, textFileName);

                    try {
                        FileOutputStream textFileStream = new FileOutputStream(outputFile);
                        textFileStream.write(textOutput.get(0).getBytes());
                        textFileStream.close();

                        String fileName = Long.toString(tempFileNameLong);
                        textArrayList.add(0, textFileName);

                        //refreshRecyclerView();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
       // Toast.makeText(SpeechToText.this, "yes", Toast.LENGTH_LONG).show();
    }

    /**/

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
