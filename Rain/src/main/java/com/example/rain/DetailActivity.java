package com.example.rain;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by Jhon on 02/09/13.
 * Detail Activity
 * This activity provides us the detail information of the selected record
 */

public class DetailActivity extends FragmentActivity {
    private  String name,phone,party,office,link,district,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        name = getIntent().getExtras().getString("name");
        phone = getIntent().getExtras().getString("phone");
        party = getIntent().getExtras().getString("party");
        office = getIntent().getExtras().getString("office");
        link = getIntent().getExtras().getString("link");
        district = getIntent().getExtras().getString("district");
        state = getIntent().getExtras().getString("state");


        if(name!=null)
            ((TextView) findViewById(R.id.name)).setText("Name: " + name);

        if(phone!=null)
            ((TextView) findViewById(R.id.phone)).setText("Phone: " + phone);

        if(party!=null)
            ((TextView) findViewById(R.id.party)).setText("Party : " + party);

        if(office!=null)
            ((TextView) findViewById(R.id.office)).setText("Office : " + office);

        if(link!=null)
            ((TextView) findViewById(R.id.link)).setText("Site : " + Html.fromHtml(link));

        if(state!=null)
            ((TextView) findViewById(R.id.state)).setText("State : " + state);

        if(district!=null)
            ((TextView) findViewById(R.id.district)).setText("District : " + district);


    }

}
