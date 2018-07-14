package com.aaronraffdev.squote;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeUIUpdater implements UIUpdater<Stock> {
    private Activity activity;
    private TextView companyNameView;
    private TextView priceView;
    private EditText symbolField;

    public HomeUIUpdater(Activity activity) {
        this.activity = activity;
        this.companyNameView = activity.findViewById(R.id.companyName);
        this.priceView = activity.findViewById(R.id.price);
        this.symbolField = activity.findViewById(R.id.symbol);
    }

    public void update(Stock s) {
        String priceString = new StringBuilder()
                .append("$")
                .append(s.getLatestPrice())
                .toString();

        companyNameView.setText(s.getCompanyName());
        priceView.setText(priceString);
    }
}
