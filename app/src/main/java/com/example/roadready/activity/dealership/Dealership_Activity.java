package com.example.roadready.activity.dealership;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roadready.R;
import com.example.roadready.databinding.DrawerDealershipActivityBinding;
import com.example.roadready.fragments.dealership.Applicants_Fragment;
import com.example.roadready.fragments.dealership.Approved_Fragment;
import com.example.roadready.fragments.dealership.Banklist_Fragment;
import com.example.roadready.fragments.dealership.DealershipProfile_Fragment;
import com.example.roadready.fragments.dealership.ForApproval_Fragment;
import com.example.roadready.fragments.dealership.MyVehicles_Fragment;
import com.example.roadready.fragments.common.notificationfragments.Notifications_Fragment;
import com.example.roadready.fragments.dealership.RegistrationDocs_Fragment;
import com.google.android.material.navigation.NavigationView;

public class Dealership_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView dealershipNavigationView;
    TextView headerText;
    private final String TAG = "Dealership_Activity"; // declare TAG for each class for debugging purposes using Log.d()
    private DrawerDealershipActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DrawerDealershipActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dealershipNavigationView = findViewById(R.id.dealershipNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.btnOpenSidebar);
        headerText = findViewById(R.id.dealershipTxtHeader);

        buttonDrawerToggle.setOnClickListener(v -> drawerLayout.open());

        replaceFragment(new MyVehicles_Fragment());
        headerText.setText(R.string.text_header_my_vehicles);
        dealershipNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println(item.getItemId());
                int num = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                item.setChecked(true);

                if(num == R.id.navMyVehicles){
                    replaceFragment(new MyVehicles_Fragment());
                    headerText.setText(R.string.text_header_my_vehicles);
                }else if(num == R.id.navForApproval){
                    replaceFragment(new ForApproval_Fragment());
                    headerText.setText(R.string.text_header_for_approval);
                }else if(num == R.id.navApproved){
                    replaceFragment(new Approved_Fragment());
                    headerText.setText(R.string.text_header_approved);
                }else if(num == R.id.navRegistrationProgress){
                    replaceFragment(new Applicants_Fragment());
                    headerText.setText(R.string.text_header_documents_progress);
                }else if(num == R.id.navLTO){
                    replaceFragment(new RegistrationDocs_Fragment());
                    headerText.setText(R.string.text_header_lto);
                }else if(num == R.id.navBank){
                    replaceFragment(new Banklist_Fragment());
                    headerText.setText(R.string.text_header_banklist);
                }else if(num == R.id.navNotifications){
                    replaceFragment(new Notifications_Fragment());
                    headerText.setText(R.string.text_header_notifications);
                }else if(num == R.id.navProfile){
                    replaceFragment(new DealershipProfile_Fragment());
                    headerText.setText(R.string.text_header_profile);
                }

                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}