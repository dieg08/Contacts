package com.myapps.diegogonzalez.contacts.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.myapps.diegogonzalez.contacts.R;
import com.myapps.diegogonzalez.contacts.database.DBHelper;

import java.util.HashMap;
import java.util.Random;

public class WebViewActivity extends Activity {

    /* Reference to the webview*/
    private WebView mWebView;
    /* Helper to get access to database*/
    private DBHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Getting intent information
        Intent i = getIntent();
        Bundle b = i.getExtras();
        int position = b.getInt("position");

        // initialize webview
        mWebView = (WebView) findViewById(R.id.webView);

        // Enabling javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Setting web interface
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        // Getting list of contacts
        admin = new DBHelper(this);
        HashMap<String, String> contact = admin.getDataById(position);

        // Building html string
        String summary = "<html>\n" +
                "<body>\n" +
                "<style>\n" +
                "    #contact {\n" +
                "    text-align: center\n" +
                "    }\n" +
                "</style>\n" +
                "<div id=\"contact\">\n" +
                "    <h1>"+ contact.get("fullName") + "</h1>\n" +
                "    <h2>Phone: "+ contact.get("phone") +"</h2>\n" +
                "    <h2>Email: "+ contact.get("email") +"</h3>\n" +
                "<input type='button' value='Exit' onClick='Android.endActivity()' />" +
                "<input type='button' value='Get Random Address' onClick='Android.getRandom()' />" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        mWebView.loadData(summary, "text/html", null);
    }

    public class WebAppInterface {

        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Method to close activity and launch list. Uses a thread because in order to change
         * The UI, it has to run in a thread.
         */
        @JavascriptInterface
        public void endActivity() {
            runOnUiThread(new Runnable() {

                public void run() {
                    Intent i = new Intent(mContext, MainActivity.class);
                    finish();
                    startActivity(i);
                }
            });
        }

        /**
         * Method to generate random contact. Uses a thread because in order to change
         * The UI, it has to run in a thread.
         */
        @JavascriptInterface
        public void getRandom() {
            runOnUiThread(new Runnable() {

                public void run() {
                    Random randomGenerator = new Random();
                    int index = randomGenerator.nextInt(admin.getSize());
                    if(index == 0) {
                        index = 1;
                    }
                    Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("position", index);
                    i.putExtras(extras);
                    finish();
                    startActivity(i);
                }
            });
        }
    }
}
