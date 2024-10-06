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

import static com.security.user.constant.ApiConstants.*;

@RestController
@RequestMapping("job/")
public class JobController {
    @Autowired
    JobService jobService;

    @PostMapping("post")
    public ResponseEntity<AddJobResponse> add(@RequestBody AddJobRequest addRequest) {

        return jobService.saveJob(addRequest);
    }

    @PostMapping("get-all-jobs")
    public ResponseEntity<List<JobEntity>> get(@RequestBody GetJobRequest getJobRequest, @RequestHeader(value = API_KEY, required = false) String secretKey ) {

        return jobService.getJobs(getJobRequest, secretKey);
    }
    @PostMapping("get/verified-jobs")
    public  ResponseEntity<List<JobEntity>> getVerifiedJobs(@RequestBody GetJobRequest getJobRequest)
    {
        return jobService.getVerifiedJobs(getJobRequest);
    }
    @PostMapping("approve")
    public ResponseEntity<?> approveJob(@RequestHeader(value = JOB_ID  , required = false) String jobId,@RequestHeader(value = RELEASE_DATE  , required = false) Long jobDate, @RequestHeader(value = API_KEY , required = false) String secretKey ) {
        System.out.println(secretKey);
      return jobService.approveJob(jobId , jobDate ,  secretKey);
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteJob(@RequestHeader(value = JOB_ID  , required = false) String jobId, @RequestHeader(value = RELEASE_DATE  , required = false) Long jobDate,@RequestHeader(value = API_KEY , required = false) String secretKey) {
        return jobService.deleteJob(jobId , jobDate ,  secretKey);
    }
}