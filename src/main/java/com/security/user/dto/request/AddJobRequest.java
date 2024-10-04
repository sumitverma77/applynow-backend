package com.security.user.dto.request;

import lombok.Data;

@Data
public class AddJobRequest {

    private String jobTitle;

    private String description;

    private String applicationLink;

    private String  dateOfRelease;

    private String applicationOpenTill;
    private String experience;
    private String location;
}
