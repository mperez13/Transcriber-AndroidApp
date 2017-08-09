package com.transcriber.com.transcriber;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "speechToText";
    protected static final int RESULT_SPEECH = 1;
    public static StringBuilder tempTextResult;


    private ImageButton btnSpeak;
    private TextView txtText;
    private Long tempFileNameLong;
    final private String audioFileExtension = ".mp3";
    final private String textFileExtension = ".mp3.txt";
    public ArrayList<String> audioArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        tempTextResult = new StringBuilder();

        btnSpeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                speakButtonOnClick(v);
            }
        });

    }
    // when the speak button is clicked
    public void speakButtonOnClick(View view) {
        Log.i("ROUNDBUTTON", "RECORD BUTTON TOUCHED");
        Toast.makeText(SpeechToText.this, "Recording...", Toast.LENGTH_LONG).show();
        startRecording();
    }
    /**
     * Creates folder to keep text files
     */
    private void createFolder(){
        // Folder for the audio
        /* getExternalStorageDirectory Returns the primary shared/external storage directory*/
        File folderAudio = new File(Environment.getExternalStorageDirectory() + File.separator + "Audio");
        //Folder for the text
        File folderText = new File(Environment.getExternalStorageDirectory() + File.separator + "Text");

        //if folders do not exist create them
        if(!(folderAudio.exists())){
            /*mkdirs() - creates dir named by path name; includes parent directories*/
            folderAudio.mkdirs();
            Log.d(TAG +"Audio Dir", Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Audio");
        }
        if(!(folderText.exists())){
            folderAudio.mkdirs();
            Log.d(TAG +"Text Dir", Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Text");
        }

    }

    /**
     * startRecording() - start Recording audio that will stop when user presses stop. Will use Google Speech Recognition API.
     *
     */
    private void startRecording(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

                intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
                intent.putExtra("android.speech.extra.GET_AUDIO", true);

                /* startActivityForResult will start a new activity and expect a result (the audio and text)*/
                try{
                    startActivityForResult(intent, RESULT_SPEECH);
                }catch(ActivityNotFoundException e){
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Device Does Not Support Speech Recognition", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        };
        Thread spechToTextThread = new Thread(r);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case RESULT_SPEECH:{
                if(requestCode == RESULT_OK && data != null){
                    // array of the recognition results when performing ACTION_RECOGNIZE_SPEECH
                    ArrayList<String> textOutput = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i(TAG + "Text Ouput", textOutput.get(0));

                    // get text; delete any data in tempText first
                    tempTextResult.delete(0,tempTextResult.length());
                    tempTextResult.append(textOutput.get(0));

                    // Set the directory for the audio
                    String audio_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Audio";

                    //Set the directory for the text
                    String text_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Text";

                    // Retrieve Audio and place appropriate folder
                    try{
                        Uri audioUri = data.getData();
                        ContentResolver contentResolver = getContentResolver();

                        // Set the file as the current time
                        tempFileNameLong = System.currentTimeMillis();
                        String audioFileName = Long.toString(tempFileNameLong) + audioFileExtension;

                        //Create file named as stated above
                        File file = new File(audio_dir, audioFileName);
                        InputStream audioFileStream = contentResolver.openInputStream(audioUri);
                        OutputStream out = new FileOutputStream(file);

                        try{
                            byte[] buffer = new byte[4* 1024];
                            int read;
                            while((read = audioFileStream.read(buffer)) != -1){
                                out.write(buffer, 0, read);
                            }
                            out.flush();
                            out.close();
                        }finally {
                            out.close();
                            audioFileStream.close();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    // Retrieve Text and place appropriate folder
                    String textFileName = Long.toString(tempFileNameLong) + textFileExtension ;
                    File outputFile = new File(text_dir, textFileName);

                    try{
                        FileOutputStream textFileStream = new FileOutputStream(outputFile);
                        textFileStream.write(textOutput.get(0).getBytes());
                        textFileStream.close();

                        // Add array of Audio files to recyclerView
                        String fileName = Long.toString(tempFileNameLong);
                        audioArrayList.add(0, fileName + audioFileExtension);

                        //refreshRecyclerView();

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }
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
