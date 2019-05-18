package diploma.storytime.stolbysassistant.utils;


import androidx.fragment.app.Fragment;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class FragmentChanger {


    public static void changeFragment(Fragment fragment, MainActivity activity){
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack("fragment")
                .commit();

    }

    public static void createFirstFragment(Fragment fragment, MainActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, fragment)
                .commit();
    }
}
