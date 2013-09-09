package com.example.rain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rain.adapters.RepresentativeAdapter;
import com.example.rain.entities.Person;
import com.example.rain.utitilities.RepresentativeProvider;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhon on 02/09/13.
 * Main Activity, which provide us the first list of the api.
 * Returns data on both representatives and senators by zipcode
 */

public class MainActivity extends FragmentActivity {

    private RepresentativeAdapter adapter;
    private ListView list;
    private String feed;
    private String filter;
    private ProgressBar progressBar;
    private List<Person> per = new ArrayList<Person>();
    private RepresentativeProvider utils = new RepresentativeProvider();
    private TextView title;
    private String type;
    private Aplication dtoFactory;
    private  Dao<Person, Integer> person;
    private  String filter2=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dtoFactory = (Aplication) getApplication();
        try {
           // dtoFactory.ClearPerson();
            person = dtoFactory.getContatoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (isOnline(this) == false) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Error!");
            alertDialog.setIcon(R.drawable.ic_launcher);
            alertDialog.setMessage("No internet connection");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();

        } else {

            title = (TextView) findViewById(R.id.title);

            if(getIntent() != null && getIntent().getExtras() != null) {
                filter = getIntent().getExtras().getString("filter");

            } else
                filter ="/getall_mems.php=";

            if(filter.equals("/getall_reps_byname.php")){
                title.setText(this.getString(R.string.search_by_reps_name));
                title.setHint("Name");

            }
            else if(filter.equals("/getall_mems.php")){
                title.setText(this.getString(R.string.search_by_zip));
                title.setHint("Zip Code");
            }
            else if(filter.equals("/getall_sens_byname.php")){
                title.setText(this.getString(R.string.search_by_senators_name));
                title.setHint("Name");

            }

            else if(filter.equals("/getall_reps_bystate.php")){
                title.setText(this.getString(R.string.search_by_reps_state));
                title.setHint("State");
            }

            else if(filter.equals("/getall_sens_bystate.php")){
                title.setText(this.getString(R.string.search_by_senators_state));
                title.setHint("State");

            }

            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            list = (ListView) findViewById(R.id.list);


            /** Execute AsyncTask **/
            if(filter!=null)
                new ReadJsonTask().execute(feed);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(MainActivity.this,
                            DetailActivity.class);
                    intent.putExtra("name", adapter.getItem(position).getName());
                    intent.putExtra("phone", adapter.getItem(position).getPhone());
                    intent.putExtra("party", adapter.getItem(position).getParty());
                    intent.putExtra("office", adapter.getItem(position).getOffice());
                    intent.putExtra("link", adapter.getItem(position).getWebsite());
                    intent.putExtra("state", adapter.getItem(position).getState());
                    intent.putExtra("district", adapter.getItem(position).getDistrict());

                    startActivity(intent);
                }
            });

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText txt = (EditText) findViewById(R.id.editText);

                    if (!txt.getText().toString().equals("")) {

                        if(filter.equals("/getall_reps_byname.php")){
                            feed = getBaseContext().getString(R.string.API)+"/getall_reps_byname.php?name=" + txt.getText().toString() + "&output=json";
                            type = "resp";
                            filter2 ="name";
                        }
                        else if(filter.equals("/getall_mems.php")){
                            feed = getBaseContext().getString(R.string.API) + "/getall_mems.php?output=json&zip=" + txt.getText().toString();
                            type = "resp";
                            filter2 ="zip";
                        }
                        else if(filter.equals("/getall_sens_byname.php")){
                            feed = getBaseContext().getString(R.string.API)+"/getall_sens_byname.php?name=" +  txt.getText().toString()  + "&output=json";
                            type = "sens";
                            filter2 ="name";
                        }

                        else if(filter.equals("/getall_reps_bystate.php")){
                            feed = getBaseContext().getString(R.string.API)+"/getall_reps_bystate.php?state=" + txt.getText().toString() + "&output=json";
                            type = "resp";
                            filter2 ="state";
                        }
                        else if(filter.equals("/getall_sens_bystate.php")){
                            feed = getBaseContext().getString(R.string.API)+"/getall_sens_bystate.php?state=" +  txt.getText().toString()  + "&output=json";
                            type = "sens";
                            filter2 ="state";
                        }
                    }

                    try {
                        per = person.queryBuilder().where().like(filter2, "%"+txt.getText().toString()+"%").and().eq("type",type).query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if(per.size()>0)
                        feed = null;

                    new ReadJsonTask().execute(feed);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        int id = item.getItemId();
        if (id == R.id.rep_by_name) {
            intent = new Intent(MainActivity.this,
                    MainActivity.class);
            intent.putExtra("filter", "/getall_reps_byname.php");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.sens_by_zip) {
            intent = new Intent(MainActivity.this,
                    MainActivity.class);

            intent.putExtra("filter", "/getall_mems.php");
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.rep_by_state) {
            intent = new Intent(MainActivity.this,
                    MainActivity.class);

            intent.putExtra("filter", "/getall_reps_bystate.php");

            startActivity(intent);

        } else if (id == R.id.sens_by_state) {
            intent = new Intent(MainActivity.this,
                    MainActivity.class);

            intent.putExtra("filter", "/getall_sens_bystate.php");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.sens_by_name) {
            intent = new Intent(MainActivity.this,
                    MainActivity.class);

            intent.putExtra("filter", "/getall_sens_byname.php");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.sens_by_name) {
            intent = new Intent(MainActivity.this,
                    MainActivity.class);

            intent.putExtra("filter", "/getall_sens_byname.php");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else {
            item.setChecked(true);
        }

         finish();
        return super.onOptionsItemSelected(item);
    }

    private class ReadJsonTask extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... arg) {
            try {
                if(feed!=null) {
                    adapter = new RepresentativeAdapter(getBaseContext(),
                        R.layout.list_main,utils.getData(arg[0],dtoFactory,type));
                } else {
                    adapter = new RepresentativeAdapter(getBaseContext(),
                            R.layout.list_main,per);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            list.setAdapter(adapter);
            if (adapter.isEmpty())
                findViewById(R.id.empty).setVisibility(View.VISIBLE);
            else
                findViewById(R.id.empty).setVisibility(View.GONE);

            progressBar.setVisibility(View.GONE);
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
