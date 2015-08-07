package com.myapps.diegogonzalez.contacts.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.diegogonzalez.contacts.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Diego Gonzalez on 8/6/2015.
 */
public class MyAdapter extends BaseAdapter {

    /* Field used to hold application context*/
    private Context context;

    /* List of Contacts*/
    private ArrayList<HashMap<String, String>> contacts;

    public MyAdapter(Context context, ArrayList<HashMap<String, String>> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Getting a hashmap to populate views
        HashMap<String, String> contact = contacts.get(position);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.contact, parent, false);

            holder = new ViewHolder();

            holder.picture = (ImageView) convertView.findViewById(R.id.defaultImage);
            holder.fullName = (TextView) convertView.findViewById(R.id.fullName);
            holder.phone = (TextView) convertView.findViewById(R.id.phoneNumber);
            holder.email = (TextView) convertView.findViewById(R.id.email2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.picture.setImageResource(R.drawable.phone);
        holder.phone.setText(contact.get("phone"));
        holder.fullName.setText(contact.get("fullName"));
        holder.email.setText(contact.get("email"));

        return convertView;
    }

    /**
     * Method to update list in case of changes
     *
     * @param contacts
     */
    public void updateList(ArrayList<HashMap<String, String>> contacts) {
        this.contacts.clear();
        this.contacts = contacts;
        this.notifyDataSetChanged();
    }

    /**
     * Class to store pointers to views
     */
    public static class ViewHolder {
        ImageView picture;
        TextView fullName;
        TextView phone;
        TextView email;
    }
}
