package com.transcriber.com.transcriber;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


// added spinner as well as arguments that were needed

public class UpdateToDoFragment extends DialogFragment {

    private EditText title;
    private EditText text;

    private Button add;
    private final String TAG = "updatetodofragment";
    private long id;
    private Spinner spin;


    public UpdateToDoFragment(){}

    public static UpdateToDoFragment newInstance(String title, String text, long id, String category) {
        UpdateToDoFragment f = new UpdateToDoFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();

        args.putLong("id", id);
        args.putString("title", title);
        args.putString("category", category);
        args.putString("text", text);

        f.setArguments(args);

        return f;
    }

    //To have a way for the activity to get the data from the dialog
    public interface OnUpdateDialogCloseListener {
        void closeUpdateDialog(String title, String text, long id, String category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_adder, container, false);
        title = (EditText) view.findViewById(R.id.title);
        text = (EditText) view.findViewById(R.id.text);
        add = (Button) view.findViewById(R.id.add);

        // Add category spinner
        //
        //
        spin = (Spinner) view.findViewById(R.id.sCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.selectedCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);


        id = getArguments().getLong("id");
        String stitle = getArguments().getString("title");
        String stext = getArguments().getString("text");


        title.setText(stitle);
        text.setText(stext);

        add.setText("Update");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateToDoFragment.OnUpdateDialogCloseListener activity = (UpdateToDoFragment.OnUpdateDialogCloseListener) getActivity();
                Log.d(TAG, "id: " + id);
                activity.closeUpdateDialog(title.getText().toString(), text.getText().toString(), id, spin.getSelectedItem().toString());
                UpdateToDoFragment.this.dismiss();
            }
        });

        return view;
    }
}