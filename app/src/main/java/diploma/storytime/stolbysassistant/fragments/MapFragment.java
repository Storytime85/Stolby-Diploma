package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.simple.parser.ParseException;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import diploma.storytime.stolbysassistant.BuildConfig;
import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.Friend;
import diploma.storytime.stolbysassistant.utils.ReadJSON;
import diploma.storytime.stolbysassistant.utils.maputils.AlpineHat;
import diploma.storytime.stolbysassistant.utils.maputils.Pillar;
import diploma.storytime.stolbysassistant.views.MainActivity;


public class MapFragment extends Fragment {
    private MainActivity activity;

    private MapView mapView;
    private IMapController mapController;
    private MyLocationNewOverlay locationOverlay;
    private ArrayList<Pillar> pillars;

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
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        Configuration.getInstance().load(activity, PreferenceManager.getDefaultSharedPreferences(activity));
}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.pillars = activity.getPillars();

        mapView = activity.findViewById(R.id.mapView);

        XYTileSource tileSource = new XYTileSource("Stolby", 11, 18,
                256, ".jpg", new String[]{
                "http://otile1.mqcdn.com/tiles/1.0.0/map/",
                "http://otile2.mqcdn.com/tiles/1.0.0/map/",
                "http://otile3.mqcdn.com/tiles/1.0.0/map/",
                "http://otile4.mqcdn.com/tiles/1.0.0/map/"});
        mapView.setTileSource(tileSource);
        mapView.setUseDataConnection(false);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();

        mapView.scrollTo(55, 92);
        mapController.setZoom(11.0);

        createPolyline(R.raw.inner_line, Color.GREEN);
        createPolyline(R.raw.middle_line, Color.YELLOW);
        createPolyline(R.raw.outer_line, Color.RED);
        createRailroadPolylines(Color.BLACK);

        ScaleBarOverlay scaleBar = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(scaleBar);

        locationOverlay = new MyLocationNewOverlay(mapView);
        locationOverlay.enableFollowLocation();
        locationOverlay.enableMyLocation();

        mapView.getOverlays().add(locationOverlay);
        addAlpineHats();
        addPillarsOnMap();
        if (activity.getUser() != null) {
            addFriendsOnMap();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
        mapView.scrollTo(55, 92);
        mapController.setZoom(11.0);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void createPolyline(int line_id, int color) {
        ArrayList<GeoPoint> line = new ArrayList<>();
        try {
            line = ReadJSON.readCoordinates(activity, line_id);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Polyline polyline = new Polyline();
        polyline.setPoints(line);
        polyline.setColor(color);
        mapView.getOverlayManager().add(polyline);
    }

    private void createRailroadPolylines(int color) {
        ArrayList<Polyline> polylines = new ArrayList<>();
        try {
            polylines = ReadJSON.readRailroads(activity, R.raw.railroad);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Polyline poly : polylines) {
            poly.setColor(color);
            mapView.getOverlayManager().add(poly);
        }
    }

    private void addAlpineHats() {
        List<IGeoPoint> points = new ArrayList<>();
        ArrayList<AlpineHat> alpines = null;
        try {
            alpines = ReadJSON.readMisc(activity, R.raw.misc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (AlpineHat ap : alpines) {
            points.add(new LabelledGeoPoint(ap.getCoordinates()[0], ap.getCoordinates()[1],
                    ap.getNames()[0]));
        }

        SimplePointTheme pt = new SimplePointTheme(points, true);

        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#0000ff"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MEDIUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(false).setCellSize(15).setTextStyle(textStyle);

        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

        mapView.getOverlays().add(sfpo);
    }

    private void addPillarsOnMap() {
        List<IGeoPoint> points = new ArrayList<>();
        String string = Locale.getDefault().getDisplayLanguage();
        int i;
        if (string.equals("русский")) {
            i = 0;
        } else {
            i = 1;
        }
        for (Pillar pillar : pillars) {
            points.add(new LabelledGeoPoint(pillar.getCoordinates()[0], pillar.getCoordinates()[1],
                    pillar.getNames()[i]));
        }

        SimplePointTheme pt = new SimplePointTheme(points, true);

        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#A0522D"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MEDIUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);

        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

        sfpo.setOnClickListener((adapter, point) -> {
            FragmentChanger.changeFragment(new OnePillarFragment(point), activity);
        });

        mapView.getOverlays().add(sfpo);
    }

    //TODO: обновление местоположения, друзья
    private boolean checkCoordinates() {
        return (activity.getLongitude() > 55.703 && activity.getLongitude() < 55.978)
                && (activity.getLatitude() > 92.621 && activity.getLongitude() < 93.354);
    }

    private void addFriendsOnMap() {

        List<IGeoPoint> points = new ArrayList<>();
        ArrayList<Friend> alpines = new ArrayList<>();
        alpines.add(new Friend("Max", true, 55.728, 92.999));
        alpines.add(new Friend("Елисей", false, 55.778, 92.95));
        alpines.add(new Friend("Elena", true, 55.758, 92.811));
        alpines.add(new Friend("Василий", true, 55.838, 92.745));
        for (Friend f : alpines) {
            if (f.isStatus()) {
                points.add(new LabelledGeoPoint(f.getLatitude(), f.getLongitude(), f.getName()));
            }
        }
        SimplePointTheme pt = new SimplePointTheme(points, true);

        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#000000"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MEDIUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(false).setCellSize(15).setTextStyle(textStyle);

        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

        mapView.getOverlays().add(sfpo);
    }
}
