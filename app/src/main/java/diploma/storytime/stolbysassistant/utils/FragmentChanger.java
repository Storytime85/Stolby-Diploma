package diploma.storytime.stolbysassistant.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class FragmentChanger {


    public static void changeFragment(Fragment fragment, MainActivity activity){
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_content, fragment);

        fragmentTransaction.commit();
    }


}
