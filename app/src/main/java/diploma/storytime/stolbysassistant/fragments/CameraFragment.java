package diploma.storytime.stolbysassistant.fragments;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import diploma.storytime.stolbysassistant.R;

public class CameraFragment extends Fragment {

    public CameraFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_camera, container, false);
    }

}