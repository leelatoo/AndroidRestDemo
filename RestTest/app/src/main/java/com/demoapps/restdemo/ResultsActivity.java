/*
 * Copyright (c) 2019. Leeladevi Balasundaram, leelabalasundaram@gmail.com
 */

package com.demoapps.restdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.parseColor("#D9EBE6"));

        Intent intent = getIntent();
        RideDetails rd = (RideDetails)intent.getSerializableExtra("RideDetails");

        AddTextBlock(linearLayout,"", "#003D85C6");
        AddTextBlock(linearLayout," Estimated Time : " + rd.getEstimatedTime(), "#3D85C6");
        for (int i = 0; i < rd.getRide().size(); i++)
        {
            String txt = "  " + (i+1) + ". " + rd.getRide().get(i);
            AddTextBlock(linearLayout, txt, "#3D85C6");
        }
    }

    private void AddTextBlock(LinearLayout linearLayout, String text, String bgcolor)
    {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_VERTICAL);

        textView.setBackgroundColor(Color.parseColor(bgcolor));
        linearLayout.addView(textView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 20, 30, 20);
        textView.setLayoutParams(params);
    }
}
