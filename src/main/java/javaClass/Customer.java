package javaClass;

public class Customer {

    private String contact;
    private String name;

    private String email;

    public Customer(String contact, String name,String email) {
        this.contact = contact;
        this.name = name;
        this.email=email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
