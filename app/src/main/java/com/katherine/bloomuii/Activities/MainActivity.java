package com.katherine.bloomuii.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.katherine.bloomuii.Fragments.AchievementFragment;
import com.katherine.bloomuii.Fragments.DiaryFragment;
import com.katherine.bloomuii.Fragments.HomeFragment;
import com.katherine.bloomuii.Fragments.ProfileFragment;
import com.katherine.bloomuii.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //chip navigation
    private ChipNavigationBar chipNavigationBar;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //chip navigation
        chipNavigationBar = (ChipNavigationBar)findViewById(R.id.bottom_nav);
        chipNavigationBar.setOnItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    //chip navigation
    private ChipNavigationBar.OnItemSelectedListener bottomNavMethod = new ChipNavigationBar.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int i) {
            Fragment fragment = null;

            switch (i) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_diary:
                    fragment = new DiaryFragment();
                    break;
                case R.id.nav_achiv:
                    fragment = new AchievementFragment();
                    break;
                case R.id.nav_prof:
                    fragment = new ProfileFragment();
                    break;

            }
            if (fragment != null){
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }else{
                Log.e(TAG, "Error in creating fragment" );
            }
        }
    };

}
