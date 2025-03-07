package com.talentstream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talentstream.entity.Job;
import com.talentstream.repository.JobRepository;
@Transactional
@Service
public class WidgetService {

	@Autowired
	JobRepository jobrepository;
	
	
	public List<Job> getAllJobs() {
		
		
		return jobrepository.findAll();
	}

	
}
