package com.example.prateek.springbootexample;

import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.prateek.springbootexample.R.id.phone;

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
    User mUser;
    String mName;
    String mImage;
    String mOccup;
    String mComp;
    String mPh;
    String mGoogle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_detail, container, false);

        name = (TextView) view.findViewById(R.id.name);
        imageView = (ImageView) view.findViewById(R.id.dispImage);
        occ = (TextView) view.findViewById(R.id.occupation);
        ph = (TextView) view.findViewById(phone);
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
                    mName = user.name;
                    mImage = user.image;
                    mOccup = user.occupation;
                    mComp = user.company;
                    mPh = user.phone;
                    mGoogle = user.google;
                    name.setText(mName);
                    image = user.image;
                    occ.setText(mOccup);
                    comp.setText(mComp);
                    ph.setText(mPh);
                    goog.setText(mGoogle);
                }
                Glide.with(getActivity()).load(image).into(imageView);
                Log.v("IMG", image);
                storeVcf();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    private void storeVcf() {
        checkExternalMedia();
        File root = Environment.getExternalStorageDirectory();
        Log.v("Log","\nExternal file system root: " + root);

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File(root.getAbsolutePath() + "/BusinessCard");
        dir.mkdirs();
        File file = new File(dir, "userData.txt");

        try {
            Log.v("NAME", mName);
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("Name" + mName);
            pw.println("Image" + mImage);
            pw.println("Occupatoin" + mOccup);
            pw.println("Company" + mComp);
            pw.println("Phone" + mPh);
            pw.println("Google" + mGoogle);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("LOG", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("LOG","\n\nFile written to " + file);
    }

    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        Log.v("LOG","\n\nExternal Media: readable="
                +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
    }
}


