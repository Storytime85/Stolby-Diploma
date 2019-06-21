package diploma.storytime.stolbysassistant.views;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.fragments.CompassFragment;
import diploma.storytime.stolbysassistant.fragments.FriendsFragment;
import diploma.storytime.stolbysassistant.fragments.LoginFragment;
import diploma.storytime.stolbysassistant.fragments.MainFragment;
import diploma.storytime.stolbysassistant.fragments.MapFragment;
import diploma.storytime.stolbysassistant.fragments.PillarsFragment;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.HTTPRequest;
import diploma.storytime.stolbysassistant.utils.ReadJSON;
import diploma.storytime.stolbysassistant.utils.User;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private double[] currentLocation = new double[2];
    private User user;
    public TextView emailTextView;
    public TextView userNameTextView;
    public ArrayList<Pillar> pillars;
    private MenuItem logIn, logOut, friends;
    private boolean menuState = true;

    //region getters/setters
    public double getLongitude() {
        return currentLocation[0];
    }

    public double getLatitude() {
        return currentLocation[1];
    }

    public ArrayList<Pillar> getPillars() {
        return pillars;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMenuState() {
        return menuState;
    }

    public void setMenuState(boolean menuState) {
        this.menuState = menuState;
    }

    //endregion

    //region Overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        int id = item.getItemId();
        //TODO: четутпроисходит
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_main){
            FragmentChanger.changeFragment(new MainFragment(),this);
        } else if (id == R.id.nav_map) {
            FragmentChanger.changeFragment(new MapFragment(),this);
        } else if (id == R.id.nav_compass) {
            FragmentChanger.changeFragment(new CompassFragment(),this);
        } else if (id == R.id.nav_stolby) {
            FragmentChanger.changeFragment(new PillarsFragment(),this);
        } else if (id == R.id.nav_log_out) {
            checkLeave();
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        logIn.setVisible(!menuState);
        logOut.setVisible(menuState);
        friends.setVisible(menuState);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        logIn = menu.findItem(R.id.nav_login);
        logOut = menu.findItem(R.id.nav_log_out);
        friends = menu.findItem(R.id.nav_friends);

        return true;
    }
    //endregion

    private void checkLeave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.confirm));
        builder.setMessage(getString(R.string.are_you_sure));

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            HTTPRequest.setOffline(user);
            emailTextView.setText("");
            userNameTextView.setText("");
            user = new User();
            switchMenuItems(false);
            dialog.dismiss();
        });

        builder.setNegativeButton(getString(R.string.no), (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setUserInfo() {
        //i dunno why, but they are switched, don't touch it
        emailTextView = findViewById(R.id.user_name);
        userNameTextView = findViewById(R.id.user_email);
        emailTextView.setText(user.getEmail());
        userNameTextView.setText(user.getName());
    }

    public void switchMenuItems(boolean switcher) {
        invalidateOptionsMenu();

        logIn.setVisible(!switcher);
        logOut.setVisible(switcher);
        friends.setVisible(switcher);
    }
}
