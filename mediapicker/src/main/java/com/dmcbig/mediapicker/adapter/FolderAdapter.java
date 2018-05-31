package com.dmcbig.mediapicker.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.entity.Media;

import java.util.ArrayList;

/**
 * Created by dmcBig on 2017/7/19.
 */

public class FolderAdapter extends BaseAdapter {
    ArrayList<Folder> folders;
    private LayoutInflater mInflater;
    private Context mContext;
    int lastSelected = 0;

    public FolderAdapter(ArrayList<Folder> folders, Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.folders = folders;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Folder getItem(int position) {
        return folders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateAdapter(ArrayList<Folder> list) {
        this.folders = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.folders_view_item, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Folder folder = getItem(position);
        Media media;
        if (folder.getMedias().size() > 0) {
            media = folder.getMedias().get(0);
            Glide.with(mContext)
                    .load(Uri.parse("file://" + media.path))
                    .into(holder.cover);
        } else {
            holder.cover.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.default_image));
        }

        holder.name.setText(folder.name);

        holder.size.setText(folder.getMedias().size() + "" + mContext.getString(R.string.count_string));
        holder.indicator.setVisibility(lastSelected == position ? View.VISIBLE : View.INVISIBLE);
        return view;
    }


    public void setSelectIndex(int i) {
        if (lastSelected == i) return;
        lastSelected = i;
        notifyDataSetChanged();
    }

    public ArrayList<Media> getSelectMedias() {
        return folders.get(lastSelected).getMedias();
    }

    class ViewHolder {
        ImageView cover, indicator;
        TextView name, path, size;

        ViewHolder(View view) {
            cover = (ImageView) view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.name);
            path = (TextView) view.findViewById(R.id.path);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            view.setTag(this);
        }
    }
}
