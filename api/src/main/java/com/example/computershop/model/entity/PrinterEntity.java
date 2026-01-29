package com.example.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "printer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
public class PrinterEntity extends BaseDeviceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "printer_code_seq")
    @SequenceGenerator(name = "printer_code_seq", sequenceName = "printer_code_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long code;
    @Column
    private String color;
    @Column
    private String type;;


}
