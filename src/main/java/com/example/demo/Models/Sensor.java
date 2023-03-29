package com.example.demo.Models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "sensor")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    @NotEmpty(message="Имя сенсора не должно быть пустым")
    @Size(min = 2, max = 30, message = "Длина имени должна лежать в пределах от 2 до 30")
    String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "sensor")
    List<Measurement> measurements;
}
