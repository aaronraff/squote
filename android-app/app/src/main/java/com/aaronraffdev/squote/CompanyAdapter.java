package com.aaronraffdev.squote;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    public ArrayList<Stock> dataSet;

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        public View companyListItem;

        public CompanyViewHolder(View v) {
            super(v);
            companyListItem = v;
        }
    }

    public CompanyAdapter(ArrayList<Stock> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public CompanyAdapter.CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_list_item, parent, false);

        CompanyViewHolder vh = new CompanyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        TextView companyNameTextView = (TextView) holder.companyListItem
                .findViewById(R.id.company_name_text_view);
        companyNameTextView.setText(dataSet.get(position).getCompanyName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
