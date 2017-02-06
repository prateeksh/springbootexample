package com.example.prateek.springbootexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    TextView goog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_detail, container, false);

        name = (TextView) view.findViewById(R.id.name);
        occ = (TextView) view.findViewById(R.id.occupation);
        ph = (TextView) view.findViewById(R.id.phone);
        comp = (TextView) view.findViewById(R.id.company);
        goog = (TextView) view.findViewById(R.id.google);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://shielded-citadel-52821.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserService service = retrofit.create(UserService.class);
        //final User user = new User();
        Call<List<User>> createCall = service.all();
        createCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                User user = (User)response.body();
                name.setText(user.getName());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
