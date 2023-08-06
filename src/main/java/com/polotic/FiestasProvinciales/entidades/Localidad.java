package com.polotic.FiestasProvinciales.entidades;

import lombok.*;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="localidad")

public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String nombre;

    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String informacion;

    private int distancia;
    
    @ManyToOne
    private Provincia provincias;

    @OneToMany(mappedBy = "localidad")
    @JsonManagedReference
    private List<Fiesta> fiesta;

}
