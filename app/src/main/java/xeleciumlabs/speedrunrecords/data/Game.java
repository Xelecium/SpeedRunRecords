package xeleciumlabs.speedrunrecords.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xelecium on 8/19/2015.
 */
public class Game implements Parcelable {
    String mGameId;
    String mGameName;


    String mGameWebLink;
    int mGameRelease;
    String mGamePlatform;
//    Uri mGameCover;

    //Getters & Setters

    public String getGameId() {
        return mGameId;
    }
    public void setGameId(String mGameId) {
        this.mGameId = mGameId;
    }
    public String getGameName() {
        return mGameName;
    }
    public void setGameName(String mGameName) {
        this.mGameName = mGameName;
    }
    public String getGameWebLink() {
        return mGameWebLink;
    }
    public void setGameWebLink(String gameWebLink) {
        mGameWebLink = gameWebLink;
    }
    public int getGameRelease() {
        return mGameRelease;
    }
    public void setGameRelease(int mGameRelease) {
        this.mGameRelease = mGameRelease;
    }
    public String getGamePlatform() {
        return mGamePlatform;
    }
    public void setGamePlatform(String mGamePlatform) {
        this.mGamePlatform = mGamePlatform;
    }


    public Game() {

    }

    protected Game(Parcel in) {
        mGameId = in.readString();
        mGameName = in.readString();
//        mGameWebLink
        mGameRelease = in.readInt();
        mGamePlatform = in.readString();
//        mGameCover
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGameId);
        dest.writeString(mGameName);
//        mGameWebLink
        dest.writeInt(mGameRelease);
        dest.writeString(mGamePlatform);
//        mGameCover
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
