package com.example.formacio.cameraexercise.Domain;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.formacio.cameraexercise.R;

import org.w3c.dom.Text;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //private String[] mImages;
    private Cursor mMediaStoreCursor;
    private final Activity mActivity;


    public MyAdapter(Activity mActivity){
        this.mActivity = mActivity;
    }

    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView){

        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        Bitmap bitmap = getBitmapFromMediaStore(position);
        if(bitmap != null) {
            holder.mTextView.setImage(mImages[position]);
        }
    }

    public int getItemCount(){
        return (mMediaStoreCursor == null) ? 0 : mMediaStoreCursor.getCount();
    }

    private Bitmap getBitmapFromMediaStore(int position){
        int idIndex = mMediaStoreCursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        mMediaStoreCursor.moveToPosition(position);
        return MediaStore.Images.Thumbnails.getThumbnail(
                mActivity.getContentResolver(),
                mMediaStoreCursor.getLong(idIndex),
                MediaStore.Images.Thumbnails.MICRO_KIND,
                null
        );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mImageView;



        public ViewHolder(View itemView){
            super(itemView);
            this.mImageView = (ImageView)itemView.findViewById(R.id.image_item_view);
         }
    }

    private Cursor swapCursor(Cursor cursor){
        if(mMediaStoreCursor == null){
            return null;
        }
        Cursor oldCursor = mMediaStoreCursor;
        this.mMediaStoreCursor = cursor;
        if(cursor != null){
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    public void changeCursor(Cursor cursor){
        Cursor oldCursor = swapCursor(cursor);
        if(oldCursor != null){
            oldCursor.close();
        }
    }

    /*public MyAdapter(String[] myImages){
        mImages = myImages;
    }
*/

}
