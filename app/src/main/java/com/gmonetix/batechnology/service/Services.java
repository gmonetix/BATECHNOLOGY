package com.gmonetix.batechnology.service;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gmonetix.batechnology.R;
import com.gmonetix.batechnology.adapter.ServiceAdapter;
import com.gmonetix.batechnology.model.Service;

import java.util.ArrayList;
import java.util.List;

public class Services extends Fragment {

    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private List<Service> serviceList;

    public Services() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_services, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        serviceList = new ArrayList<>();
        adapter = new ServiceAdapter(getActivity(),serviceList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareServices();

        return view;
    }

    private void prepareServices() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5};

        Service a = new Service(getResources().getString(R.string.web_development), getResources().getString(R.string.web_development_summary), covers[0],getResources().getString(R.string.web_development_content_browser_url));
        serviceList.add(a);

        a = new Service(getResources().getString(R.string.e_commerce_solution), getResources().getString(R.string.e_commerce_solution_summary), covers[1],getResources().getString(R.string.e_commerce_solution_content_browser_url));
        serviceList.add(a);

        a = new Service(getResources().getString(R.string.mobile_app_dev), getResources().getString(R.string.mobile_app_dev_summary), covers[2],getResources().getString(R.string.mobile_app_dev_content_browser_url));
        serviceList.add(a);

        a = new Service(getResources().getString(R.string.seo), getResources().getString(R.string.seo_summary), covers[3],getResources().getString(R.string.seo_content_browser_url));
        serviceList.add(a);

        a = new Service(getResources().getString(R.string.logo_branding), getResources().getString(R.string.logo_branding_summary), covers[4],getResources().getString(R.string.logo_branding_content_browser_url));
        serviceList.add(a);

        adapter.notifyDataSetChanged();

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {

                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
