package org.example;

public class User {
    public int id;
    public String name;
    public String email;
    public String gender;
    public String status;


    //default constructor - Jackson (used internally by RestAssured) requires
    public User() {
    }

    public User(String name, String email, String gender, String status){
            //this.id = id;
            this.name = name;
            this.email = email;
            this.gender = gender;
            this.status = status;
        }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
