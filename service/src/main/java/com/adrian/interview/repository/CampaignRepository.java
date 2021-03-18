package com.adrian.interview.repository;

import com.adrian.interview.model.RecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<RecordModel, Long>, JpaSpecificationExecutor<RecordModel> {
    List<RecordModel> findAll();
}
