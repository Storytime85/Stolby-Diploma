package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.recycler.RecyclerAdapter;
import diploma.storytime.stolbysassistant.recycler.RecyclerItem;
import diploma.storytime.stolbysassistant.recycler.RecyclerViewListener;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.views.MainActivity;


public class MainFragment extends Fragment {
    private MainActivity activity;

    private RecyclerAdapter adapter;
    private ArrayList<RecyclerItem> items = new ArrayList<>();
    private RecyclerView recycler;

    private RecyclerViewListener listener = (view, position) -> {
        switch (position){
            case 0:{
                FragmentChanger.changeFragment(new MapFragment(), activity);
                break;
            }
            case 1:{
                FragmentChanger.changeFragment(new CompassFragment(), activity);
                break;
            }
            case 2:{
                FragmentChanger.changeFragment(new PillarsFragment(), activity);
                break;
            }
            default:{

                break;
            }
        }
    };

    public MainFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecyclerAdapter(activity, listener);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initializeModels();
        recycler = view.findViewById(R.id.recyclerView);
        if (recycler != null) {
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(activity));
            recycler.setAdapter(adapter);
        }
        adapter.updateData(items);

    }

    private void initializeModels(){
        items.add(new RecyclerItem(
                R.string.map_title,
                R.string.map_description,
                new Intent(activity, MainActivity.class),
                R.drawable.maps_tease));
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