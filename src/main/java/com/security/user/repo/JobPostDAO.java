package com.security.user.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.security.user.entity.job.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JobPostDAO extends AbstractDynamoDBDAO<JobEntity> {

    @Autowired
    public JobPostDAO(DynamoDBMapper dynamoDBMapper) {

        super(dynamoDBMapper);
    }

    public JobEntity getJobPost(String id , Long jobDate) {
        return getDynamoDBMapper().load(JobEntity.class, id , jobDate);
    }

    public List<JobEntity> getAvailableJobs(long dateInSecond) {
        List<JobEntity> allItems = getAll(JobEntity.class);
        return allItems.stream()
                .filter(item -> item.getApplicationOpenTill() >= dateInSecond)
                .collect(Collectors.toList());
    }

    public List<JobEntity> getVerifiedJobs(long dateInSecond) {
        List<JobEntity> allItems = getAll(JobEntity.class);
        return allItems.stream()
                .filter(item -> item.getIsVerified()!=null  && item.getIsVerified()&& item.getApplicationOpenTill() >= dateInSecond)
                .collect(Collectors.toList());
    }
        public void deleteJob(String id , Long jobDate) {
            JobEntity job = getJobPost(id , jobDate );
            if(Objects.nonNull(job))
            {
                getDynamoDBMapper().delete(job);
            }
        }
//    public void update(JobEntity entity) {
//        getDynamoDBMapper()
//                .save(entity, DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE).build());
//    }

    public void save(JobEntity entity) {
        getDynamoDBMapper().save(entity);
    }


    public void approveJob(JobEntity jobEntity) {
        jobEntity.setIsVerified(true);
        getDynamoDBMapper()
                .save(jobEntity, DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE).build());
    }
}