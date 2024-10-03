package com.security.user.service;

import com.security.user.Converter.JobConverter;
import com.security.user.dto.request.AddJobRequest;
import com.security.user.dto.request.GetJobRequest;
import com.security.user.dto.response.AddJobResponse;
import com.security.user.entity.job.JobEntity;
import com.security.user.exception.InvalidDateException;
import com.security.user.repo.JobPostDAO;
import com.security.user.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobPostDAO jobPostDAO;

    public ResponseEntity<AddJobResponse> saveJob(AddJobRequest addJobRequest) {
        if(DateUtil.isValidDate(addJobRequest.getDateOfRelease()) && DateUtil.isValidDate(addJobRequest.getApplicationOpenTill()) )
        {
            try {
                jobPostDAO.save(JobConverter.ConvertAddJobRequestToJobEntity(addJobRequest));
                return new ResponseEntity<>(new AddJobResponse("Job saved successfully"), HttpStatus.OK);
            }
            catch (InvalidDateException e) {
                return new ResponseEntity<>(new AddJobResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(new AddJobResponse("Invalid date "), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<List<JobEntity>> getJobs(GetJobRequest getJobRequest) {
        Optional<Long> inputDateInSeconds = DateUtil.toUnixTimestamp(getJobRequest.getJobsAvailableDate());
        if(DateUtil.isValidDate(getJobRequest.getJobsAvailableDate()) && inputDateInSeconds.isPresent())
        {
            List<JobEntity>availableJobs=jobPostDAO.getAvailableJobs(inputDateInSeconds.get());
            return new ResponseEntity<>(availableJobs, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}