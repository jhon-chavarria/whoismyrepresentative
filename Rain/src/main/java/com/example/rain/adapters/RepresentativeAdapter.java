package com.example.rain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rain.R;
import com.example.rain.entities.Person;

import java.util.List;

public class RepresentativeAdapter extends ArrayAdapter<Person>{

	private Context context;
	private List<Person> representatives;
    private final LayoutInflater mInflater;


    public RepresentativeAdapter(Context context, int textViewResourceId,
                                 List<Person> re) {
		super(context, textViewResourceId, re);
		this.context=context;
        mInflater = LayoutInflater.from(context);

        this.representatives=re;
	}
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Person re = representatives.get(position);

        RepresentativeHolder holder = new RepresentativeHolder();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_main, parent, false);

            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.phone = (TextView)convertView.findViewById(R.id.phone);


            convertView.setTag(holder);
        } else holder = (RepresentativeHolder)convertView.getTag();


        if(re.getName()!=null)
            holder.name.setText(re.getName());

        if(re.getPhone()!=null)
            holder.phone.setText(re.getPhone());

		return convertView;
	}

    public static class RepresentativeHolder {
        TextView name;
        TextView phone;

    }

}
