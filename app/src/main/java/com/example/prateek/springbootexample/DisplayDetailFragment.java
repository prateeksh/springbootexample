package com.example.prateek.springbootexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayDetailFragment extends Fragment {

    public DisplayDetailFragment() {
    }

    View view;

    TextView name;
    TextView occ;
    TextView comp;
    TextView ph;
    TextView wor;
    TextView hang;
    TextView goog;
    TextView fb;
    TextView skp;
    TextView blg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_detail, container, false);
        name = (TextView) view.findViewById(R.id.nam);
        occ = (TextView) view.findViewById(R.id.occ);
        ph = (TextView) view.findViewById(R.id.ph);
        wor = (TextView) view.findViewById(R.id.wk);
        hang = (TextView) view.findViewById(R.id.hang);
        goog = (TextView) view.findViewById(R.id.gog);
        fb = (TextView) view.findViewById(R.id.face);
        skp = (TextView) view.findViewById(R.id.skp);
        blg = (TextView) view.findViewById(R.id.blo);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        new Fetch().execute();
    }

    public class Fetch extends AsyncTask<Void,Void,User> {

        @Override
        protected User doInBackground(Void... params) {
            try {
                final String url = "http://192.168.1.5:8080/user";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                User user = restTemplate.getForObject(url, User.class);
                Log.v("DATA",user.toString());
                return user;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            Log.v("DATA2", user.toString());
            name.setText(user.getName());
            occ.setText(user.getOccupation());
            comp.setText(user.getCompany());
            ph.setText(user.getPhone());
            wor.setText(user.getWork());
            hang.setText(user.getHangout());
            goog.setText(user.getGoogle());
            fb.setText(user.getFacebook());
            skp.setText(user.getSkype());
            blg.setText(user.getBlog());
        }

    }
}
