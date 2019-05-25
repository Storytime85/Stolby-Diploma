package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class PillarsFragment extends Fragment {

    private MainActivity activity;
    private ArrayList<Pillar> pillars;
    private String[] names;
    private int[] ids;

    public PillarsFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
        pillars = activity.getPillars();
        initializeArrays();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_pillars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ListView lvMain = activity.findViewById(R.id.lvMain);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                R.layout.text_support, names);
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener((parent, view1, position, id) -> {
            String string = (String) lvMain.getItemAtPosition(position);
            int pillarid = 0;
            for (int i = 0; i < names.length; i++) {
                if (string.equals(names[i])) {
                    pillarid = ids[i];
                }
            }
            FragmentChanger.changeFragment(new OnePillarFragment(pillarid), activity);
        });
    }

    private void initializeArrays() {
        names = new String[pillars.size()];
        ids = new int[pillars.size()];
        String string = Locale.getDefault().getDisplayLanguage();
        int in;
        if (string.equals("русский")) {
            in = 0;
        } else {
            in = 1;
        }
        for (int i = 0; i < pillars.size(); i++) {
            names[i] = pillars.get(i).getNames()[in];
            ids[i] = i;
        }
        sortArrays();
    }

    private void sortArrays() {
        for (int j = names.length - 1; j >= 1; j--) {
            for (int i = 0; i < j; i++) {
                if (names[i].compareTo(names[j]) >= 0)
                    toSwap(i, i + 1);
            }
        }
    }

    private void toSwap(int first, int second) {
        String tempString = names[first];
        names[first] = names[second];
        names[second] = tempString;
        int tempInt = ids[first];
        ids[first] = ids[second];
        ids[second] = tempInt;

    }
}
