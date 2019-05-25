package diploma.storytime.stolbysassistant.views;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.fragments.CameraFragment;
import diploma.storytime.stolbysassistant.fragments.CompassFragment;
import diploma.storytime.stolbysassistant.fragments.FriendsFragment;
import diploma.storytime.stolbysassistant.fragments.LoginFragment;
import diploma.storytime.stolbysassistant.fragments.MainFragment;
import diploma.storytime.stolbysassistant.fragments.MapFragment;
import diploma.storytime.stolbysassistant.fragments.PillarsFragment;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.ReadJSON;
import diploma.storytime.stolbysassistant.utils.User;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FusedLocationProviderClient fusedLocationClient;

    private boolean isStarted = true;
    private double[] currentLocation = new double[2];
    private User user;
    private MenuItem logIn, logOut;

    public double getLongitude() {
        return currentLocation[0];
    }

    public double getLatitude() {
        return currentLocation[1];
    }

    public boolean isStarted() {
        return isStarted;
    }
    //@BindView(R.id.user_email)
    public TextView emailTextView;

    //@BindView(R.id.user_name)
    public TextView userNameTextView;

    public ArrayList<Pillar> pillars;

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void refreshPosition() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null && location.getLongitude() != 0 && location.getLatitude() != 0) {
                            currentLocation[1] = location.getLatitude();
                            currentLocation[0] = location.getLongitude();
                        } else {
                            //none
                        }
                    });
        }
    }

    public ArrayList<Pillar> getPillars() {
        return pillars;
    }

    public void setPillars(ArrayList<Pillar> pillars) {
        this.pillars = pillars;
    }

    public TextView getEmailTextView() {
        return emailTextView;
    }

    public void setEmailTextView(TextView emailTextView) {
        this.emailTextView = emailTextView;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        try {
            pillars = ReadJSON.readPillars(this, R.raw.pillars);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setSupportActionBar(toolbar);

        FragmentChanger.createFirstFragment(new MainFragment(), this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_russian) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_main){
            FragmentChanger.changeFragment(new MainFragment(),this);
        }else if (id == R.id.nav_camera) {
            FragmentChanger.changeFragment(new CameraFragment(),this);
        } else if (id == R.id.nav_map) {
            FragmentChanger.changeFragment(new MapFragment(),this);
        } else if (id == R.id.nav_compass) {
            FragmentChanger.changeFragment(new CompassFragment(),this);
        } else if (id == R.id.nav_stolby) {
            FragmentChanger.changeFragment(new PillarsFragment(),this);
        } else if (id == R.id.nav_log_out) {
            emailTextView.setText("");
            userNameTextView.setText("");
            user = new User();
            switchMenuItems(false);
        } else if (id == R.id.nav_login){
            FragmentChanger.changeFragment(new LoginFragment(),this);
        } else if (id == R.id.nav_friends) {
            FragmentChanger.changeFragment(new FriendsFragment(), this);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUserInfo() {
        //i dunno why, but they are switched, don't touch it
        emailTextView = findViewById(R.id.user_name);
        userNameTextView = findViewById(R.id.user_email);
        emailTextView.setText(user.getEmail());
        userNameTextView.setText(user.getName());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        // Get dynamic menu item
        logIn = menu.findItem(R.id.nav_login);
        logOut = menu.findItem(R.id.nav_log_out);
//        logIn.setVisible(!isStarted);
//        logOut.setVisible(isStarted);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        //      getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        // Get dynamic menu item
//        logIn = menu.findItem(R.id.nav_login);
//        logOut = menu.findItem(R.id.nav_log_out);
        return true;
    }

    public void switchMenuItems(boolean switcher) {
        logIn.setVisible(!switcher);
        logOut.setVisible(switcher);
    }
}
