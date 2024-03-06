package com.quizapp.user.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@Entity
@Table(schema = "quizapp")
public class Authority implements GrantedAuthority {

    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private UUID id;

    public String authority;
}
