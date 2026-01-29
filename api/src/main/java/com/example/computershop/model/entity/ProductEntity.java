package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String model;
    @Column
    private String maker;
    @Column
    private String type;


}

