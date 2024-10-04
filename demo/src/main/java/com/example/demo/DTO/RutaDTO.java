package com.example.demo.DTO;

import com.example.demo.modelo.Estacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class RutaDTO {
    private Long id;

    private String codigo;

    private List<EstacionDTO> estaciones;
}