package com.transcriber.com.transcriber;

import android.content.ContentValues;
import android.content.Intent;
<<<<<<< HEAD
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
=======
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
>>>>>>> 5ec73b97c00ba814d7ba9af43403a09f48de7fc3
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
<<<<<<< HEAD
import android.widget.Toast;
import android.content.Context;

import java.io.File;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mainActivity";
=======

import com.transcriber.com.transcriber.data.Contract;
import com.transcriber.com.transcriber.data.DBHelper;

public class MainActivity extends AppCompatActivity implements AddToDoFragment.OnDialogCloseListener, UpdateToDoFragment.OnUpdateDialogCloseListener{

    private RecyclerView rv;
    private FloatingActionButton button;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    ToDoListAdapter adapter;
    private final String TAG = "mainactivity";
    private String picked = "all";

    // added the missing arguments to the code as well as added methods that were needed
>>>>>>> 5ec73b97c00ba814d7ba9af43403a09f48de7fc3

    private View view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        view = findViewById(R.id.main_view);

        checkPermissionGranted(view);
        createFolder();
        Toast.makeText(MainActivity.this, getApplicationContext().getApplicationInfo().dataDir, Toast.LENGTH_LONG).show();

    }

    /**
     * Creates folder to keep text files
     */
    public static void createFolder() {

        /* Folder for the audio*/
            /* getExternalStorageDirectory Returns the primary shared/external storage directory*/
        File folderAudio = new File(Environment.getExternalStorageDirectory() + File.separator + "Audio");
        //Folder for the text
        File folderText = new File(Environment.getExternalStorageDirectory() + File.separator + "Text");

        //if folders do not exist create them
        if (!(folderAudio.exists())) {
                /*mkdirs() - creates dir named by path name; includes parent directories*/
            folderAudio.mkdirs();
        }
        if (!(folderText.exists())) {
            folderAudio.mkdirs();
        }
    }
    /*check if required permission are granted*/
    private void checkPermissionGranted(View view){
        int audio_permission = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        int storage_permission = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        boolean permissions = audio_permission == PackageManager.PERMISSION_GRANTED && storage_permission == PackageManager.PERMISSION_GRANTED;

        if(!permissions){
            ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, 200);
        }
=======
        Log.d(TAG, "oncreate called in main activity");
        button = (FloatingActionButton) findViewById(R.id.addToDo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddToDoFragment frag = new AddToDoFragment();
                frag.show(fm, "addtodofragment");
            }
        });
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
>>>>>>> 5ec73b97c00ba814d7ba9af43403a09f48de7fc3
    }




    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
    }

    @Override
    protected void onStart() {
        super.onStart();

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        if(picked.equals("all")){
            cursor = getAllItems(db);
        }
        else{
            cursor = getCategoryItems(db);
        }

        adapter = new ToDoListAdapter(cursor, new ToDoListAdapter.ItemClickListener() {

            @Override
            public void onItemClick(int pos, String title, String text, long id, String category) {
//                Log.d(TAG, "item click id: " + id);
//
//
//                FragmentManager fm = getSupportFragmentManager();
//
//                UpdateToDoFragment frag = UpdateToDoFragment.newInstance(title, text, id, category);
//                frag.show(fm, "updatetodofragment");
                Intent intent = new Intent(getBaseContext(), DisplayItem.class);
                intent.putExtra("TITLE", title);
                intent.putExtra("TEXT", text);
                startActivity(intent);
            }
        });

        rv.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + id);
                removeToDo(db, id);
                adapter.swapCursor(getAllItems(db));
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    public void closeDialog(String title, String text, int id, String category) {
        addToDo(db, title, text, category);
        cursor = getAllItems(db);
        adapter.swapCursor(cursor);
    }


    public String formatDate(int year, int month, int day) {
        return String.format("%04d-%02d-%02d", year, month + 1, day);
    }
    private Cursor getAllItems(SQLiteDatabase db) {
        return db.query(
                Contract.TABLE_TODO.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.TABLE_TODO.COLUMN_NAME_TITLE
        );
    }


    private Cursor getCategoryItems(SQLiteDatabase db) {
        String all[] = new String[]{
                Contract.TABLE_TODO._ID,
                Contract.TABLE_TODO.COLUMN_NAME_TITLE,
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY,
                Contract.TABLE_TODO.COLUMN_NAME_TEXT,
        };

        return db.query(
                Contract.TABLE_TODO.TABLE_NAME,
                all,
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY + "=?",
                new String[]{picked},
                null,
                null,
                Contract.TABLE_TODO.COLUMN_NAME_TITLE
        );
    }

    private long addToDo(SQLiteDatabase db, String title, String text, String category) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_TITLE, title);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_TEXT, text);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY, category);
        return db.insert(Contract.TABLE_TODO.TABLE_NAME, null, cv);
    }

    private boolean removeToDo(SQLiteDatabase db, long id) {
        Log.d(TAG, "deleting id: " + id);
        return db.delete(Contract.TABLE_TODO.TABLE_NAME, Contract.TABLE_TODO._ID + "=" + id, null) > 0;
    }


    private int updateToDo(SQLiteDatabase db, String title, String text, long id, String category){



        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_TITLE, title);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_TEXT, text);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY, category);



        return db.update(Contract.TABLE_TODO.TABLE_NAME, cv, Contract.TABLE_TODO._ID + "=" + id, null);
    }

    @Override
    public void closeUpdateDialog(String title, String text, long id, String category) {
        updateToDo(db, title, text, id, category);
        if(picked.equals("all")){
            cursor = getAllItems(db);
        }
        else{
            cursor = getCategoryItems(db);
        }
        adapter.swapCursor(cursor);
    }


    // on create
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.categorys, menu);
        return true;
    }

    // choosing the desired category
    @Override
<<<<<<< HEAD
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
            case R.id.main:
                Intent k = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
=======
    public boolean onOptionsItemSelected(MenuItem item){
        int clickedItemId = item.getItemId();

        // display category_all
        if(clickedItemId == R.id.cAll){
            picked = "all";
            adapter.swapCursor(getAllItems(db));
        } else if(clickedItemId == R.id.cSchool){
            picked = "school";
            adapter.swapCursor(getCategoryItems(db));
        } else if(clickedItemId == R.id.cWebsite){
            picked = "website";
            adapter.swapCursor(getCategoryItems(db));
        } else if(clickedItemId == R.id.cWork){
            picked = "work";
            adapter.swapCursor(getCategoryItems(db));
        } else if(clickedItemId == R.id.cArt){
            picked = "art";
            adapter.swapCursor(getCategoryItems(db));
        } else if(clickedItemId == R.id.tts){
            Intent j = new Intent(getApplicationContext(), TextToSpeech.class);
            startActivity(j);
        } else if(clickedItemId == R.id.stt){
            Intent i = new Intent(getApplicationContext(), SpeechToText.class);
            startActivity(i);
>>>>>>> 5ec73b97c00ba814d7ba9af43403a09f48de7fc3
        }

        return true;
    }

}