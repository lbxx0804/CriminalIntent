package net.kboss.criminalintent;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Scan on 2016-02-01.
 */
public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";

    public final static String exputKey = "crimeID";
    private final static String DIALOG_DATE = "date";
    private final static int REQUEST_DATE = 0;
    private static  final  int REQUEST_PHOTO = 1;
    private Crime mCrime;
    private EditText mTitleField;
    private Button crime_date;
    private CheckBox crime_solved;

    private ImageButton crime_imageButton;

    private ImageView imageViewPhoto;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Intent intent = getActivity().getIntent();
        UUID cid = (UUID) getArguments().getSerializable(exputKey);
        // UUID cid = (UUID) intent.getSerializableExtra(CrimeActivity.exputKey);
        if (cid != null) {
            mCrime = CrimeLab.get(getActivity()).getCrimeByUUID(cid);
        } else {
            mCrime = new Crime();
        }
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//启用向上按钮
            if (NavUtils.getParentActivityName(getActivity()) != null) {//避免误导用户
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
                Activity activity = getActivity();

                ActionBar actionBar = activity.getActionBar();
                Log.d("CrimeFragment", String.valueOf(actionBar == null));
            }
        }
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
        //crime_date.setText(strDate);
        updateDate();
        //crime_date.setEnabled(false);
        crime_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);//将Dialog的目标设置为本碎片，进行关联
                dialog.show(fragmentManager, DIALOG_DATE);
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

        crime_imageButton = (ImageButton)view.findViewById(R.id.crime_imageButton);
        //检查是否支持摄像机
        PackageManager packageManager = getActivity().getPackageManager();
        boolean hasACamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                ||packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)||
                Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD || Camera.getNumberOfCameras()>0;
        if(!hasACamera){
            crime_imageButton.setEnabled(false);
        }
        crime_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CrimeCameraActivity.class);
                //startActivity(i);
                startActivityForResult(i,REQUEST_PHOTO);
            }
        });

        imageViewPhoto = (ImageView)view.findViewById(R.id.crimeImageView);

        return view;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(exputKey, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if ((requestCode == REQUEST_DATE)) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setmDate(date);
            updateDate();
        }else if (requestCode  == REQUEST_PHOTO){
            String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if(filename != null){
                //Log.i(TAG,filename);
                Photo photo = new Photo(filename);
                mCrime.setPhoto(photo);
                showPhoto();
            }
        }
    }

    public void updateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        crime_date.setText(simpleDateFormat.format(mCrime.getmDate()));
    }

    /**
     * 设置菜单按钮点击回调事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 将图片显示在ImageView上面
     */
    private void showPhoto(){
        Photo photo = mCrime.getPhoto();
        BitmapDrawable bitmapDrawable = null;
        if (photo != null){
            String path = getActivity().getFileStreamPath(photo.getFileName()).getAbsolutePath();
            bitmapDrawable = PictureUtils.getScaledDrawable(getActivity(),path);
            imageViewPhoto.setImageDrawable(bitmapDrawable);
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        showPhoto();//显示照片
    }

    @Override
    public void onStop(){
        super.onStop();
        PictureUtils.cleanImageView(imageViewPhoto);//清理bitmapDrawable
    }
}
