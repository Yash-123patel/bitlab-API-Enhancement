package com.talentstream.dto;
 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
 
 
public class PlacementDriveDTO {
 
    @NotNull(message = "Drive Name is required")
    private String driveName;
 
    private Date fromDate;
    private Date toDate;
 
    private String startTime;
    private String endTime;
 
    private String mode; // Keeping it as a String to avoid direct Enum binding
 
    @Size(min = 15, max = 200, message = "Guidelines must be between 15 and 200 characters")
    private String guidelines;
 
    @Size(min = 15, max = 200, message = "Interview Process must be between 15 and 200 characters")
    private String interviewProcess;
 
    // Getters and Setters
    public String getDriveName() {
        return driveName;
    }
 
    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }
 
    public Date getFromDate() {
        return fromDate;
    }
 
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
 
    public Date getToDate() {
        return toDate;
    }
 
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
 
    public String getStartTime() {
        return startTime;
    }
 
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
 
    public String getEndTime() {
        return endTime;
    }
 
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
 
    public String getMode() {
        return mode;
    }
 
    public void setMode(String mode) {
        this.mode = mode;
    }
 
    public String getGuidelines() {
        return guidelines;
    }
 
    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }
 
    public String getInterviewProcess() {
        return interviewProcess;
    }
 
    public void setInterviewProcess(String interviewProcess) {
        this.interviewProcess = interviewProcess;
    }
}