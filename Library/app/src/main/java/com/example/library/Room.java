package com.example.library;

public class Room {
    private String type;
    private int allowed_users; // Artık tek bir int
    private int min_people;
    private int max_people;
    private int total_rooms;
    private int min_duration;
    private int max_duration;

    public Room() {
        // Firebase için boş constructor
    }

    public Room(String type, int allowed_users, int min_people, int max_people, int total_rooms, int min_duration, int max_duration) {
        this.type = type;
        this.allowed_users = allowed_users;
        this.min_people = min_people;
        this.max_people = max_people;
        this.total_rooms = total_rooms;
        this.min_duration = min_duration;
        this.max_duration = max_duration;
    }

    public String getType() {
        return type;
    }

    public int getAllowed_users() {
        return allowed_users;
    }

    public int getMin_people() {
        return min_people;
    }

    public int getMax_people() {
        return max_people;
    }

    public int getTotal_rooms() {
        return total_rooms;
    }

    public int getMin_duration() {
        return min_duration;
    }

    public int getMax_duration() {
        return max_duration;
    }
}
