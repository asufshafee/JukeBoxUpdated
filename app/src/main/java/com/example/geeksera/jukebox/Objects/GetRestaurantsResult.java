package com.example.geeksera.jukebox.Objects;

/**
 * Created by Mir Kamran on 5/7/2018.
 */

public class GetRestaurantsResult {

    private String Name;

    private String Id;

    private String City;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

}
