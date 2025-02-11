package com.talentstream.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.talentstream.entity.Applicant;
import com.talentstream.entity.ApplicantResume;
import com.talentstream.exception.CustomException;
import com.talentstream.repository.ApplicantResumeRepository;
import com.talentstream.repository.RegisterRepository;

@Service
public class ApplicantResumeService {

	  private static final Logger logger = LoggerFactory.getLogger(ApplicantResumeService.class);
    @Autowired
    private ApplicantResumeRepository applicantResumeRepository;

    
    
    private final LocalOfficeManager officeManager;

    public ApplicantResumeService(LocalOfficeManager officeManager) {
        this.officeManager = officeManager;
    }
    
    
    @Autowired
    private RegisterRepository applicantService;

    // Uploads a PDF resume for the specified applicant; validates file size and
    // type; throws CustomException for errors.
    public String uploadPdf(long applicantId, MultipartFile pdfFile) throws IOException {

//        if (pdfFile.getSize() > 1 * 1024 * 1024) {
//            throw new CustomException("File size should be less than 1MB.", HttpStatus.BAD_REQUEST);
//        }
        
    	//#changed the code now it will take 2mb size
        if (pdfFile.getSize() > 2 * 1024 * 1024) {
            throw new CustomException("File size should be less than 1MB.", HttpStatus.BAD_REQUEST);
        }

       
        String contentType = pdfFile.getContentType();
//        if (!"application/pdf".equals(contentType)) {
//            throw new CustomException("Only PDF file types are allowed.", HttpStatus.BAD_REQUEST);
//        }
        
        //#changed the code now it will accept pdf along with doc and docx.
        if (!"application/pdf".equals(contentType) &&
        	    !"application/msword".equals(contentType) &&
        	    !"application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
        	    
        	    throw new CustomException("Only PDF, DOC, and DOCX file types are allowed.", HttpStatus.BAD_REQUEST);
        	}
        

        Applicant applicant = applicantService.getApplicantById(applicantId);
        if (applicant == null)
            throw new CustomException("Applicant not found for ID: " + applicantId, HttpStatus.NOT_FOUND);
        else {
            ApplicantResume existingpdf = applicantResumeRepository.findByApplicant(applicant);
            if (existingpdf != null) {
                String folderPath = "src/main/resources/applicant/resumes";
                String existingFileName = existingpdf.getPdfname();
                String existingFilePath = Paths.get(folderPath, existingFileName).toString();

                try {
                    Files.deleteIfExists(Paths.get(existingFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String name = StringUtils.cleanPath(pdfFile.getOriginalFilename());
                String newFileName = applicantId + "_" + name;
                String filePath = Paths.get(folderPath, newFileName).toString();
                try {
                    Files.copy(pdfFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                existingpdf.setPdfname(newFileName);
                applicantResumeRepository.save(existingpdf);

                return name;
            } else {

                String name = StringUtils.cleanPath(pdfFile.getOriginalFilename());
                String fileName = applicantId + "_" + name;
                String folderPath = "src/main/resources/applicant/resumes";
                String filePath = Paths.get(folderPath, fileName).toString();
                try {
                    Files.createDirectories(Paths.get(folderPath));
                    Files.copy(pdfFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ApplicantResume applicantResume = new ApplicantResume();
                applicantResume.setPdfname(fileName);
                applicantResume.setApplicant(applicant);
                applicantResumeRepository.save(applicantResume);

                return name;
            }
        }
    }

    // Retrieves the resume for the specified applicant by ID; returns a Resource
    // for the PDF file; throws CustomException if not found.


 
//     public ResponseEntity<org.springframework.core.io.Resource> getResumeByApplicantId(long applicantId)
//            throws IOException {
// 
//        ApplicantResume applicantResume = applicantResumeRepository.findByApplicantId(applicantId);
//        
//        
//        if (applicantResume != null) {
//            String fileName = applicantResume.getPdfname();
//            
//            Path filePath = Paths.get("src/main/resources/applicant/resumes", fileName);
//           
//            try {
//                UrlResource resource = new UrlResource(filePath.toUri());
// 
//                
//                if (!resource.exists() || !resource.isReadable()) {
//                    throw new CustomException("Resume file not accessible for applicant ID: " + applicantId, HttpStatus.NOT_FOUND);
//                }
// 
//                // Get file extension
//                String fileExtension = StringUtils.getFilenameExtension(fileName);
//                logger.info(" fileExtension is: {}",fileExtension);
//                
//                
//                // Determine media type dynamically
//                MediaType mediaType = getMediaTypeForFile(fileExtension);
//                logger.info("Media type is: {}",mediaType);
//                
//                
//                return ResponseEntity.ok()
//                        .contentType(mediaType)
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } catch (MalformedURLException e) {
//                throw new RuntimeException("Error reading the resume file for applicant ID: " + applicantId, e);
//            }
//        } else {
//            throw new CustomException("Resume not found for applicant ID: " + applicantId, HttpStatus.NOT_FOUND);
//        }
//    }
    
  
    public ResponseEntity<Resource> getResumeByApplicantId(long applicantId) throws IOException, OfficeException {

        ApplicantResume applicantResume = applicantResumeRepository.findByApplicantId(applicantId);

        if (applicantResume != null) {
            String fileName = applicantResume.getPdfname();
            Path filePath = Paths.get("src/main/resources/applicant/resumes", fileName);

            try {
                // Retrieve the file as a resource
                UrlResource resource = new UrlResource(filePath.toUri());

                if (!resource.exists() || !resource.isReadable()) {
                    throw new CustomException("Resume file not accessible for applicant ID: " + applicantId, HttpStatus.NOT_FOUND);
                }

                
                // Get file extension
                String fileExtension = StringUtils.getFilenameExtension(fileName);
                logger.info("File extension is: {}", fileExtension);

                
                
                
                // If the file is a DOC or DOCX, convert it to PDF in-memory
                if ("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
                	
                    // In-memory DOC to PDF conversion
                    InputStream inputStream = resource.getInputStream();
                    ByteArrayResource pdfResource = convertDocToPdfInMemory(inputStream);

                    // Update the file name to PDF, regardless of whether the file is DOC or DOCX
                    fileName = fileName.replaceFirst("\\.(doc|docx)$", ".pdf");  
                    fileExtension = "pdf";  

                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_PDF)
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                            .body(pdfResource);
                   }


                // Handle cases where the file is already PDF or other types
              
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(resource);

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error reading the resume file for applicant ID: " + applicantId, e);
            }
        } else {
            throw new CustomException("Resume not found for applicant ID: " + applicantId, HttpStatus.NOT_FOUND);
        }
    }

    
    
    //method converting doc and docx into pdf before retreving
    public   ByteArrayResource convertDocToPdfInMemory(InputStream docInputStream) throws IOException, OfficeException {
    	ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        try {
            LocalConverter.make(officeManager)
                    .convert(docInputStream)
                    .to(pdfOutputStream)
                    .as(DefaultDocumentFormatRegistry.PDF)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error converting document to PDF");
        }
        return new ByteArrayResource(pdfOutputStream.toByteArray());
    }
    
    
    
    
    
    
    
 // Helper method to determine the correct media type
//    public MediaType getMediaTypeForFile(String extension) {
//        
//    	 logger.info("File extension is: {}",extension);
//        
//    	if (extension == null) {
//    		
//    		logger.warn("File extension is null media type is binary type");
//            return MediaType.APPLICATION_OCTET_STREAM; // Default binary type
//        }
//
//        switch (extension.toLowerCase()) {
//            case "pdf":
//                return MediaType.APPLICATION_PDF;
//            case "doc":
//                return MediaType.parseMediaType("application/msword");
//            case "docx":
//                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//            default:
//                return MediaType.APPLICATION_OCTET_STREAM; // Generic binary type
//        }
//    }
}