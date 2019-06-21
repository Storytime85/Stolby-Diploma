package diploma.storytime.stolbysassistant.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.views.MainActivity;


public class CompassFragment extends Fragment implements SensorEventListener {
    private MainActivity activity;

    private ImageView headerImage;
    private float rotateDegree = 0f;
    private SensorManager sensorManager;
    private TextView orientText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_compass, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        headerImage = view.findViewById(R.id.imageViewCompass);
        orientText = view.findViewById(R.id.tvHeading);
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        String degrees;
        if (degree ==360){
            degrees = "0";
        } else {
            degrees = Float.toString(degree);
        }
        orientText.setText(degrees + "°" );

        RotateAnimation rotateAnimation = new RotateAnimation(
                rotateDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);

        headerImage.startAnimation(rotateAnimation);
        rotateDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
