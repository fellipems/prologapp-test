package com.prologapp.test.interview.domain.entities;

import com.prologapp.test.interview.domain.enums.BrandTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BrandTypeEnum type;

}
