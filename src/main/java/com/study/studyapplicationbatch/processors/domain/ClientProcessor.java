package com.study.studyapplicationbatch.processors.domain;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientProcessor {

    @NotBlank
    @Size(min = 1, max = 100)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;

    @NotNull
    @Min(18)
    @Max(100)
    private int age;

    @Email
    @Size(min = 1, max = 50)
    private String email;

    @Valid
    private AddressProcessor address;

    @Override
    public String toString() {
        String addressText = EMPTY;
        if (nonNull(addressText)) {
            addressText = address.toString();
        }

        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + addressText + '\'' +
                '}';
    }
}
