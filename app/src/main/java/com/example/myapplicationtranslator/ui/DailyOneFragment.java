package com.example.myapplicationtranslator.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplicationtranslator.constant.Constants;
import com.example.myapplicationtranslator.db.DBUtil;
import com.example.myapplicationtranslator.db.NotebookDatabaseHelper;
import com.marktony.translator.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuht on 2017/3/13.
 */

public class DailyOneFragment extends Fragment{

    private RequestQueue queue;

    private TextView textViewEng;
    private TextView textViewChi;
    private ImageView imageViewMain;
    private ImageView ivStar;
    private ImageView ivCopy;
    private ImageView ivShare;

    private boolean isMarked = false;
    private NotebookDatabaseHelper dbHelper;
    private String imageUrl = null;
    public DailyOneFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        dbHelper = new NotebookDatabaseHelper(getActivity(),"MyStore.db",null,1);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_daily_one,container,false);

        initViews(view);

        requestData();

        ivStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //在没被收藏的情况下
                if(!isMarked){
                    ivStar.setImageResource(R.drawable.ic_grade_white_24dp);
                    Snackbar.make(ivShare,R.string.add_to_notebook,Snackbar.LENGTH_SHORT).show();
                    isMarked = true;

                    ContentValues values = new ContentValues();
                    values.put("input",textViewEng.getText().toString());
                    values.put("output",textViewChi.getText().toString());
                    DBUtil.insertValue(dbHelper,values);

                    values.clear();
                }else{
                    ivStar.setImageResource(R.drawable.ic_grade_white_24dp);
                    Snackbar.make(ivShare,R.string.add_to_notebook,Snackbar.LENGTH_SHORT).show();
                    isMarked = false;

                    DBUtil.deleteValue(dbHelper,textViewEng.getText().toString());
                }
            }
        });

        ivCopy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ClipboardManager manager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text",String.valueOf(textViewEng.getText()+"\n"+textViewChi.getText()));
                manager.setPrimaryClip(clipData);

                Snackbar.make(ivCopy,R.string.copy_done,Snackbar.LENGTH_SHORT).show();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND).setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,String.valueOf(textViewEng.getText())+"\n"+textViewChi.getText());
                startActivity(Intent.createChooser(intent,getString(R.string.choose_app_to_share)));

            }
        });
        return view;
    }

       public void initViews(View view){
           textViewChi = (TextView) view.findViewById(R.id.text_view_chi);
           textViewEng = (TextView) view.findViewById(R.id.text_view_eng);
           imageViewMain = (ImageView) view.findViewById(R.id.image_view_daily);

           ivStar = (ImageView) view.findViewById(R.id.image_view_mark_star);
           ivCopy = (ImageView) view.findViewById(R.id.image_view_copy);
           ivShare = (ImageView) view.findViewById(R.id.image_view_share);
    }

    private void requestData(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.DAILY_SENTENCE,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject jsonObject){
                try{
                    imageUrl = jsonObject.getString("picture2");
                    Glide.with(getActivity())
                            .load(imageUrl)
                            .asBitmap()
                            .centerCrop()
                            .into(imageViewMain);

                    textViewEng.setText(jsonObject.getString("content"));
                    textViewChi.setText(jsonObject.getString("note"));

                    if(DBUtil.queryIfItemExist(dbHelper,textViewEng.getText().toString())){
                        ivStar.setImageResource(R.drawable.ic_star_border_white_24dp);
                        isMarked = true;
                    }else{
                        ivStar.setImageResource(R.drawable.ic_star_border_white_24dp);
                        isMarked = false;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError){
            }
        });
        queue.add(request);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(imageUrl!=null){
            imageViewMain.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getActivity())
                    .load(imageUrl)
                    .asBitmap()
                    .centerCrop()
                    .into(imageViewMain);
        }
    }
}
