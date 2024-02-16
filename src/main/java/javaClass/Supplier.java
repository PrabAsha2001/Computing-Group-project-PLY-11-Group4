package javaClass;

public class Supplier {

    private String contact;
    private String name;

    private String address;

    private String email;

    public Supplier() {
    }

    public Supplier(String contact, String name, String address, String email) {
        this.contact = contact;
        this.name = name;
        this.address = address;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
