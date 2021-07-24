package com.study.studyapplicationbatch.jobJdbcCursorReader.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDto {

    private Long id;
    private String name;
    private Long stateId;
    private String user;
    private String userCreated;

    @Override
    public String toString() {
        return "CityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stateId=" + stateId +
                ", user='" + user + '\'' +
                ", userCreated='" + userCreated + '\'' +
                '}';
    }
}
