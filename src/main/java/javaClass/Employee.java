package javaClass;

public class Employee {

    private String contact;
    private String name;
    private String password;
    private String address;
    private String type;
    private String email;

    public Employee() {
    }

    public Employee(String contact, String name, String password, String address, String type, String email) {
        this.contact = contact;
        this.name = name;
        this.password = password;
        this.address = address;
        this.type = type;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
