package com.example.newswebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dob;
    private String status = "Active";
    private String avatar;
    private String otp;


    //Ràng buộc quan hệ JPA data spring
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleEntity> roles = new HashSet<>();


    //Relationship
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    //Relationship
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Interactions> interactions = new ArrayList<>();

    // Constructor, Getter and Setter


    public UserEntity() {
    }

    public UserEntity(Long id, String fullname, String username, String password, String phone, String email, String address, String gender, Timestamp dob, String status, Set<RoleEntity> roles) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.status = status;
        this.roles = roles;
    }

    public UserEntity(Long id, String fullname, String username, String password, String phone, String email, String address, String gender, Timestamp dob, String status) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.status = status;
    }

    public UserEntity(String name, String username, String email, String phonenumber, String encode) {
        this.fullname = name;
        this.username = username;
        this.email = email;
        this.phone = phonenumber;
        this.password = encode;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
