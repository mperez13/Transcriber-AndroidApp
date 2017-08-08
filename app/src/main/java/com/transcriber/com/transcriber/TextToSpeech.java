package com.transcriber.com.transcriber;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
public class TextToSpeech extends AppCompatActivity {
    Button readbutton;
    EditText inputtext;
    TextView content;
    Button savebutton;
    Button speak;
    android.speech.tts.TextToSpeech texttoaudioconversion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (TextView) findViewById(R.id.content);
        inputtext = (EditText) findViewById(R.id.inputtext);
        readbutton = (Button) findViewById(R.id.readbutton);
        savebutton = (Button) findViewById(R.id.savebutton);
        speak = (Button) findViewById(R.id.button);
//        readbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                content.setText(Filesinterface.ReadFile(TextToSpeech.this));
//            }
//        });

//        savebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Filesinterface.saveToFile(inputtext.getText().toString()))
//                {
//                    Toast.makeText(TextToSpeech.this, "File saved", Toast.LENGTH_SHORT).show();
//                } else
//                {
//                    Toast.makeText(TextToSpeech.this, "File not saved error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


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
}
