package diploma.storytime.stolbysassistant.views;

//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import diploma.storytime.stolbysassistant.fragments.CameraFragment;
import diploma.storytime.stolbysassistant.fragments.CompassFragment;
import diploma.storytime.stolbysassistant.fragments.LoginFragment;
import diploma.storytime.stolbysassistant.fragments.MainFragment;
import diploma.storytime.stolbysassistant.fragments.MapFragment;
import diploma.storytime.stolbysassistant.fragments.PillarsFragment;
import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //@BindView(R.id.user_email)
    public TextView emailTextView;

    //@BindView(R.id.user_name)
    public TextView userNameTextView;

    //@BindView(R.id.userImageView)
    public ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getEmailTextView() {
        return emailTextView;
    }

    public void setEmailTextView(TextView emailTextView) {
        this.emailTextView = emailTextView;
    }

    public TextView getUserNameTextView() {
        return userNameTextView;
    }

    public void setUserNameTextView(TextView userNameTextView) {
        this.userNameTextView = userNameTextView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        setContentView(R.layout.activity_main);

        emailTextView = (TextView) findViewById(R.id.user_email);
        userNameTextView = findViewById(R.id.user_name);
        imageView = findViewById(R.id.userImageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FragmentChanger.changeFragment(new MainFragment(),this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
        } else if (id == R.id.nav_settings) {

            //changeFragment(settingsFragment);

        } else if (id == R.id.nav_login){
            FragmentChanger.changeFragment(new LoginFragment(),this);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
