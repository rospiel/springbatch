package com.study.studyapplicationbatch.jobFileFlat.domain;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Address address;

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
