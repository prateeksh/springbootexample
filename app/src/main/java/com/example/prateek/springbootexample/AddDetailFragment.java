package com.example.prateek.springbootexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.prateek.springbootexample.R.id.google;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddDetailFragment extends Fragment{

    private EditText mName;
    private EditText mOccupation;
    private EditText mCompany;
    private EditText mPhone;
    private EditText mGoogle;

    private TextView dispname;
    private TextView dispocc;
    private TextView dispcom;
    private TextView dispph;
    private TextView dispgoog;

    String name;
    String occ;
    String comp;
    String phone;
    String goog;

    public AddDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_detail, container, false);
        mName = (EditText) view.findViewById(R.id.name);
        mOccupation = (EditText) view.findViewById(R.id.occupation);
        mCompany = (EditText) view.findViewById(R.id.company);
        mPhone = (EditText) view.findViewById(R.id.phone);
        mGoogle = (EditText) view.findViewById(google);

        dispname = (TextView) view.findViewById(R.id.dispname);
        dispocc = (TextView) view.findViewById(R.id.dispoccupation);
        dispcom = (TextView) view.findViewById(R.id.dispcompany);
        dispph = (TextView) view.findViewById(R.id.dispphone);
        dispgoog = (TextView) view.findViewById(R.id.dispgoogle);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cryptic-savannah-42247.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final UserService service = retrofit.create(UserService.class);

        Button submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setName(mName.getText().toString());

                Call<User> createCall = service.update(user);
                createCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User newUser = response.body();
                        //Log.v("Response", response.toString());
                        if(response.body() == null){
                            try {
                                Log.v("Null","no response"+ response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.v("DATA", response.body().toString());
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
               // Toast.makeText(getActivity(), "Values added", Toast.LENGTH_SHORT).show();
            }
        });
        Button viewData = (Button) view.findViewById(R.id.disp);
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<User>> createCall = service.all();
                createCall.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        Log.v("RESPONSE",response.toString());
                        Log.v("RESPONSE 2",response.body().toString());
                        User user = (User) response.body();
                        dispname.setText(user.name);
                        dispocc.setText(user.occupation);
                     /*   dispcom.setText(user.company);
                        dispph.setText(user.phone);*/
                        dispgoog.setText(user.google);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

    public void getImage(){

    }
}
