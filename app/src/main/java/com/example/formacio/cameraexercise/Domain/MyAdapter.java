package com.example.formacio.cameraexercise.Domain;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.formacio.cameraexercise.R;

import org.w3c.dom.Text;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private String[] mImages;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public ViewHolder(TextView tv){
            super(tv);
            mTextView = tv;
        }
    }

    public MyAdapter(String[] myImages){
        mImages = myImages;
    }

    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView){

        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(mImages[position]);
    }

    public int getItemCount(){
        return mImages.length;
    }
}
