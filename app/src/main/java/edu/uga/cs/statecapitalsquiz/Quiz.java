package edu.uga.cs.statecapitalsquiz;

public class Quiz {

    private long   id; // primary key
    private String companyName;
    private String phone;
    private String url;
    private String comments;

    /**
     * This class (a POJO) represents a single quiz, including the id, .
     * The id is -1 if the object has not been persisted in the database yet, and
     * the db table's primary key value, if it has been persisted. Can be changed as neccessary because don't know if this is correct
     */
    public Quiz()
    {
        this.id = -1;
        this.stateAsked = null;
        this.phone = null;
        this.url = null;
        this.comments = null;
    }

    // custom constructor
    public Quiz(String companyName, String phone, String url, String comments ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.companyName = companyName;
        this.phone = phone;
        this.url = url;
        this.comments = comments;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String toString()
    {
        return id + ": " + companyName + " " + phone + " " + url + " " + comments;
    }
}
