package net.kboss.criminalintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;

    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//需要接收选项菜单方法回调
        getActivity().setTitle("陋习列表");
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        //ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);
        CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimes);
        setListAdapter(crimeAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
        //Toast.makeText(getActivity(), "选中了：" + c.getmTitle(), Toast.LENGTH_SHORT)
               // .show();
        //Intent intent = new Intent(getActivity(),CrimeActivity.class);
        //startActivity(intent);
       // CrimeActivity.createNewActivity(getActivity(),c.getmId());
        CrimePagerActivity.createNewActivity(getActivity(),c.getmId());
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {//避免重复创建视图，提高效率
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }
            Crime crime = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crime.getmTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dateTextView.setText(simpleDateFormat.format(crime.getmDate()));
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(crime.ismSolved());
            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();//通知进行更新
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getActivity(),"点击新增按钮",Toast.LENGTH_SHORT).show();
        Log.d("CrimeListFragment","点击新增按钮");
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(),CrimePagerActivity.class);
                i.putExtra(CrimeFragment.exputKey,crime.getmId());
                startActivityForResult(i,0);
                return true;
            default:
            return super.onContextItemSelected(item);
        }
    }
}

