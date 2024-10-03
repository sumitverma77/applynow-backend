package com.security.user.Converter;

import com.security.user.dto.request.AddJobRequest;
import com.security.user.entity.job.JobEntity;
import com.security.user.exception.InvalidDateException;
import com.security.user.utils.DateUtil;

import java.util.Optional;

public class JobConverter {
    public static JobEntity ConvertAddJobRequestToJobEntity(AddJobRequest addJobRequest) throws InvalidDateException {
        JobEntity jobEntity = new JobEntity();

        jobEntity.setJobTitle(addJobRequest.getJobTitle());
        jobEntity.setDescription(addJobRequest.getDescription());
        jobEntity.setExperience(addJobRequest.getExperience());

        Optional<Long> releaseTimestamp = DateUtil.toUnixTimestamp(addJobRequest.getDateOfRelease());
        if (releaseTimestamp.isPresent()) {
            jobEntity.setDateOfRelease(releaseTimestamp.get());
        } else {
            throw new InvalidDateException("Invalid Date format: " + addJobRequest.getDateOfRelease());
        }

        Optional<Long> openTillTimestamp = DateUtil.toUnixTimestamp(addJobRequest.getApplicationOpenTill());
        if (openTillTimestamp.isPresent()) {
            jobEntity.setApplicationOpenTill(openTillTimestamp.get());
        } else {
            throw new InvalidDateException("Invalid Date format: " + addJobRequest.getApplicationOpenTill());
        }
        jobEntity.setLocation(addJobRequest.getLocation());
        jobEntity.setApplicationLink(addJobRequest.getApplicationLink());
        return jobEntity;
    }
}
