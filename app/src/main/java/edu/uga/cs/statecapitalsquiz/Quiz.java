package edu.uga.cs.statecapitalsquiz;

public class Quiz {

    private long   id; // primary key
    private String state;
    private String capital;
    private String firstCity;
    private String secondCity;
    private String capitalSince;

    /**
     * This class (a POJO) represents a single quiz, including the id, .
     * The id is -1 if the object has not been persisted in the database yet, and
     * the db table's primary key value, if it has been persisted. Can be changed as neccessary because don't know if this is correct
     */
    public Quiz()
    {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.firstCity = null;
        this.secondCity = null;
        this.capitalSince = null;
    }

    // custom constructor
    public Quiz(String state, String capital, String firstCity, String secondCity, String capitalSince ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.state = state;
        this.capital = capital;
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.capitalSince = capitalSince;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    public String getFirstCity()
    {
        return firstCity;
    }

    public void setFirstCity(String firstCity)
    {
        this.firstCity = firstCity;
    }

    public String getSecondCity()
    {
        return secondCity;
    }

    public void setSecondCity(String secondCity)
    {
        this.secondCity = secondCity;
    }

    public String getCapitalSince() {
        return capitalSince;
    }

    public void setCapitalSince(String capitalSince) {
        this.capitalSince = capitalSince;
    }

    public String toString()
    {
        return id + ": " + state + " " + capital + " " + firstCity + " " + secondCity;
    }
}
