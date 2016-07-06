package com.chong.coordinatorlayoutdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chong on 2016/7/6.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    // 数据集
    private List<String> mDataset;

    public MyAdapter(List<String> dataset) {
        super();
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        viewHolder.mTextView.setText(mDataset.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            mTextView = (TextView) itemView;

        }

    }

}




