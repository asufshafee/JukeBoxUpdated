
package com.example.geeksera.jukebox.Objects;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NowPlayingObject {

    @SerializedName("NowPlayingResult")
    private com.example.geeksera.jukebox.Objects.NowPlayingResult mNowPlayingResult;

    public com.example.geeksera.jukebox.Objects.NowPlayingResult getNowPlayingResult() {
        return mNowPlayingResult;
    }

    public void setNowPlayingResult(com.example.geeksera.jukebox.Objects.NowPlayingResult NowPlayingResult) {
        mNowPlayingResult = NowPlayingResult;
    }

}
