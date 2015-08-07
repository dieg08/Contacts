package com.myapps.diegogonzalez.contacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.myapps.diegogonzalez.contacts.R;
import com.myapps.diegogonzalez.contacts.adapters.MyAdapter;
import com.myapps.diegogonzalez.contacts.database.DBHelper;

/**
 * Created by Diego Gonzalez on 7/23/2015.
 */

public class MainActivity extends Activity {

    /* Listview that will hold contacts*/
    private ListView mListView;
    /* Database class to grants access to database*/
    private DBHelper admin;
    /* Custom adapter for handling listview data*/
    private  MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Database helper
        admin = new DBHelper(this);

        // Initialize the List view
        mListView = (ListView) findViewById(R.id.contacts);

        // Initialize adapter
        adapter = new MyAdapter(this, admin.getAllCotacts());

        // set adapter and footer
        mListView.setAdapter(adapter);

        // Listen for clicks on the listview to launch webview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                Bundle extras = new Bundle();
                // Adding one to position because the database doesn't start at 0 like a list
                extras.putInt("position", position + 1);
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }

    /**
     * Calls the newcontact activity to add a new contact
     *
     * @param v
     */
    public void addContact(View v) {
        Intent i = new Intent(this, NewContact.class);
        startActivityForResult(i, 1);
    }

    /**
     * refreshes list when activity two is done
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            adapter.updateList(admin.getAllCotacts());
        }
    }
}
