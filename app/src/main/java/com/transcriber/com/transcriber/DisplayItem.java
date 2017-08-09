package com.transcriber.com.transcriber;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.transcriber.com.transcriber.data.DBHelper;

public class DisplayItem extends AppCompatActivity {

    private EditText etTitle;
    private EditText etText;
    private Button bSend;
    private Button bSave;
    private Spinner spin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);

        String s = getIntent().getStringExtra("TITLE");
        String t = getIntent().getStringExtra("TEXT");





        etTitle  = (EditText) findViewById(R.id.title);
        etTitle.setText(s);

        etText  = (EditText) findViewById(R.id.text);
        etText.setText(t);

        spin = (Spinner) findViewById(R.id.sCategory);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.selectedCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);


        bSend = (Button) findViewById(R.id.send);
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                i.putExtra(Intent.EXTRA_SUBJECT, getIntent().getStringExtra("TITLE"));
                i.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("TEXT"));
                try {
                    startActivity(Intent.createChooser(i,"Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DisplayItem.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
                }
            }
        });

        bSave = (Button) findViewById(R.id.save);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String text = etText.getText().toString();
                String cat = spin.getSelectedItem().toString();
                String sid = getIntent().getStringExtra("ID");
                Long id = Long.parseLong(sid);
                Integer i = (int) (long) id;


                DBHelper helper = new DBHelper(getBaseContext());



                helper.updateItem(i,title, text, cat);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int clickedItemId = item.getItemId();

        // display category_all
        if(clickedItemId == R.id.tts){
            Intent j = new Intent(getApplicationContext(), TextToSpeech.class);

            startActivity(j);
        } else if(clickedItemId == R.id.stt){
            Intent i = new Intent(getApplicationContext(), SpeechToText.class);
            startActivity(i);
        }

        return true;
    }
}
