package com.quizapp.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizapp.user.dto.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "quizapp",name="user")
@Builder
@Data
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
}
