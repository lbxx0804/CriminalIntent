package net.kboss.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button crime_date;
    private CheckBox crime_solved;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = sdf.format(mCrime.getmDate());
        crime_date = (Button) view.findViewById(R.id.crime_date);
        crime_date.setText(strDate);
        crime_date.setEnabled(false);
        crime_solved = (CheckBox) view.findViewById(R.id.crime_solved);
        crime_solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });
        return view;
    }
}
