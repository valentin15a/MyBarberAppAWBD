package com.awbd.mybarberapp.domain;

import com.awbd.mybarberapp.services.security.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @Singular
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
}