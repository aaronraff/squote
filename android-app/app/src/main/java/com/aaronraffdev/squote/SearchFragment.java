package com.aaronraffdev.squote;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView companyRecyclerView;
    private RecyclerView.Adapter companyAdaper;
    private RecyclerView.LayoutManager companyLayoutManager;

    private ArrayList<Stock> dataSet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceBundle) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpListeners();

        companyRecyclerView = (RecyclerView) view.findViewById(R.id.company_recycler_view);

        companyLayoutManager = new LinearLayoutManager(getActivity());
        companyRecyclerView.setLayoutManager(companyLayoutManager);

        companyAdaper = new CompanyAdapter(dataSet);
        companyRecyclerView.setAdapter(companyAdaper);
    }

    private void setUpListeners() {
        final EditText searchQuery = (EditText) getActivity().findViewById(R.id.search_query);
        searchQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Search the DB for the possible companies
                }
                return false;
            }
        });
    }
}
