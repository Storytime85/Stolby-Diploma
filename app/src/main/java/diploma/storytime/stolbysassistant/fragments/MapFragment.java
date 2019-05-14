package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.osmdroid.views.MapView;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class MapFragment extends Fragment {
    private MainActivity activity;

    private MapView mapView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_map, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Mapbox.getInstance(activity, getString(R.string.mapbox_access_token));
}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


//    private ArrayList<LatLng> readText(Context context, int resId) throws IOException, ParseException {
//        InputStream is = context.getResources().openRawResource(resId);
//        JSONParser parser = new JSONParser();
//
//        BufferedReader br= new BufferedReader(new InputStreamReader(is));
//        JSONArray array = (JSONArray) parser.parse(br);
//        ArrayList<LatLng> result = new ArrayList<>();
//        for(Object obj : array){
//            result.add(parseCoordinatesObject((JSONObject)obj));
//        }
//        return result;
//    }
//
//    private static LatLng parseCoordinatesObject(JSONObject object) {
//        JSONArray solution = (JSONArray) object.get("coordinates");
//        double[] array = new double[2];
//        if (solution == null) {
//            throw new AssertionError();
//        }
//        Iterator iterator = solution.iterator();
//        for (int i = 0; i<2;i++) {
//            array[i] = (Double) iterator.next();
//        }
//        return new LatLng(array[0],array[1]);
//    }

    //    private ArrayList<LatLng> innerLine;
//    private ArrayList<LatLng> middleLine;
//    private ArrayList<LatLng> outerLine;

    //        try {
//            innerLine = readText(activity, R.raw.inner_line);
//            middleLine = readText(activity, R.raw.middle_line);
//            outerLine = readText(activity, R.raw.outer_line);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
