package com.transcriber.com.transcriber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class DisplayItem extends AppCompatActivity {

    private EditText etTitle;
    private EditText etText;

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


    }
}
