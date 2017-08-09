package com.transcriber.com.transcriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mounika on 8/8/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> list;
    android.speech.tts.TextToSpeech texttoaudioconversion;
    private static ProgressDialog progressDialog;


    public ListAdapter(Context context, List<String> itemModels,android.speech.tts.TextToSpeech tts) {
        this.list = itemModels;
        this.mContext = context;
        this.texttoaudioconversion=tts;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.file_path_list, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FileViewHolder fileViewHolder = (FileViewHolder) holder;
        fileViewHolder.tvFilePath.setText(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFilePath,content;
        Button btn_speakfile,btn_view_text;
        ImageButton btn_share;
        public FileViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            tvFilePath = (TextView) itemView.findViewById(R.id.path);
            btn_share= (ImageButton) itemView.findViewById(R.id.btn_share);
            btn_share.setOnClickListener(this);
            btn_speakfile = (Button) itemView.findViewById(R.id.btn_read);
            btn_speakfile.setOnClickListener(this);
            btn_view_text= (Button) itemView.findViewById(R.id.btn_view_text);
            btn_view_text.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String fim =list .get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.btn_read:
                    showProgressDialogWithText(mContext,"Please wait...");
                    try {
                        File file = new File(fim);
                        StringBuilder text = new StringBuilder();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;

                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                text.append('\n');
                            }
                            br.close();
                        } catch (IOException e) {
                            dismissProgressDialog();
                            e.printStackTrace();
                        }

                        dismissProgressDialog();
                        texttoaudioconversion.speak(text.toString(), android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                    }catch (Exception e){
                        e.printStackTrace();
                        dismissProgressDialog();
                    }
                    break;
                case R.id.btn_share:
                    File file = new File(list.get(getAdapterPosition()));
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
                    mContext.startActivity(Intent.createChooser(sharingIntent, "share file with"));
                    break;
                case R.id.btn_view_text:
                    content.setText(Filesinterface.ReadFile(mContext,list.get(getAdapterPosition())));
                    content.setVisibility(View.VISIBLE);
                    break;
            }
        }

        public void showProgressDialogWithText(Context context, String message) {
            Log.e("Progress dialog called", context.getClass().getSimpleName());
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage(message + "");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                if (progressDialog != null) {
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                }

            }
        }
        public void dismissProgressDialog() {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = null;
            }
        }
    }
}
