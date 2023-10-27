package com.group.Gestion.src.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String authority;
    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Role(String authority){
        this.authority=authority;
    }
}
