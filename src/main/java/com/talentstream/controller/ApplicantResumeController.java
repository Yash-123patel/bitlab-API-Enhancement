package com.talentstream.controller;

import java.io.IOException;

import org.jodconverter.core.office.OfficeException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.talentstream.exception.CustomException;
import com.talentstream.exception.UnsupportedFileTypeException;
import com.talentstream.service.ApplicantResumeService;

@RestController
@RequestMapping("/applicant-pdf")
public class ApplicantResumeController {
	
    private final ApplicantResumeService applicantResumeService;
    
    public ApplicantResumeController(ApplicantResumeService applicantResumeService) {
    	this.applicantResumeService=applicantResumeService;
    }
	
 
	@PostMapping("/{applicantId}/upload")
	public String fileUpload(@PathVariable Long applicantId, @RequestParam("resume") MultipartFile resume)
    {
    	try {
            String filename = this.applicantResumeService.uploadPdf(applicantId, resume);
            return "Resume uploaded successfully. Filename: " + filename;
        } catch (CustomException ce) {
            return ce.getMessage();
        } catch (UnsupportedFileTypeException e) {
            return "Only PDF files are allowed.";
        } catch (MaxUploadSizeExceededException e) {
            return "File size should be less than 5MB.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Resume not uploaded successfully";
        }
    }
	
	@GetMapping("/getresume/{applicantId}")
    public ResponseEntity<Resource> getResume(@PathVariable long applicantId) throws IOException, OfficeException {
        return applicantResumeService.getResumeByApplicantId(applicantId);
    }
 
 
    
}