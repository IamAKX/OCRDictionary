package com.akash.applications.ocrdictionary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.akash.applications.ocrdictionary.Fragments.CameraFragment;
import com.akash.applications.ocrdictionary.Fragments.ImageFragment;
import com.akash.applications.ocrdictionary.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_PERMISSION_CODE = 100;
    private final int STORAGE_PERMISSION_CODE = 200;

    Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.camera:
                    //create camera and storage request
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] { android.Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
                    else
                        {
                        fragment = new CameraFragment();
                        swapFragment(fragment);
                    }
                    break;


                case R.id.image:
                    //create storage request
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] { android.Manifest.permission.WRITE_EXTERNAL_STORAGE },STORAGE_PERMISSION_CODE);
                    else {
                        fragment = new ImageFragment();
                        swapFragment(fragment);
                    }
                    break;


                case R.id.search:
                    fragment = new SearchFragment();
                    swapFragment(fragment);
                    break;
            }
            return true;
        }

    };


    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;
        swapFragment(new SearchFragment());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED ))
                {
                    getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container,new CameraFragment())
                                .commitAllowingStateLoss();

                }
                else
                {
                    Toast t = Toast.makeText(context,"Permission denied",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,new ImageFragment())
                            .commitAllowingStateLoss();
                }
                else
                {
                    Toast t = Toast.makeText(context,"Permission denied",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                }
                break;
        }
    }
}
