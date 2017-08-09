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

import static android.R.attr.id;


// added a spinner as well as a arguments needed
public class AddToDoFragment extends DialogFragment {

    private EditText etTitle;
    private EditText etText;
    private Button add;
    private final String TAG = "addtodofragment";
    private Spinner spin;

    public AddToDoFragment() {
    }

    //To have a way for the activity to get the data from the dialog
    public interface OnDialogCloseListener {
        void closeDialog(String title, String text, int id, String category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_adder, container, false);
        // adding a spinner to choose a category
        spin = (Spinner) view.findViewById(R.id.sCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.selectedCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        etTitle = (EditText) view.findViewById(R.id.title);
        etText = (EditText) view.findViewById(R.id.text);
        add = (Button) view.findViewById(R.id.add);





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                Log.d(TAG, "id: " + id);
                activity.closeDialog(etTitle.getText().toString(), etText.getText().toString(), id, spin.getSelectedItem().toString());
                AddToDoFragment.this.dismiss();
            }
        });

        return view;
    }
}