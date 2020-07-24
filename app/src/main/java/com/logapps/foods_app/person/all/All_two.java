package com.logapps.foods_app.person.all;

public class All_two {

    private String name ;
    private String details ;
    private String address ;
    private String phone ;
    private String image ;

    public All_two(String name, String details, String address, String phone, String image) {
        this.name = name;
        this.details = details;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "All_two{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
