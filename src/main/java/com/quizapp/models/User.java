package com.quizapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizapp.dto.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "quizapp",name="user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;
    @JsonProperty
    @Column(name = "user_name")
    private String userName;
    @JsonProperty
    @Column(name = "email")
    private String email;
    @JsonProperty
    @Column(name = "user_image")
    private String userImage;
    @JsonProperty
    @Column(name = "provider_id")
    private String providerId;
    @JsonProperty
    @Column(name = "user_role")
    private String userRole;
    @JsonProperty
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JsonProperty
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
