package com.example.demo.Models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "measurements")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "value")
    @NotNull(message="Значение не должно быть пустым")
    @DecimalMin(value = "-100.0", message = "Значение не менее -100")
    @DecimalMax(value = "100.0", message = "Значение не более 100")
    float value;

    @Column(name = "raining")
    @NotNull(message="Значение не должно быть пустым")
    boolean raining;

    @Column(name = "time")
    LocalDateTime time;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    Sensor sensor;
}
