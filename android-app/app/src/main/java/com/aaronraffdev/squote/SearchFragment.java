package com.aaronraffdev.squote;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView companyRecyclerView;
    private RecyclerView.Adapter companyAdaper;
    private RecyclerView.LayoutManager companyLayoutManager;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // Search the DB for the possible companies
                    String queryText = searchQuery.getText().toString();

                    // Need this for querying Firebase
                    queryText = queryText.substring(0, 1).toUpperCase()
                                + queryText.substring(1);

                    /* (Firebase Docs)
                       The \uf8ff character used in the query above is a very
                       high code point in the Unicode range.
                     */
                    Query query = database.child("availableSymbols").orderByChild("name")
                            .startAt(queryText)
                            .endAt(queryText.toLowerCase() + "\uf8ff")
                            .limitToFirst(5);

                    Log.d("TEST", queryText);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("RIP", "rip");
                            if(dataSnapshot.exists()) {
                                Log.d("START", dataSnapshot.toString());
                                // Clear and populate the data set
                                dataSet.clear();

                                for(DataSnapshot company : dataSnapshot.getChildren()) {
                                    String symbol = company.child("symbol").getValue().toString();
                                    String name = company.child("name").getValue().toString();
                                    dataSet.add(new Stock(symbol, name));
                                    Log.d("test", company.child("symbol").getValue().toString());
                                }

                                // Let the adapter know that it needs to update
                                companyAdaper.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                return false;
            }
        });
    }
}
