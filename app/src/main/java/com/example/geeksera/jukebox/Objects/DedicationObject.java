
package com.example.geeksera.jukebox.Objects;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DedicationObject {

    @SerializedName("CurrentDedicationsResult")
    private List<com.example.geeksera.jukebox.Objects.CurrentDedicationsResult> mCurrentDedicationsResult;

    public List<com.example.geeksera.jukebox.Objects.CurrentDedicationsResult> getCurrentDedicationsResult() {
        return mCurrentDedicationsResult;
    }

    public void setCurrentDedicationsResult(List<com.example.geeksera.jukebox.Objects.CurrentDedicationsResult> CurrentDedicationsResult) {
        mCurrentDedicationsResult = CurrentDedicationsResult;
    }

}
