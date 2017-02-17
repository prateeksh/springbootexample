package com.example.prateek.springbootexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
    String image;
    TextView name;
    ImageView imageView;
    TextView occ;
    TextView comp;
    TextView ph;
    TextView goog;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_detail, container, false);

        name = (TextView) view.findViewById(R.id.name);
        imageView = (ImageView) view.findViewById(R.id.dispImage);
        occ = (TextView) view.findViewById(R.id.occupation);
        ph = (TextView) view.findViewById(R.id.phone);
        comp = (TextView) view.findViewById(R.id.company);
        goog = (TextView) view.findViewById(R.id.google);

        linearLayout = (LinearLayout) view.findViewById(R.id.content_add_detail);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://powerful-plateau-57637.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserService service = retrofit.create(UserService.class);

        Call<List<User>> createCall = service.all();
        createCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.v("RESPONSE",response.toString());
                Log.v("RESPONSE 2",response.body().toString());

                for(User user: response.body()) {
                    name.setText(user.name);
                    image = user.image;
                    occ.setText(user.occupation);
                    comp.setText(user.company);
                    ph.setText(user.phone);
                    goog.setText(user.google);
                }
                Glide.with(getActivity()).load(image).into(imageView);
                Log.v("IMG", image);
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


