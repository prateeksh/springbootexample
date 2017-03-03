package com.example.prateek.springbootexample;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecievedFileFragment extends Fragment {

    public RecievedFileFragment() {
    }

    View mView;
    TextView mRecieved;
    ImageView mImg;
    Context context;
    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recieved_file, container, false);

        mRecieved = (TextView) mView.findViewById(R.id.dataRecieved);
        mImg = (ImageView) mView.findViewById(R.id.userImage);
        final File f = new File(Environment.getExternalStorageDirectory() + "/"
                + getContext().getPackageName());



        //Get the text file
        File file = new File(f,"wifip2pshared.txt");
        Log.v("FILE", file.toString());
        //Read text from file
        StringBuilder text = new StringBuilder();
        StringBuilder img = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
                counter++;
                if(counter == 3)
                {
                    img.append(line);
                }
            }
            Log.v("FILE IMG", img.toString());
            Glide.with(getActivity()).load(img.toString()).into(mImg);
            mRecieved.setText(text);
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return mView;
    }
}
