package diploma.storytime.stolbysassistant.views;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mapbox.android.core.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import diploma.storytime.stolbysassistant.fragments.CameraFragment;
import diploma.storytime.stolbysassistant.fragments.CompassFragment;
import diploma.storytime.stolbysassistant.fragments.LoginFragment;
import diploma.storytime.stolbysassistant.fragments.MapFragment;
import diploma.storytime.stolbysassistant.fragments.PillarsFragment;
import diploma.storytime.stolbysassistant.fragments.SettingsFragment;
import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.recycler.RecyclerAdapter;
import diploma.storytime.stolbysassistant.recycler.RecyclerItem;
import diploma.storytime.stolbysassistant.recycler.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MapFragment mapFragment = new MapFragment();
    LoginFragment loginActivity = new LoginFragment();
    SettingsFragment settingsActivity = new SettingsFragment();
    CompassFragment compassFragment = new CompassFragment();
    PillarsFragment pillarsFragment = new PillarsFragment();
    CameraFragment cameraFragment = new CameraFragment();

    RecyclerAdapter adapter = new RecyclerAdapter(this);
    ArrayList<RecyclerItem> items = new ArrayList<>();
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeModels();
        recycler = findViewById(R.id.recyclerView);
        adapter.setDataSet(items);
        if (recycler != null) {
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            recycler.setAdapter(adapter);
        }

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_content, cameraFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_map) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_content, mapFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_compass) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_content, compassFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_stolby) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_content, pillarsFragment);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_content, settingsActivity);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_login){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_content, loginActivity);

            fragmentTransaction.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeModels(){
        //RecyclerItem(int title, int description, Intent javaActivity,
        //                        int imageUrl)
        items.add(new RecyclerItem(
                R.string.map_title,
                R.string.map_description,
                new Intent(MainActivity.this, MainActivity.class),
                null));
        items.add(new RecyclerItem(
                R.string.ar_title,
                R.string.ar_description,
                new Intent(MainActivity.this, MainActivity.class),
                null));
        items.add(new RecyclerItem(
                R.string.compass_title,
                R.string.compass_description,
                new Intent(MainActivity.this, MainActivity.class),
                null));
        items.add(new RecyclerItem(
                R.string.pillars_title,
                R.string.pillars_description,
                new Intent(MainActivity.this, MainActivity.class),
                null));
    }

}
