package com.example.myapplicationtranslator.ui;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationtranslator.adapter.SampleAdapter;
import com.example.myapplicationtranslator.db.DBUtil;
import com.example.myapplicationtranslator.db.NotebookDatabaseHelper;
import com.example.myapplicationtranslator.model.BingModel;
import com.example.myapplicationtranslator.util.NetworkUtil;
import com.marktony.translator.R;

import java.util.ArrayList;

import static com.marktony.translator.R.id.start;

/**
 * Created by liuht on 2017/3/13.
 */

public class TranslateFragment extends Fragment{

    private EditText editText;
    private TextView textViewClear;
    private ProgressBar progressBar;
    private TextView textViewResult;
    private ImageView imageViewMark;
    private View viewResult;
    private AppCompatButton button;

    private ArrayList<BingModel.Sample> samples;
    private RecyclerView recyclerView;
    private SampleAdapter adapter;

    private NotebookDatabaseHelper dbHelper;

    private String result = null;
    private BingModel model;

    private RequestQueue queue;

    private boolean isMarked = false;
    private boolean completeWithEnter;
    private boolean showSamples;

    public TranslateFragment(){}

    public static TranslateFragment newInstance() {return new TranslateFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        dbHelper = new NotebookDatabaseHelper(getActivity(),"MyStore.db",null,1);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        initViews(view);
        //在这里进行网络连接的判断，如果没有连接，则进行snackBar的提示
        //如果有网络连接，则不进行任何操作
        if(!NetworkUtil.isNetworkConnected(getActivity())){
            showNoNetwork();
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!NetworkUtil.isNetworkConnected(getActivity())){
                    showNoNetwork();
                }else if(editText.getText()==null||editText.getText().length()==0){
                    Snackbar.make(button,getString(R.string.no_input),Snackbar.LENGTH_SHORT).show();
                }else{
                    sendReq(editText.getText().toString());
                }
            }
        });
        textViewClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText.getEditableText().toString().length()!=0){
                    textViewClear.setVisibility(View.VISIBLE);
                }else{
                    textViewClear.setVisibility(View.INVISIBLE);
                }

                //handle the situation when text ends uo wih enter
                //监听回车事件
                if(completeWithEnter){
                    if(count == 1 && s.charAt(start) == "\n"){
                        editText.getText().replace(start, start+1,"");
                        sendReq(editText.getEditableText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imageViewMark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //在没有被收藏的情况下
                if(!isMarked){
                    imageViewMark.setImageResource(R.drawable.ic_grade_white_24dp);
                    Snackbar.make(button,R.string.add_to_notebook,Snackbar.LENGTH_SHORT).show();
                    isMarked = true;

                    ContentValues values = new ContentValues();
                    values.put("input",model.getWord());
                    values.put("output",result);
                    DBUtil.insertValue(dbHelper,values);

                    values.clear();
                }else{
                    imageViewMark.setImageResource(R.drawable.ic_star_border_white_24dp);
                    Snackbar.make(button,R.string.remove_from_notebook,Snackbar.LENGTH_SHORT).show();
                    isMarked = false;

                    DBUtil.deleteValue(dbHelper,model.getWord());
                }
            }
        });

        viewResult.findViewById(R.id.image_view_share).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND).setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,result);
                startActivity(Intent.createChooser(intent,getString(R.string.choose_app_to_share)));
            }
        });

        viewResult.findViewById(R.id.image_view_copy).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text",result);
                manager.setPrimaryClip(clipData);

                Snackbar.make(button,R.string.copy_done,Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
