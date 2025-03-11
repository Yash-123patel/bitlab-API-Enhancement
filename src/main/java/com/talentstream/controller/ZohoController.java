package com.talentstream.controller;

import java.util.Map;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.talentstream.service.ZohoCRMService;
 
@CrossOrigin
@RestController
@RequestMapping("/zoho")
public class ZohoController {
 
    private final ZohoCRMService zohoCRMService;
 
    @GetMapping("/name")
    public String getName() {
        return "Hello ZOHO Controller";
    }
 
    public ZohoController(ZohoCRMService service) {
        this.zohoCRMService = service;
    }
 
    @PostMapping("/create-lead")
    public ResponseEntity<Object> createLead(@RequestBody Map<String, Object> requestData) {
        try {
            Object response = zohoCRMService.storeDataInZohoCRM(requestData);
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to create lead. " + e.getMessage());
        }
    }
 
    @PutMapping("/update/{recordId}")
    public ResponseEntity<Object> updateLead(@PathVariable String recordId,
            @RequestBody Map<String, Object> updateData) {
        try {
            Object response = zohoCRMService.updateLead(recordId, updateData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to update lead. " + e.getMessage());
        }
    }
 
    @DeleteMapping("/deleteLead/{recordId}")
    public ResponseEntity<Object> deleteLead(@PathVariable String recordId) {
        try {
            Object response = zohoCRMService.deleteLead(recordId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to delete lead. " + e.getMessage());
        }
    }
 
}