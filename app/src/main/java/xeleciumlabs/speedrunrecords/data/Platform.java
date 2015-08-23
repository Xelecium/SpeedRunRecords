package xeleciumlabs.speedrunrecords.data;

/**
 * Created by Xelecium on 8/22/2015.
 */
public class Platform {
    String mPlatformId;
    String mPlatformName;
    int mPlatformRelease;

    public String getPlatformId() {
        return mPlatformId;
    }
    public void setPlatformId(String platformId) {
        mPlatformId = platformId;
    }
    public String getPlatformName() {
        return mPlatformName;
    }
    public void setPlatformName(String platformName) {
        mPlatformName = platformName;
    }
    public int getPlatformRelease() {
        return mPlatformRelease;
    }
    public void setPlatformRelease(int platformRelease) {
        mPlatformRelease = platformRelease;
    }

    public Platform() {

    }
}
