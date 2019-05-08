package diploma.storytime.stolbysassistant.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.offline.OfflineManager;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class MapFragment extends Fragment {
    private MainActivity activity;
    private MapView mapView;

    private boolean isEndNotified;
    private ProgressBar progressBar;
    private OfflineManager offlineManager;

    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_map, container, false);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Mapbox.getInstance(activity, getString(R.string.mapbox_access_token));
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
//
//                mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
//                    @Override
//                    public void onStyleLoaded(@NonNull Style style) {
//
//// Set up the OfflineManager
//                        offlineManager = OfflineManager.getInstance(SimpleOfflineMapActivity.this);
//
//// Create a bounding box for the offline region
//                        LatLngBounds latLngBounds = new LatLngBounds.Builder()
//                                .include(new LatLng(55.9656, 92.7049)) // Northeast
//                                .include(new LatLng(55.8751, -119.6815)) // Southwest
//                                .build();
//
//// Define the offline region
//                        OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
//                                style.getUrl(),
//                                latLngBounds,
//                                10,
//                                20,
//                                SimpleOfflineMapActivity.this.getResources().getDisplayMetrics().density);
//
//// Set the metadata
//                        byte[] metadata;
//                        try {
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put(JSON_FIELD_REGION_NAME, "Yosemite National Park");
//                            String json = jsonObject.toString();
//                            metadata = json.getBytes(JSON_CHARSET);
//                        } catch (Exception exception) {
//                            Timber.e("Failed to encode metadata: %s", exception.getMessage());
//                            metadata = null;
//                        }
//
//// Create the region asynchronously
//                        offlineManager.createOfflineRegion(
//                                definition,
//                                metadata,
//                                new OfflineManager.CreateOfflineRegionCallback() {
//                                    @Override
//                                    public void onCreate(OfflineRegion offlineRegion) {
//                                        offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);
//
//// Display the download progress bar
//                                        progressBar = findViewById(R.id.progress_bar);
//                                        startProgress();
//
//// Monitor the download progress using setObserver
//                                        offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
//                                            @Override
//                                            public void onStatusChanged(OfflineRegionStatus status) {
//
//// Calculate the download percentage and update the progress bar
//                                                double percentage = status.getRequiredResourceCount() >= 0
//                                                        ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
//                                                        0.0;
//
//                                                if (status.isComplete()) {
//// Download complete
//                                                    endProgress(getString(R.string.simple_offline_end_progress_success));
//                                                } else if (status.isRequiredResourceCountPrecise()) {
//// Switch to determinate state
//                                                    setPercentage((int) Math.round(percentage));
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onError(OfflineRegionError error) {
//// If an error occurs, print to logcat
//                                                Timber.e("onError reason: %s", error.getReason());
//                                                Timber.e("onError message: %s", error.getMessage());
//                                            }
//
//                                            @Override
//                                            public void mapboxTileCountLimitExceeded(long limit) {
//// Notify if offline region exceeds maximum tile count
//                                                Timber.e("Mapbox tile count limit exceeded: %s", limit);
//                                            }
//                                        });
//                                    }
//
//                                    @Override
//                                    public void onError(String error) {
//                                        Timber.e("Error: %s", error);
//                                    }
//                                });
//                    }
//                });
//            }
//        });
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
