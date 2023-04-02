package com.example.demo.Models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@Entity
@Table(name = "user_list")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class User {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int id;

        @Column(name = "name")
        String name;

        @Column(name = "password")
        String password;

        @Column(name = "role")
        String role;
    }

