package com.example.tuananh.module1.Activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuananh.module1.R;
import com.example.tuananh.module1.databinding.LayoutTaggedImageBinding;

import java.util.ArrayList;

public class TaggedImageAdapter extends RecyclerView.Adapter<TaggedImageAdapter.ViewHolder> {
    Context context;
    ArrayList<Bitmap> bitmaps;

    public TaggedImageAdapter(Context context, ArrayList<Bitmap> bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutTaggedImageBinding layoutTaggedImageBinding = DataBindingUtil
                .inflate(inflater,R.layout.layout_tagged_image,viewGroup,false);
        return new ViewHolder(layoutTaggedImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.layoutTaggedImageBinding.setBitmap(bitmaps.get(i));
        viewHolder.layoutTaggedImageBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        LayoutTaggedImageBinding layoutTaggedImageBinding;
        public ViewHolder(@NonNull LayoutTaggedImageBinding layoutTaggedImageBinding) {
            super(layoutTaggedImageBinding.getRoot());
            this.layoutTaggedImageBinding = layoutTaggedImageBinding;
        }
    }
}
