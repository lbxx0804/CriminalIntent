package net.kboss.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Scan on 2016-02-01.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
}
