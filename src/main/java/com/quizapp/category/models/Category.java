package com.quizapp.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "quizapp",name="category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;
    @JsonProperty
    @Column(name = "category_name")
    private String categoryName;
    @JsonProperty
    @Column(name = "description",length = 2048)
    private String description;
    @JsonProperty
    @Column(name = "category_image")
    private String categoryImage;

    @JsonProperty
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JsonProperty
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}