package net.kboss.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Scan on 2016-02-02.
 */
public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;

    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        UUID uuid = (UUID) getIntent().getSerializableExtra(CrimeFragment.exputKey);
        //设置当前显示项
        for (int i =0; i<mCrimes.size();i++){
            if (mCrimes.get(i).getmId().equals(uuid)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        //mViewPager.setOnPageChangeListener();
    }

    public static void createNewActivity(Context context,UUID uuid){
        Intent intent = new Intent(context,CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.exputKey,uuid);
        context.startActivity(intent);
    }
}
