package com.jumia.phonenumbers.models.entities;

import com.jumia.phonenumbers.models.enums.PhoneNumberState;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer extends BaseEntity {
    private String name;
    private String phone;
    @Enumerated(value = EnumType.STRING)
    private PhoneNumberState state = PhoneNumberState.NOT_VALID;
    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                "} " + super.toString();
    }
}
