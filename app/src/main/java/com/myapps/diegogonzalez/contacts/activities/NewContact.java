package com.myapps.diegogonzalez.contacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.myapps.diegogonzalez.contacts.R;
import com.myapps.diegogonzalez.contacts.database.DBHelper;

/**
 * Created by Diego Gonzalez on 7/23/2015.
 */


public class NewContact extends Activity {

    private DBHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        admin = new DBHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addContact(View view) {
        Intent i = new Intent(this, MainActivity.class);
        String[] contact = new String[4];
        TextView fName = (TextView) findViewById(R.id.fName);
        TextView lName = (TextView) findViewById(R.id.lName);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        contact[0] = fName.getText().toString();
        contact[1] = lName.getText().toString();
        contact[2] = phone.getText().toString();
        contact[3] = email.getText().toString();
        admin.insertContact(contact[0], contact[1], contact[2], contact[3]);
        Intent intent = new Intent();
        intent.putExtra("MESSAGE","ok");
        setResult(1, intent);
        finish();
    }
}
