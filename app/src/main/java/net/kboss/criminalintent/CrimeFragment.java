package net.kboss.criminalintent;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeFragment extends Fragment {

    public final static String exputKey = "crimeID";
    private  final  static  String DIALOG_DATE = "date";
    private Crime mCrime;
    private EditText mTitleField;
    private Button crime_date;
    private CheckBox crime_solved;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = getActivity().getIntent();
        UUID cid = (UUID)  getArguments().getSerializable(exputKey);
       // UUID cid = (UUID) intent.getSerializableExtra(CrimeActivity.exputKey);
        if (cid != null) {
            mCrime = CrimeLab.get(getActivity()).getCrimeByUUID(cid);
        } else {
            mCrime = new Crime();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitleField.setText(mCrime.getmTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = sdf.format(mCrime.getmDate());
        crime_date = (Button) view.findViewById(R.id.crime_date);
        crime_date.setText(strDate);
        //crime_date.setEnabled(false);
        crime_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog =  DatePickerFragment.newInstance(mCrime.getmDate());
                dialog.show(fragmentManager,DIALOG_DATE);
            }
        });

        crime_solved = (CheckBox) view.findViewById(R.id.crime_solved);
        crime_solved.setChecked(mCrime.ismSolved());
        crime_solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });
        return view;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(exputKey, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }
}
