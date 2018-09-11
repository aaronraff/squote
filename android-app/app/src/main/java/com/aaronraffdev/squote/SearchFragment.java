package com.aaronraffdev.squote;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends Fragment {
    private RecyclerView companyRecyclerView;
    private RecyclerView.Adapter companyAdaper;
    private RecyclerView.LayoutManager companyLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        companyRecyclerView = (RecyclerView) view.findViewById(R.id.company_recycler_view);

        companyLayoutManager = new LinearLayoutManager(getActivity());
        companyRecyclerView.setLayoutManager(companyLayoutManager);

        Stock[] test = {new Stock("Google")};
        companyAdaper = new CompanyAdapter(test);
        companyRecyclerView.setAdapter(companyAdaper);
    }
}
