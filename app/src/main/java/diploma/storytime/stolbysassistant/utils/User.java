package diploma.storytime.stolbysassistant.utils;

import java.util.List;

public class User {
    private String name;
    private String email;
    private int friendCount;
    private List<Friend> friends;
    private String friendKey;

    public User(String name, String email, int friendCount, String friendKey) {
        this.name = name;
        this.email = email;
        this.friendCount = friendCount;
        this.friendKey = friendKey;
    }

    public User() {

    }

    //region getters/setters
    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public String getFriendKey() {
        return friendKey;
    }

    public void setFriendKey(String friendKey) {
        this.friendKey = friendKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }
    //endregion

}
