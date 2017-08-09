package com.transcriber.com.transcriber;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayItem extends AppCompatActivity {

    private EditText etTitle;
    private EditText etText;
    private Button bSend;

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


    }
}
