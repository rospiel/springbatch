package com.study.studyapplicationbatch.processorsClassifier.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressProcessorClassifier {

    private String street;

    private Integer number;
    private String city;
    private String state;
    private String postalCode;

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
