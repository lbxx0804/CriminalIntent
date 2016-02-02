package net.kboss.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {



    @Override
    protected Fragment createFragment() {
        return  CrimeFragment.newInstance((UUID) getIntent().getSerializableExtra(CrimeFragment.exputKey));
    }

    public static void createNewActivity(Context context,UUID uuid){
        Intent intent = new Intent(context,CrimeActivity.class);
        intent.putExtra(CrimeFragment.exputKey,uuid);
        context.startActivity(intent);
    }
}
