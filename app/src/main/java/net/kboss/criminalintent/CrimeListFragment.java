package net.kboss.criminalintent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("陋习列表");
    }
}

