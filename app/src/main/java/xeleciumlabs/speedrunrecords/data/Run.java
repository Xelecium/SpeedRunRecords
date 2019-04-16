package xeleciumlabs.speedrunrecords.data;

/**
 * Created by Xelecium on 8/22/2015.
 */
public class Run {

    private String mGameTitle;
    private int mRunTime;
    private String mRunUserId;
    private String mVideoLink;

    public String getGameTitle() {
        return mGameTitle;
    }
    public void setGameTitle(String gameTitle) {
        mGameTitle = gameTitle;
    }
    public int getRunTime() {
        return mRunTime;
    }
    public void setRunTime(int runTime) {
        mRunTime = runTime;
    }
    public String getRunUserId() {
        return mRunUserId;
    }
    public void setRunUserId(String runUserId) {
        mRunUserId = runUserId;
    }
    public String getVideoLink() {
        return mVideoLink;
    }
    public void setVideoLink(String VideoLink) {
        mVideoLink = VideoLink;
    }

}
