package net.kboss.criminalintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;

    private static final String TAG = "CrimeListFragment";

    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//需要接收选项菜单方法回调
        getActivity().setTitle("陋习列表");
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        //ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);
        CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimes);

        setListAdapter(crimeAdapter);
        setRetainInstance(true);//设置为保留实例
        mSubtitleVisible = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle saveInstanceState){

        //View view = super.onCreateView(inflater,parent,saveInstanceState);
        View view = inflater.inflate(R.layout.fragment_crime_list,parent,false);
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(android.R.id.empty);
        listView.setEmptyView(linearLayout);//设置空试图



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if (mSubtitleVisible){
                getActivity().getActionBar().setSubtitle("显示");

            }
        }

        Button btnAddCrime = (Button) view.findViewById(R.id.btn_addCrime);//设置空数据视图中新增按钮点击事件
        btnAddCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCrime();
            }
        });
        return view;
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
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem menuItem =  menu.findItem(R.id.menu_item_show_subtitle);//要进行同步
        if (mSubtitleVisible){
            menuItem.setTitle("隐藏");
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(getActivity(),"点击新增按钮",Toast.LENGTH_SHORT).show();
        //Log.d("CrimeListFragment","点击新增按钮");
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                addNewCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null){
                    getActivity().getActionBar().setSubtitle("子菜单");
                    mSubtitleVisible = true;
                    item.setTitle("隐藏");
                }else {
                    getActivity().getActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle("显示");
                }

                return  true;
            default:
            return super.onContextItemSelected(item);
        }
    }

    private void addNewCrime(){
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent i = new Intent(getActivity(),CrimePagerActivity.class);
        i.putExtra(CrimeFragment.exputKey,crime.getmId());
        startActivityForResult(i,0);
    }
}

