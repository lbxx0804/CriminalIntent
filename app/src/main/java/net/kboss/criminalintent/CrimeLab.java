package net.kboss.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private ArrayList<Crime> mCrimes;

    private static final String TAG = "CrimeLab";
    private static String FILENAME = "crime.json";

    private CriminalIntentJSONSerializer serializer;

    private CrimeLab(Context appContext) {
        this.mAppContext = appContext;
        //mCrimes = new ArrayList<Crime>();
        serializer = new CriminalIntentJSONSerializer(appContext, FILENAME);
        try {
            mCrimes = serializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "加载失败：", e);
        }
        /*
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setmTitle("陋习#" + i);
            crime.setmSolved(i % 3 == 0);
            mCrimes.add(crime);
        }*/
    }

    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return this.mCrimes;
    }

    public Crime getCrimeByUUID(UUID uuid) {
        Crime crime = null;
        for (Crime c : mCrimes) {
            if (c.getmId().equals(uuid)) {
                crime = c;
                break;
            }
        }
        return crime;
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    /**
     * 移除
     * @param c
     */
    public void removeCrime(Crime c) {
        mCrimes.remove(c);
    }

    public boolean saveCrimes() {
        try {
            serializer.saveCrimes(mCrimes);
            Log.d(TAG, "保存Crimes到文件");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "保存Crimes到文件失败：", e);
            return false;
        }
    }
}
