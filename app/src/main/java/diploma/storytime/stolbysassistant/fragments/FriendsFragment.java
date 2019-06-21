package diploma.storytime.stolbysassistant.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.Friend;
import diploma.storytime.stolbysassistant.utils.HTTPRequest;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class FriendsFragment extends Fragment {
    private MainActivity activity;

    private Button addButton, copyButton, showButton, refreshButton, deleteButton;
    private ListView friendListView;
    private TextView friendKeyTextView;
    private EditText friendKeyEditText;
    private int currentFriend;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initializeViews();
        initializeListeners();

        friendKeyTextView.setText(activity.getUser().getFriendKey());

        refreshFriendList();
    }

    private void initializeViews() {
        addButton = activity.findViewById(R.id.add_button);
        copyButton = activity.findViewById(R.id.copy_button);
        showButton = activity.findViewById(R.id.show_button);
        refreshButton = activity.findViewById(R.id.refreshButton);
        deleteButton = activity.findViewById(R.id.delete_button);

        friendKeyTextView = activity.findViewById(R.id.friend_key);
        friendKeyEditText = activity.findViewById(R.id.edit_text);

        friendListView = activity.findViewById(R.id.friend_list);
    }

    private void initializeListeners() {
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
            String getString = friendKeyTextView.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Friend Key", getString);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
            }
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
            refreshFriendList();
        });

        refreshButton.setOnClickListener(v -> {
            refreshFriendList();
        });

        deleteButton.setOnClickListener(v -> {
            HTTPRequest.deleteFriend(activity, activity.getUser(),
                    activity.getUser().getFriends().get(currentFriend).getName());
            refreshFriendList();
        });
    }

    private void initializeListView() {
        ArrayList<String> strings = new ArrayList<>();
        for (Friend f : activity.getUser().getFriends()) {
            strings.add(f.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.layout_friend_list, strings) {
            @Override
            @NonNull
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (activity.getUser().getFriends().get(position).isStatus()) {
                    ((TextView) view.findViewById(R.id.friend_name)).setTextColor(Color.GREEN);
                } else {
                    ((TextView) view.findViewById(R.id.friend_name)).setTextColor(Color.RED);
                }
                return view;
            }
        };

        friendListView.setAdapter(adapter);
        friendListView.setOnItemClickListener((parent, view1, position, id) -> {
            currentFriend = position;
            friendListView.setSelection(position);
            deleteButton.setEnabled(true);
        });
    }

    private void refreshFriendList() {
        HTTPRequest.getFriends(activity, activity.getUser());
        initializeListView();
    }
}
