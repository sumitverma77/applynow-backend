package com.security.user.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.security.user.entity.job.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JobPostDAO extends AbstractDynamoDBDAO<JobEntity> {

    @Autowired
    public JobPostDAO(DynamoDBMapper dynamoDBMapper) {

        super(dynamoDBMapper);
    }

    public JobEntity getJobPost(String id) {
        return getDynamoDBMapper().load(JobEntity.class, id);
    }
    public List<JobEntity> getAvailableJobs(long DateInSecond) {
        List<JobEntity> allItems = getAll(JobEntity.class);
        return allItems.stream()
                .filter(item -> item.getApplicationOpenTill() >= DateInSecond)
                .collect(Collectors.toList());
    }

    public void update(JobEntity entity) {
        getDynamoDBMapper()
                .save(entity, DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE).build());
    }

    public void save(JobEntity entity) {
        getDynamoDBMapper().save(entity);
    }
}