package BusinessLayer.SuppliersModule;

import java.util.List;

public class Contact {
    String poc_name;
    String phone_number;
    String email;
    String city;
    String street;
    int building_number;


    public Contact(String poc_name, String phone_numbers, String email, String city, String street, int building_number)
    {
            this.poc_name = poc_name;
            this.phone_number = phone_numbers;
            this.email = email;
            this.city = city;
            this.street = street;
            this.building_number=building_number;
    }


    public String getPoc_name() {
        return poc_name;
    }

    public void setPoc_name(String poc_name) {
        this.poc_name = poc_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_numbers(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(int building_number) {
        building_number = building_number;
    }


}
