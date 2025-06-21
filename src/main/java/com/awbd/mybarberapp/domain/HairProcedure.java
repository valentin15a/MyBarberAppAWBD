package com.awbd.mybarberapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "hair_procedure")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HairProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "procedure")
    private Set<BarberProcedure> barberProcedures;
}
