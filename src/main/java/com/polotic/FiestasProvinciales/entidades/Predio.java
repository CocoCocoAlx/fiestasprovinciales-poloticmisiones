package com.polotic.FiestasProvinciales.entidades;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="predio")

public class Predio {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long Id;
  
      @OneToMany(mappedBy = "predio")
      @JsonManagedReference
      private List<Fiesta> fiesta;

      @NotBlank(message = "Campo obligatorio")
      @Size(max = 250, message= "Nombre demasiado largo")
      private String nombre;
      
      private Long capacidad;

      private String ubicacion;

      private String ubicacionUrl;
        
}
