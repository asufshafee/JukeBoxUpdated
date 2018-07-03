
package com.example.geeksera.jukebox.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NowPlayingResult {

    @SerializedName("A_id")
    private Object mAId;
    @SerializedName("AlbumName")
    private Object mAlbumName;
    @SerializedName("Duration")
    private Object mDuration;
    @SerializedName("Path")
    private Object mPath;
    @SerializedName("Periority")
    private Object mPeriority;
    @SerializedName("Playstatus")
    private Object mPlaystatus;
    @SerializedName("S_id")
    private Object mSId;
    @SerializedName("Singer")
    private Object mSinger;
    @SerializedName("Title")
    private Object mTitle;

    public Object getAId() {
        return mAId;
    }

    public void setAId(Object AId) {
        mAId = AId;
    }

    public Object getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(Object AlbumName) {
        mAlbumName = AlbumName;
    }

    public Object getDuration() {
        return mDuration;
    }

    public void setDuration(Object Duration) {
        mDuration = Duration;
    }

    public Object getPath() {
        return mPath;
    }

    public void setPath(Object Path) {
        mPath = Path;
    }

    public Object getPeriority() {
        return mPeriority;
    }

    public void setPeriority(Object Periority) {
        mPeriority = Periority;
    }

    public Object getPlaystatus() {
        return mPlaystatus;
    }

    public void setPlaystatus(Object Playstatus) {
        mPlaystatus = Playstatus;
    }

    public Object getSId() {
        return mSId;
    }

    public void setSId(Object SId) {
        mSId = SId;
    }

    public Object getSinger() {
        return mSinger;
    }

    public void setSinger(Object Singer) {
        mSinger = Singer;
    }

    public Object getTitle() {
        return mTitle;
    }

    public void setTitle(Object Title) {
        mTitle = Title;
    }

}
