package com.example.library;

public class User {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String gender;
    private String role;

    // Firebase için boş yapıcı
    public User() {}

    public User(String name, String surname, String email, String phone, String gender, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
    }

    // Getter'lar
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }
}
