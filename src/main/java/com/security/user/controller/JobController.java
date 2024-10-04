package com.security.user.controller;

import com.security.user.dto.request.AddJobRequest;
import com.security.user.dto.request.GetJobRequest;
import com.security.user.dto.response.AddJobResponse;
import com.security.user.entity.job.JobEntity;
import com.security.user.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("job/")
public class JobController {
    @Autowired
    JobService jobService;

    @PostMapping("post")
    public ResponseEntity<AddJobResponse> add(@RequestBody AddJobRequest addRequest) {
        System.out.println("hello");
        return jobService.saveJob(addRequest);
    }

    @GetMapping("get")
    public ResponseEntity<List<JobEntity>> get(@RequestBody GetJobRequest getJobRequest, @RequestHeader("API_KEY") String secretKey ) {
        return jobService.getJobs(getJobRequest, secretKey);
    }
    @GetMapping("get/verified-jobs")
    public  ResponseEntity<List<JobEntity>> getVerifiedJobs(@RequestBody GetJobRequest getJobRequest)
    {
        return jobService.getVerifiedJobs(getJobRequest);
    }
    @PostMapping("approve")
    public ResponseEntity<?> approveJob(@RequestHeader("JOB_ID") String jobId, @RequestHeader("DATE") Long jobDate, @RequestHeader("API_KEY") String secretKey ) {
      return jobService.approveJob(jobId , jobDate ,  secretKey);
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteJob(@RequestHeader("JOB_ID") String jobId, @RequestHeader("DATE") Long jobDate,@RequestHeader("API_KEY") String secretKey) {
        return jobService.deleteJob(jobId , jobDate ,  secretKey);
    }
}