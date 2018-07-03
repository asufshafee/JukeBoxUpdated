package com.example.geeksera.jukebox.Objects;

/**
 * Created by Mir Kamran on 5/7/2018.
 */

public class Resturents_Object {

    private GetRestaurantsResult[] GetRestaurantsResult;

    public GetRestaurantsResult[] getGetRestaurantsResult ()
    {
        return GetRestaurantsResult;
    }

    public void setGetRestaurantsResult (GetRestaurantsResult[] GetRestaurantsResult)
    {
        this.GetRestaurantsResult = GetRestaurantsResult;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GetRestaurantsResult = "+GetRestaurantsResult+"]";
    }
}
