package com.talentstream.dto;
 
import javax.validation.constraints.NotNull;
 
public class JobVisitDTO {
 
    @NotNull(message = "User ID is required")
    private Long userId;
 
    @NotNull(message = "Job ID is required")
    private Long jobId;
 
    //Default Constructor
    public JobVisitDTO() {}
 
    
    //Argus Constructor
    public JobVisitDTO(Long userId, Long jobId) {
        this.userId = userId;
        this.jobId = jobId;
    }
 
    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public Long getJobId() {
        return jobId;
    }
 
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}