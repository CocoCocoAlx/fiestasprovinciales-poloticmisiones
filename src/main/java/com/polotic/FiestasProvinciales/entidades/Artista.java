package com.polotic.FiestasProvinciales.entidades;

import java.util.Date;
import java.util.*;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

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

public class Artista {

    
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long Id;
  
      @OneToMany(mappedBy = "artista")
      @JsonManagedReference
      private List<Fiesta> fiestas;


     @NotBlank(message = "Campo obligatorio")
      @Size(max = 250, message= "Nombre demasiado largo")
      private String nombre;
  
      @NotBlank(message = "Campo obligatorio")
      @Size(max = 250, message= "Nombre demasiado largo")
      private String descripcion;
  
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      @Temporal(TemporalType.DATE)
      private Date fechaPresentación;
      
      //@DateTimeFormat(pattern = "yyyy-MM-dd")
      //@Temporal(TemporalType.DATE)
      //private Date fechaFin;

      @Basic
      private LocalTime horaPresentacion;
      
      
      private String enlace;
  
      private String foto;
  
      //@OneToMany
      //private Fiesta fiesta;

}

