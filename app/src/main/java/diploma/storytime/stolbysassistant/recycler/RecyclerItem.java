package diploma.storytime.stolbysassistant.recycler;

import android.content.Intent;

public class RecyclerItem {

    private int title;
    private int description;
    private Intent javaActivity;
    private Integer imageUrl;

    public RecyclerItem(int title, int description, Intent javaActivity,
                        Integer imageUrl) {
        this.title = title;
        this.description = description;
        this.javaActivity = javaActivity;
        this.imageUrl = imageUrl;
    }

    //region getters/setters
    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public Intent getJavaActivity() {
        return javaActivity;
    }

    public void setJavaActivity(Intent javaActivity) {
        this.javaActivity = javaActivity;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
    //endregion
}
