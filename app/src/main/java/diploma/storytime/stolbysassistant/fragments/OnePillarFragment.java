package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class OnePillarFragment extends Fragment {

    private MainActivity activity;
    private ArrayList<Pillar> pillars;

    private TextView pillarTitle, pillarSubtitle, pillarText;
    private ImageView pillarImage;

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
        pillarImage = activity.findViewById(R.id.pillar_image);
        fillTextFields();
    }

    private void fillTextFields() {
        Resources resources = activity.getResources();

        String string = Locale.getDefault().getDisplayLanguage();
        int i;
        if (string.equals("русский")) {
            i = 0;
        } else {
            i = 1;
        }
        Pillar temp = pillars.get(id);
        pillarTitle.setText(temp.getNames()[i]);
        pillarSubtitle.setText(coordinatesToString(temp.getCoordinates()));
        pillarText.setText(temp.getDescriptions()[i]);
        if (!temp.getImage().equals("")) {
            final int resourceId = resources.getIdentifier(temp.getImage(), "drawable", activity.getPackageName());

            // get drawable by resource id
            Drawable drawable = resources.getDrawable(resourceId);
            pillarImage.setImageDrawable(drawable);
        }
        // get bitmap by resource id
        //Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resourceId);
    }

    private String coordinatesToString(double[] coordinates) {
        return coordinates[0] + ", " + coordinates[1];

    }
}
