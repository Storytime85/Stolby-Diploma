package diploma.storytime.stolbysassistant.maputils;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mapbox.geojson.FeatureCollection;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Scanner;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.fragments.MapFragment;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class DrawGeoJson extends AsyncTask<Void, Void, FeatureCollection> {

    private WeakReference<MainActivity> weakReference;
    private MainActivity activity;
    private MapFragment mapFragment;
    public DrawGeoJson(MainActivity activity, MapFragment mapFragment) {
        this.weakReference = new WeakReference<>(activity);
        this.activity = activity;
        this.mapFragment = mapFragment;
    }

    @Override
    protected FeatureCollection doInBackground(Void... voids) {
        try {
            MainActivity activity = weakReference.get();
            if (activity != null) {
                InputStream inputStream = activity.getResources().openRawResource(R.raw.get_geojson);
                return FeatureCollection.fromJson(convertStreamToString(inputStream));
            }
        } catch (Exception exception) {
            Log.e("Exception loading ", exception.toString());
        }
        return null;
    }

    private String convertStreamToString(InputStream is) {
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    @Override
    protected void onPostExecute(@Nullable FeatureCollection featureCollection) {
        super.onPostExecute(featureCollection);
        MainActivity activity = weakReference.get();
        if (activity != null && featureCollection != null) {
            mapFragment.drawLines(featureCollection);
        }
    }
}
