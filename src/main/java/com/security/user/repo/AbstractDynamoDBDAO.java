package com.security.user.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@RequiredArgsConstructor(onConstructor_ = { @Autowired})
public class AbstractDynamoDBDAO<T> {
    private final DynamoDBMapper dynamoDBMapper;

    public void save(T entity) {

        dynamoDBMapper.save(entity);
    }
    public List<T> getAll(Class<T> entityClass)
    {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(entityClass, scanExpression);
    }


    public List<DynamoDBMapper.FailedBatch> saveAll(List<T> entity) {
        return dynamoDBMapper.batchSave(entity);
    }

    public void delete(T entity) {
        dynamoDBMapper.delete(entity);
    }
}
