package com.example.dove0012.yupaopao.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Created by liuzl on 2016/3/3.
 */
public abstract class ViewHolder extends RecyclerView.ViewHolder{
    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
