package com.neory.marketsimplifieddemoapp.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.neory.marketsimplifieddemoapp.R;
import com.neory.marketsimplifieddemoapp.detail.DetailFragment;
import com.neory.marketsimplifieddemoapp.ui.PaginationListener;
import com.neory.marketsimplifieddemoapp.ui.adapter.IteamClickListner;
import com.neory.marketsimplifieddemoapp.ui.adapter.ListAdapter;
import com.neory.marketsimplifieddemoapp.ui.model.JsonObjectResult;

import java.util.ArrayList;
import java.util.List;

import static com.neory.marketsimplifieddemoapp.ui.PaginationListener.PAGE_START;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView list_Recyclerview;
    ListAdapter listAdapter;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    IteamClickListner iteamClickListner=new IteamClickListner() {
        @Override
        public void onItemClick(String val,String comment) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("URL", val);
            bundle.putString("comment",comment);
            detailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_host_fragment, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list_Recyclerview = root.findViewById(R.id.list_Recyclerview);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list_Recyclerview.setLayoutManager(linearLayoutManager);
        if(listAdapter == null)
            listAdapter = new ListAdapter(new ArrayList<JsonObjectResult>(), getActivity(),mImageLoader);
        listAdapter.iteamClick(iteamClickListner);
        list_Recyclerview.setAdapter(listAdapter);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<JsonObjectResult>>() {
            @Override
            public void onChanged(List<JsonObjectResult> jsonObjects) {
                    doApiCall();
            }
        });
        list_Recyclerview.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall();
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        if(homeViewModel.getText().getValue()!= null && homeViewModel.getText().getValue().size() ==0){
            homeViewModel.getUserDetails();
        }else{
            homeViewModel.getUserDetails();
        }
        return root;
    }

    private void doApiCall() {
        final ArrayList<JsonObjectResult> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    if(itemCount < homeViewModel.getText().getValue().size()) {
                        JsonObjectResult postItem = homeViewModel.getText().getValue().get(itemCount);
                        items.add(postItem);
                    }
                }
                if (currentPage != PAGE_START) listAdapter.removeLoading();
                listAdapter.addItems(items);

                if (currentPage < totalPage) {
                    listAdapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        Log.i("Nagendra","Sorry");
        super.onDestroy();
    }
}