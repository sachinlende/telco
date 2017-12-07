package nz.co.spark.data.model;

public class Person {

    private String id;

    private String lastname;

    private String firstname;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", lastname = "+lastname+", firstname = "+firstname+"]";
    }
}
