package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class OnePillarFragment extends Fragment {

    private MainActivity activity;
    private ArrayList<Pillar> pillars;

    private TextView pillarTitle, pillarSubtitle, pillarText;
    private int id;

    public OnePillarFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_one_pillar, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
        pillars = activity.getPillars();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        pillarTitle = activity.findViewById(R.id.pillar_title);
        pillarSubtitle = activity.findViewById(R.id.pillar_subtitle);
        pillarText = activity.findViewById(R.id.pillar_text);

        fillTextFields();
    }

    private void fillTextFields() {
        Pillar temp = pillars.get(id);
        pillarTitle.setText(temp.getNames()[0]);
        pillarSubtitle.setText(coordinatesToString(temp.getCoordinates()));
        pillarText.setText(temp.getDescriptions()[0]);

    }

    private String coordinatesToString(double[] coordinates) {
        return coordinates[0] + "," + coordinates[1];

    }
}
