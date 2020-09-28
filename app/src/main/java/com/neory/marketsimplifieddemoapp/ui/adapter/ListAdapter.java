package com.neory.marketsimplifieddemoapp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.neory.marketsimplifieddemoapp.R;
import com.neory.marketsimplifieddemoapp.ui.model.JsonObjectResult;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    ArrayList<JsonObjectResult> jsonObjectResult;
    Context context;
    IteamClickListner iteamClickListner;
    private ImageLoader mImageLoader;
    public ListAdapter(ArrayList<JsonObjectResult> jsonObjectResult, Context context,ImageLoader mImageLoader) {
        this.jsonObjectResult = jsonObjectResult;
        this.context = context;
        this.mImageLoader = mImageLoader;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        return new ListAdapter.ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        String name = jsonObjectResult.get(position).getOwner().getLogin();
        String node = jsonObjectResult.get(position).getDescription();
        String userURL = jsonObjectResult.get(position).getOwner().getAvatar_url();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(userURL)) {
            holder.username.setText(name);
            holder.description.setText(node);
            holder.imageView.setImageUrl(userURL, mImageLoader); }
    }
    @Override
    public int getItemCount() {
        return jsonObjectResult.size()-1;
    }

    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username,description;
        NetworkImageView imageView;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            description=itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.twitter_avatar);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (iteamClickListner!=null){
                iteamClickListner.onItemClick(jsonObjectResult.get(getAdapterPosition()).getOwner().getUrl());
            }
        }
    }

    public void addItems(List<JsonObjectResult> postItems) {
        jsonObjectResult.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {

        jsonObjectResult.add(new JsonObjectResult());
        notifyItemInserted(jsonObjectResult.size() - 1);
    }
    public void removeLoading() {

        int position = jsonObjectResult.size() - 1;
        JsonObjectResult item = getItem(position);
        if (item != null) {
            jsonObjectResult.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        jsonObjectResult.clear();
        notifyDataSetChanged();
    }
    JsonObjectResult getItem(int position) {
        return jsonObjectResult.get(position);
    }
    public  void iteamClick(IteamClickListner iteamClickListner){
        this.iteamClickListner=iteamClickListner;
    }
}
