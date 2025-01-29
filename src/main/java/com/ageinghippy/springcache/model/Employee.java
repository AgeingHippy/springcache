package com.ageinghippy.springcache.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 7649841808332743116L;

    @Id
    @GeneratedValue
    private int id;

    private String name;

    public Employee(String name) {
        this.name = name;
    }
}

