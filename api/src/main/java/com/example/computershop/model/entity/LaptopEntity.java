package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "laptop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
public class LaptopEntity extends BaseDeviceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laptop_code_seq")
    @SequenceGenerator(name = "laptop_code_seq", sequenceName = "laptop_code_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long code;

    @Column
    private int speed;
    @Column
    private int ram;
    @Column
    private Double hd;
    @Column
    private int screen;


}
