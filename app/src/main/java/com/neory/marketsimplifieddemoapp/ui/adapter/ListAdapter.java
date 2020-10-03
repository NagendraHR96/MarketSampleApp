package com.neory.marketsimplifieddemoapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.neory.marketsimplifieddemoapp.R;
import com.neory.marketsimplifieddemoapp.databinding.UserListBinding;
import com.neory.marketsimplifieddemoapp.ui.model.JsonObjectResult;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    ArrayList<JsonObjectResult> jsonObjectResult;
    Context context;
    IteamClickListner iteamClickListner;
    private ImageLoader mImageLoader;
    UserListBinding binding;
    public ListAdapter(ArrayList<JsonObjectResult> jsonObjectResult, Context context,ImageLoader mImageLoader) {
        this.jsonObjectResult = jsonObjectResult;
        this.context = context;
        this.mImageLoader = mImageLoader;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         binding =DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()),R.layout.user_list,parent,false);
        return new ListAdapter.ListHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        binding.setUser(jsonObjectResult.get(position));
        binding.twitterAvatar.setImageUrl(jsonObjectResult.get(position).getOwner().getAvatar_url(),mImageLoader);
        binding.executePendingBindings();
    }
    @Override
    public int getItemCount() {
        return jsonObjectResult.size()-1;
    }

    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (iteamClickListner!=null){
                iteamClickListner.onItemClick(jsonObjectResult.get(getAdapterPosition()).getOwner().getUrl(),jsonObjectResult.get(getAdapterPosition()).getOwner().getComment());
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
