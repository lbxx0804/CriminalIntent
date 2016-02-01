package net.kboss.criminalintent;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {//执行事务
            Log.d("FragmentActivity","fragment 为 null");
            fragment = new CrimeFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}
