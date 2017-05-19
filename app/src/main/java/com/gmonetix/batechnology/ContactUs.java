package com.gmonetix.batechnology;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class ContactUs extends Fragment {

    private EditText etName, etEmail, etPhone, etMessage;
    private Button submit;
    private TextView tvContactDirectly;

    private String name, email, phone, message;
    private SharedPref sharedPref;

    public ContactUs() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);

        etName = (EditText) view.findViewById(R.id.contact_form_name_et);
        etEmail = (EditText) view.findViewById(R.id.contact_form_email_et);
        etPhone = (EditText) view.findViewById(R.id.contact_form_phone_et);
        etMessage = (EditText) view.findViewById(R.id.contact_form_message_et);
        submit = (Button) view.findViewById(R.id.contact_form_submit_btn);
        tvContactDirectly = (TextView) view.findViewById(R.id.contact_directly_tv);

        Utils.setFont(getActivity(),etName);
        Utils.setFont(getActivity(),etEmail);
        Utils.setFont(getActivity(),etPhone);
        Utils.setFont(getActivity(),etMessage);
        Utils.setFont(getActivity(),submit);
        Utils.setFont(getActivity(),tvContactDirectly);

        sharedPref = new SharedPref(getActivity());

        etName.setText(sharedPref.getYourName());
        etEmail.setText(sharedPref.getYourEmail());
        etPhone.setText(sharedPref.getYourNumber());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                message = etMessage.getText().toString().trim();

                if (!name.equals("") && !email.equals("") && !phone.equals("") && !message.equals("")) {
                    submitContactForm(name,email,phone,message);
                } else {
                    Toast.makeText(getActivity(),"Enter the details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvContactDirectly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Contact Us");
                builder.setMessage("Call US : +91-9840143822\n\nEmail Us : info@batechnology.org\n\nAddress : 367, Thiruvalluvar street, Periyar Nagar, Chennai, India");
                builder.setPositiveButton("OK",null);
                builder.show();
            }
        });

        return view;
    }

    public void submitContactForm(final String name, final String email, final String phone, final String message) {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Sending...");
        dialog.setMessage("Sending ... Please wait ...");
        dialog.show();

        StringRequest contactFormRqst = new StringRequest(Request.Method.POST, Const.CONTACT_FORM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                    if (object.getString("code").equals("success")) {
                        Toast.makeText(getActivity(),object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(),object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(),"Error : "+ e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getActivity(),"Error : "+ error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();

                params.put(Const.NAME,name);
                params.put(Const.EMAIL,email);
                params.put(Const.PHONE,phone);
                params.put(Const.MESSAGE,message);

                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        rq.add(contactFormRqst);
    }

}
