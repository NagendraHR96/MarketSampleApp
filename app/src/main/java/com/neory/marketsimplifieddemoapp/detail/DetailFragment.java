package com.neory.marketsimplifieddemoapp.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.neory.marketsimplifieddemoapp.R;
import com.neory.marketsimplifieddemoapp.ui.model.DetailJsonResult;

public class DetailFragment extends Fragment {
    NetworkImageView user_pic;
    DetailViewModel detailViewModel;
    TextView name,email,type,location;
    String url;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            url = bundle.getString("URL", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //checkInternet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        name=root.findViewById(R.id.name);
        email=root.findViewById(R.id.email);
        type=root.findViewById(R.id.type);
        user_pic=root.findViewById(R.id.user_pic);
        location=root.findViewById(R.id.location);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        // Inflate the layout for this fragment
        detailViewModel.setDetailsURL(url);
        detailViewModel.getJsonObject().observe(getViewLifecycleOwner(), new Observer<DetailJsonResult>() {
            @Override
            public void onChanged(DetailJsonResult detailJsonResult) {
                name.setText("Name: "+detailJsonResult.getName());
                email.setText("Email: "+detailJsonResult.getEmail());
                type.setText("Type: "+detailJsonResult.getType());
                location.setText("Location: "+detailJsonResult.getLocation());
                user_pic.setImageUrl(detailJsonResult.getAvatar_url(), mImageLoader); }

        });
        detailViewModel.getUserDetails();

        return root;
    }

}