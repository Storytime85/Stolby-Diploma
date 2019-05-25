package diploma.storytime.stolbysassistant.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.Friend;
import diploma.storytime.stolbysassistant.utils.HTTPRequest;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class FriendsFragment extends Fragment {
    private MainActivity activity;

    private Button addButton, copyButton, showButton, refreshButton;
    private ListView friendListView;
    private TextView friendKeyTextView;
    private EditText friendKeyEditText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
//        pillars = activity.getPillars();
//        initializeArrays();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        addButton = activity.findViewById(R.id.add_button);
        copyButton = activity.findViewById(R.id.copy_button);
        showButton = activity.findViewById(R.id.show_button);
        refreshButton = activity.findViewById(R.id.refreshButton);

        friendKeyTextView = activity.findViewById(R.id.friend_key);
        friendKeyEditText = activity.findViewById(R.id.edit_text);

        friendListView = activity.findViewById(R.id.friend_list);

        friendKeyEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    addButton.setEnabled(false);
                } else {
                    addButton.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        copyButton.setOnClickListener(v -> {
            String getstring = friendKeyTextView.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Friend Key", getstring);
            clipboard.setPrimaryClip(clip);
        });
        showButton.setOnClickListener(v -> {
            if (copyButton.getVisibility() == View.GONE || copyButton.getVisibility() == View.INVISIBLE) {
                copyButton.setVisibility(View.VISIBLE);
                friendKeyTextView.setVisibility(View.VISIBLE);
                friendKeyTextView.setText(activity.getUser().getFriendKey());
            } else {
                copyButton.setVisibility(View.INVISIBLE);
                friendKeyTextView.setVisibility(View.INVISIBLE);
            }
        });
        addButton.setOnClickListener(v -> {
            HTTPRequest.addFriend(activity, activity.getUser().getName(),
                    friendKeyEditText.getText().toString());
        });
        refreshButton.setOnClickListener(v -> {
            refreshFrendlist();
        });

        friendKeyTextView.setText(activity.getUser().getFriendKey());

        initializeListView();


        // HTTPRequest.getFriends(activity, activity.getUser());
        // HTTPRequest.getFriendKey(activity, activity.getUser().getName());
//        ListView lvMain = activity.findViewById(R.id.lvMain);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//                R.layout.text_support, names);
//        lvMain.setAdapter(adapter);
//
//        lvMain.setOnItemClickListener((parent, view1, position, id) -> {
//            String string = (String) lvMain.getItemAtPosition(position);
//            int pillarid = 0;
//            for (int i = 0; i < names.length; i++) {
//                if (string.equals(names[i])) {
//                    pillarid = ids[i];
//                }
//            }
//            FragmentChanger.changeFragment(new OnePillarFragment(pillarid), activity);
//        });
    }

    private void initializeListView() {
        //      HTTPRequest.getFriends();

        ArrayAdapter<Friend> adapter = new ArrayAdapter<>(activity,
                R.layout.text_support, activity.getUser().getFriends());
        friendListView.setAdapter(adapter);

//        lvMain.setOnItemClickListener((parent, view1, position, id) -> {
//            String string = (String) lvMain.getItemAtPosition(position);
//            int pillarid = 0;
//            for (int i = 0; i < names.length; i++) {
//                if (string.equals(names[i])) {
//                    pillarid = ids[i];
//                }
//            }
//            FragmentChanger.changeFragment(new OnePillarFragment(pillarid), activity);
//        });
    }

    private void refreshFrendlist() {
        //     HTTPRequest.getFriends();
        //todo:http pojeben na obnovlenije

    }

}
