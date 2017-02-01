package com.example.prateek.springbootexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.prateek.springbootexample.R.id.google;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddDetailFragment extends Fragment{

    private EditText mName;
    private EditText mOccupation;
    private EditText mCompany;
    private EditText mPhone;
    private EditText mWork;
    private EditText mHangout;
    private EditText mSkype;
    private EditText mGoogle;
    private EditText mFb;
    private EditText mBlog;

    String name;
    String occ;
    String comp;
    String phone;
    String work;
    String hang;
    String skype;
    String goog;
    String fb;
    String blog;




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
        mWork = (EditText) view.findViewById(R.id.work);
        mHangout = (EditText) view.findViewById(R.id.hangout);
        mSkype = (EditText) view.findViewById(R.id.skype);
        mGoogle = (EditText) view.findViewById(google);
        mFb = (EditText) view.findViewById(R.id.facebook);
        mBlog = (EditText) view.findViewById(R.id.blog);

        Button submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mName.getText().toString();
                occ = mOccupation.getText().toString();
                comp = mCompany.getText().toString();
                phone = mPhone.getText().toString();
                work = mWork.getText().toString();
                hang = mHangout.getText().toString();
                skype = mSkype.getText().toString();
                goog = mGoogle.getText().toString();
                fb = mFb.getText().toString();
                blog = mBlog.getText().toString();

                Toast.makeText(getActivity(), "Values added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
