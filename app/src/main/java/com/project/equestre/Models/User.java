package com.project.equestre.Models;

public class User {

    private long id_user;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String role;

    private String abonnement;
    private String reservation;

    public User() {

    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = abonnement;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public User(String id_user, String  email, String password, String username, String name , String phone, String role,
                String abonnement, String reservation) {
        this.id_user = Long.parseLong(id_user);
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.abonnement = abonnement;
        this.reservation = reservation;
    }
    public User(Long id_user, String email, String password, String username, String name, String phone, String role,
                String abonnement, String reservation) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.abonnement = abonnement;
        this.reservation = reservation;
    }
    public User(User u){
        this.id_user = u.id_user;
        this.username = u.username;
        this.password = u.password;
        this.name =u.name;
        this.email = u.email;
        this.phone = u.phone;
        this.role = u.role;
        this.abonnement = u.abonnement;
        this.reservation = u.reservation;
    }







    @Override
    public String toString() {
        return "{" + "id_user='" + id_user + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\''
                + ", email='" + email + '\'' +", phone='" + phone + '\'' +", role='" + role + '\''
                +", abonnement='" + abonnement + '\''  +", reservation='" + reservation + '\'' + '}';
    }



}