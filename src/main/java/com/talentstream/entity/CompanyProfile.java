package com.talentstream.entity;
 
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@JsonIgnoreProperties({"jobRecruiters"})
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String website;
    private String phoneNumber;
    private String email;
    private String headOffice;
    @Column(name = "about_company", columnDefinition = "TEXT")
    private String aboutCompany;
    @OneToOne
    @JoinColumn(name = "jobRecruiter_id")
    private JobRecruiter jobRecruiter;

    @ElementCollection
    @CollectionTable(
      name = "social_profiles",
      joinColumns = @JoinColumn(name = "company_profile_id")
    		)
    private List<String> socialProfiles; 
    @Column(nullable = false)
    private String approvalStatus;
  public String getApprovalStatus() {
		return approvalStatus;
	}
  public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
public List<String> getSocialProfiles() {
      return socialProfiles;
  }
  public void setSocialProfiles(List<String> socialProfiles) {
      this.socialProfiles = socialProfiles;
  }
    public Long getId() {
        return id;
    }
     public void setId(Long id) {
        this.id = id;
    }
    public String getCompanyName() {
        return companyName;
    }
     public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
     public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
     public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
     public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadOffice() {
        return headOffice;
    }
     public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }
	public String getAboutCompany() {
		return aboutCompany;
	}
	public void setAboutCompany(String aboutCompany) {
		this.aboutCompany = aboutCompany;
	}
	public JobRecruiter getJobRecruiter() {
		return jobRecruiter;
	}
	public void setJobRecruiter(JobRecruiter jobRecruiter) {
		this.jobRecruiter = jobRecruiter;
	}
	

}