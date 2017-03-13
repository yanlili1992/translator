package com.example.myapplicationtranslator.interfaze;

import android.view.View;

/**
 * Created by liuht on 2017/3/13.
 */

public interface OnRecyclerViewOnClickListener {

    void OnItemClick(View view,int position);

    void OnSubViewClick(View view, int position);
}
