package com.security.user.service;

import com.security.user.Converter.JobConverter;
import com.security.user.dto.request.AddJobRequest;
import com.security.user.dto.request.GetJobRequest;
import com.security.user.dto.response.AddJobResponse;
import com.security.user.entity.job.JobEntity;
import com.security.user.exception.ErrorResponse;
import com.security.user.exception.InvalidDateException;
import com.security.user.repo.JobPostDAO;
import com.security.user.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobPostDAO jobPostDAO;

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
//    save jobs in db with false
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
//  get all jobs
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
    // get jobs which are verified only
    public ResponseEntity<List<JobEntity>> getVerifiedJobs(GetJobRequest getJobRequest) {
        Optional<Long> inputDateInSeconds = DateUtil.toUnixTimestamp(getJobRequest.getJobsAvailableDate());
        if(DateUtil.isValidDate(getJobRequest.getJobsAvailableDate()) && inputDateInSeconds.isPresent())
        {
            List<JobEntity>availableJobs=jobPostDAO.getVerifiedJobs(inputDateInSeconds.get());
            return  new ResponseEntity<>(availableJobs, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }




    public ResponseEntity<?> approveJob(String jobId, Long jobDate , String providedKey) {
        if (!SECRET_KEY.equals(providedKey)) {
            return new ResponseEntity<>(new ErrorResponse("Dont you have API key?" , HttpStatus.UNAUTHORIZED.value()) , HttpStatus.UNAUTHORIZED);
        }

        JobEntity job = jobPostDAO.getJobPost(jobId , jobDate);
        if (job == null) {
            return new ResponseEntity<>(new ErrorResponse("Job not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        jobPostDAO.approveJob(job);
        return new ResponseEntity<>("Job approved successfully" , HttpStatus.OK);
    }



    public ResponseEntity<?> deleteJob(String jobId, Long jobDate , String providedKey) {

        if (!SECRET_KEY.equals(providedKey)) {

            return new ResponseEntity<>(new ErrorResponse("You do not have the correct API key", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }

        JobEntity job = jobPostDAO.getJobPost(jobId, jobDate);
        if (job == null) {

            return new ResponseEntity<>(new ErrorResponse("Job not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        jobPostDAO.deleteJob(jobId , jobDate);

        return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
    }

}