package com.talentstream.dto;
 
import javax.validation.constraints.NotNull;
 
public class PricingPlanDTO {
 
    @NotNull(message = "Plan name is required")
    private String planName;
    
    private int databaseAccess;
    private int jobListings;
    private boolean emailCommunication;
    private boolean pushNotifications;
    private boolean candidateManagement;
 
    @NotNull(message = "Define validity in months")
    private String validity;
    
    private boolean whatsappSupport;
    private boolean dedicatedManager;
    private boolean customPrescreening;
 
    @NotNull(message = "Plan price is required")
    private Integer pricing;
    
    private String contactDetails;
 
    // Default Constructor
    public PricingPlanDTO() {}
 
    
 
    // Getters and Setters
    public String getPlanName() {
        return planName;
    }
 
    public void setPlanName(String planName) {
        this.planName = planName;
    }
 
    public int getDatabaseAccess() {
        return databaseAccess;
    }
 
    public void setDatabaseAccess(int databaseAccess) {
        this.databaseAccess = databaseAccess;
    }
 
    public int getJobListings() {
        return jobListings;
    }
 
    public void setJobListings(int jobListings) {
        this.jobListings = jobListings;
    }
 
    public boolean isEmailCommunication() {
        return emailCommunication;
    }
 
    public void setEmailCommunication(boolean emailCommunication) {
        this.emailCommunication = emailCommunication;
    }
 
    public boolean isPushNotifications() {
        return pushNotifications;
    }
 
    public void setPushNotifications(boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }
 
    public boolean isCandidateManagement() {
        return candidateManagement;
    }
 
    public void setCandidateManagement(boolean candidateManagement) {
        this.candidateManagement = candidateManagement;
    }
 
    public String getValidity() {
        return validity;
    }
 
    public void setValidity(String validity) {
        this.validity = validity;
    }
 
    public boolean isWhatsappSupport() {
        return whatsappSupport;
    }
 
    public void setWhatsappSupport(boolean whatsappSupport) {
        this.whatsappSupport = whatsappSupport;
    }
 
    public boolean isDedicatedManager() {
        return dedicatedManager;
    }
 
    public void setDedicatedManager(boolean dedicatedManager) {
        this.dedicatedManager = dedicatedManager;
    }
 
    public boolean isCustomPrescreening() {
        return customPrescreening;
    }
 
    public void setCustomPrescreening(boolean customPrescreening) {
        this.customPrescreening = customPrescreening;
    }
 
    public Integer getPricing() {
        return pricing;
    }
 
    public void setPricing(Integer pricing) {
        this.pricing = pricing;
    }
 
    public String getContactDetails() {
        return contactDetails;
    }
 
    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }
}