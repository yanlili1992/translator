package com.example.myapplicationtranslator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplicationtranslator.model.BingModel;
import com.marktony.translator.R;

import java.util.ArrayList;

/**
 * Created by liuht on 2017/3/13.
 */

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public final Context context;
    public final LayoutInflater inflater;
    public ArrayList<BingModel.Sample> samples;

    public SampleAdapter(Context context, ArrayList<BingModel.Sample> samples){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.samples = samples;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new SampleViewHolder(inflater.inflate(R.layout.sample_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        String s = samples.get(position).getEng()+"\n"+samples.get(position).getChn();
        ((SampleViewHolder)holder).textView.setText(s);
    }

    @Override
    public int getItemCount(){
        return samples.size();
    }


    public class SampleViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public SampleViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
