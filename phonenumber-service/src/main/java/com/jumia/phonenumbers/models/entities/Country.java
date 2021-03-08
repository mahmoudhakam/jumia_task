package com.jumia.phonenumbers.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Country extends BaseEntity {
    private String name;
    private String code;
    private String regex;

    @Transient
    public Country ofNullable() {
        return new Country();
    }
}
