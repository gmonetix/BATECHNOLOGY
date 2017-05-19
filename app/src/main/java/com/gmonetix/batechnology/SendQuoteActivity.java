package com.gmonetix.batechnology;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmonetix.batechnology.helper.Const;
import com.gmonetix.batechnology.helper.SharedPref;
import com.gmonetix.batechnology.helper.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendQuoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etName, etEmail, etPhone, etMessage;
    private Spinner sServiceType;
    private Button submit;

    private String name, email, phone, message, serviceType;
    private SharedPref sharedPref;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_quote);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SendQuoteActivity.this.setTitle("Send a Quote");

        Utils.applyFontForToolbarTitle(SendQuoteActivity.this);

        if (getIntent().hasExtra(Const.INTENT_SERVICE_TITLE))
            title = getIntent().getExtras().getString(Const.INTENT_SERVICE_TITLE);
        else title = "";

        init();

        etName.setText(sharedPref.getYourName());
        etEmail.setText(sharedPref.getYourEmail());
        etPhone.setText(sharedPref.getYourNumber());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendQuoteActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        sServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                switch (position) {
                    case 0:
                        serviceType = "null";
                        break;
                    case 1:
                        serviceType = getResources().getString(R.string.web_development);
                        break;
                    case 2:
                        serviceType = getResources().getString(R.string.e_commerce_solution);
                        break;
                    case 3:
                        serviceType = getResources().getString(R.string.mobile_app_dev);
                        break;
                    case 4:
                        serviceType = getResources().getString(R.string.seo);
                        break;
                    case 5:
                        serviceType = getResources().getString(R.string.logo_branding);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        });

        List<String> serviceItems = new ArrayList<>();
        serviceItems.add(getResources().getString(R.string.service_type_spinner));
        serviceItems.add(getResources().getString(R.string.web_development));
        serviceItems.add(getResources().getString(R.string.e_commerce_solution));
        serviceItems.add(getResources().getString(R.string.mobile_app_dev));
        serviceItems.add(getResources().getString(R.string.seo));
        serviceItems.add(getResources().getString(R.string.logo_branding));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, serviceItems);
        sServiceType.setAdapter(adapter);

        if (!title.equals("") && title.equals(getResources().getString(R.string.web_development))) {
            sServiceType.setSelection(1);
        } else if (!title.equals("") && title.equals(getResources().getString(R.string.e_commerce_solution))) {
            sServiceType.setSelection(2);
        } else if (!title.equals("") && title.equals(getResources().getString(R.string.mobile_app_dev))) {
            sServiceType.setSelection(3);
        } else if (!title.equals("") && title.equals(getResources().getString(R.string.seo))) {
            sServiceType.setSelection(4);
        } else if (!title.equals("") && title.equals(getResources().getString(R.string.logo_branding))) {
            sServiceType.setSelection(5);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                message = etMessage.getText().toString().trim();
                if (!name.equals("") && !email.equals("") && !phone.equals("") && !message.equals("") && !serviceType.equals("null")) {
                    submitQuote(name,email,phone,message,serviceType);
                } else {
                    Toast.makeText(SendQuoteActivity.this,"Enter the details correctly",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void init() {

        sharedPref = new SharedPref(this);

        etName = (EditText) findViewById(R.id.quote_form_name_et);
        etEmail = (EditText) findViewById(R.id.quote_form_email_et);
        etPhone = (EditText) findViewById(R.id.quote_form_phone_et);
        etMessage = (EditText) findViewById(R.id.quote_form_message_et);
        sServiceType = (Spinner) findViewById(R.id.quote_spinner_service_type);
        submit = (Button) findViewById(R.id.quote_form_submit_btn);

        Utils.setFont(this,etName);
        Utils.setFont(this,etEmail);
        Utils.setFont(this,etPhone);
        Utils.setFont(this,etMessage);
        Utils.setFont(this,submit);

    }

    private void submitQuote(final String name, final String email, final String phone, final String message, final String serviceType) {

        final ProgressDialog dialog = new ProgressDialog(SendQuoteActivity.this);
        dialog.setTitle("Sending...");
        dialog.setMessage("Sending ... Please wait ...");
        dialog.show();
        StringRequest quoteFormRqst = new StringRequest(Request.Method.POST, Const.QUOTE_FORM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                    if (object.getString("code").equals("success")) {
                        Toast.makeText(SendQuoteActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SendQuoteActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(SendQuoteActivity.this,"Error : "+ e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(SendQuoteActivity.this,"Error : "+ error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();

                params.put(Const.NAME,name);
                params.put(Const.EMAIL,email);
                params.put(Const.PHONE,phone);
                params.put(Const.MESSAGE,message);
                params.put(Const.SERVICE_TYPE,serviceType);

                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(SendQuoteActivity.this);
        rq.add(quoteFormRqst);
    }

}
