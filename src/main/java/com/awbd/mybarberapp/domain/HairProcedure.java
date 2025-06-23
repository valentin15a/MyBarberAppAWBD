package com.awbd.mybarberapp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
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
