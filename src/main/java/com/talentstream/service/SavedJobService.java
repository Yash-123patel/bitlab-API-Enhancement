package com.talentstream.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.talentstream.entity.Applicant;
import com.talentstream.entity.Job;
import com.talentstream.entity.SavedJob;
import com.talentstream.exception.CustomException;
import com.talentstream.repository.ApplyJobRepository;
import com.talentstream.repository.JobRepository;
import com.talentstream.repository.RegisterRepository;
import com.talentstream.repository.SavedJobRepository;

@Service
public class SavedJobService {
	@Autowired
	private SavedJobRepository savedJobRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private RegisterRepository applicantRepository;

	@Autowired
	private ApplyJobRepository applyJobRepository;

	

	public void saveJobForApplicant(long applicantId, long jobId) throws Exception {
		try {
			Applicant applicant = applicantRepository.findById(applicantId);
			Job job = jobRepository.findById(jobId).orElse(null);

			if (applicant == null || job == null) {
				throw new CustomException("Applicant or Job not found", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (!savedJobRepository.existsByApplicantAndJob(applicant, job)) {
				SavedJob savedJob = new SavedJob();
				savedJob.setApplicant(applicant);
				savedJob.setSaveJobStatus("saved");
				jobRepository.save(job);
				savedJob.setJob(job);
				savedJobRepository.save(savedJob);
			} else {
				throw new CustomException("Job has already been saved by the applicant",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			throw new CustomException("Error saving job for the applicant", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Page<Job> getSavedJobsForApplicant(long applicantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
 
        try {
            // Fetch job IDs for the applicant (with pagination)
            List<Long> savedJobIds = savedJobRepository.findSavedJobIdsByApplicantId(applicantId);
 
            if (savedJobIds.isEmpty()) {
                return Page.empty(pageable); // Return an empty page if no job IDs found
            }
 
            // Apply pagination directly in the repository query
            Page<Job> jobs = jobRepository.findJobsByIds(savedJobIds, pageable);
 
            return jobs;
 
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Error while retrieving saved jobs for applicant", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	private boolean isJobSavedByApplicant(long jobId, long applicantId) {
		return savedJobRepository.existsByApplicantIdAndJobId(applicantId, jobId);
	}

	public long countSavedJobsForApplicant(long applicantId) {
		try {
			// Check if the applicant exists
			if (!applicantRepository.existsById(applicantId)) {
				// Throw CustomException with a specific error message and 404 status
				throw new CustomException("Applicant not found", HttpStatus.NOT_FOUND);
			}

			// Use the custom query to count saved jobs
			return savedJobRepository.countByApplicantId(applicantId);
		} catch (CustomException e) {
			throw e; // Re-throw CustomException as is
		} catch (Exception e) {
			// Handle other exceptions as needed
			throw new CustomException("Error while counting saved jobs for the applicant",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public void deleteSavedJobForApplicant(long applicantId, long jobId) throws CustomException {
		try {
			Applicant applicant = applicantRepository.findById(applicantId);
			Job job = jobRepository.findById(jobId).orElse(null);
			

			if (applicant == null || job == null) {
				throw new CustomException("Applicant or Job not found", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			SavedJob savedJob = savedJobRepository.findByApplicantAndJob(applicant, job);

			savedJobRepository.delete(savedJob);


		} catch (Exception e) {
			throw new CustomException("Error deleting saved job for the applicant", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
