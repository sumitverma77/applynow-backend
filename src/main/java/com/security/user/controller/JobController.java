package com.security.user.controller;

import com.security.user.dto.request.AddJobRequest;
import com.security.user.dto.request.GetJobRequest;
import com.security.user.dto.response.AddJobResponse;
import com.security.user.entity.job.JobEntity;
import com.security.user.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<AddJobResponse> add(@RequestBody AddJobRequest addRequest) {
        return jobService.saveJob(addRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<List<JobEntity>> get(@RequestBody GetJobRequest getJobRequest) {
        System.out.println(getJobRequest.getJobsAvailableDate());
        return jobService.getJobs(getJobRequest);
    }
}