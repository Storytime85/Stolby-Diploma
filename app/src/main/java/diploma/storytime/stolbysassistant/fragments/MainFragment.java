package diploma.storytime.stolbysassistant.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import diploma.storytime.stolbysassistant.BuildConfig;
import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.recycler.RecyclerAdapter;
import diploma.storytime.stolbysassistant.recycler.RecyclerItem;
import diploma.storytime.stolbysassistant.recycler.RecyclerItemClickListener;
import diploma.storytime.stolbysassistant.views.MainActivity;
import timber.log.Timber;

import static android.support.constraint.Constraints.TAG;

public class MainFragment extends Fragment {
    private MainActivity activity;
    RecyclerAdapter adapter;
    ArrayList<RecyclerItem> items = new ArrayList<>();
    RecyclerView recycler;
    int currentItem;

    public MainFragment(){

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter(activity);
        // This will initialise Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Timber.d("success");

        initializeModels();
        recycler = view.findViewById(R.id.recyclerView);
        if (recycler != null) {
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(activity));
            recycler.setAdapter(adapter);
        }
        adapter.setDataSet(items);
        adapter.setOnItemClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onLongItemClick position: " + position);
            }
        });
    }

    private void initializeModels(){
        //RecyclerItem(int title, int description, Intent javaActivity,
        //                        int imageUrl)
        items.add(new RecyclerItem(
                R.string.map_title,
                R.string.map_description,
                new Intent(activity, MainActivity.class),
                R.drawable.maps_tease));
        items.add(new RecyclerItem(
                R.string.ar_title,
                R.string.ar_description,
                new Intent(activity, MainActivity.class),
                R.drawable.ar_tease));
        items.add(new RecyclerItem(
                R.string.compass_title,
                R.string.compass_description,
                new Intent(activity, MainActivity.class),
                R.drawable.compass_tease));
        items.add(new RecyclerItem(
                R.string.pillars_title,
                R.string.pillars_description,
                new Intent(activity, MainActivity.class),
                R.drawable.pillars_tease));
    }

}