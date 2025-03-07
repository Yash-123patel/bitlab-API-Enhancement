package com.talentstream.service;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
@Transactional
@Service
public class ZohoCRMService {
 
    private final ZohoAuthService zohoAuthService;
    private static final String ZOHO_LEADS_URL = "https://www.zohoapis.com/crm/v7/Leads";
 
    public ZohoCRMService(ZohoAuthService authService) {
        this.zohoAuthService = authService;
    }
 
    public String getLeads() {
        String accessToken = zohoAuthService.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
 
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(ZOHO_LEADS_URL, HttpMethod.GET, entity, String.class);
 
        return response.getBody();
    }
    public Object storeDataInZohoCRM(Map<String, Object> requestData) {
        String accessToken = zohoAuthService.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
 
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        ResponseEntity<Object> response = restTemplate.exchange(ZOHO_LEADS_URL, HttpMethod.POST, request, Object.class);
 
        return response.getBody();
    }
    public Object updateLead(String recordId, Map<String, Object> updateData) {
        String accessToken = zohoAuthService.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
 
        String updateUrl = ZOHO_LEADS_URL + "/" + recordId;
 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
 
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(updateData, headers);
        ResponseEntity<Object> response = restTemplate.exchange(updateUrl, HttpMethod.PUT, request, Object.class);
 
        return response.getBody();
    }

    public Object deleteLead(String recordId) {
    	String accessToken = zohoAuthService.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
 
        String deleteUrl = ZOHO_LEADS_URL + "/" +recordId;
 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, Object.class);
        return response.getBody();
    }
    public Object searchLeadByEmail(String email) {
    	String accessToken = zohoAuthService.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
 
        String deleteUrl = ZOHO_LEADS_URL + "/search?email=" +email;
 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(deleteUrl, HttpMethod.GET, entity, Object.class);
        return response.getBody();
    }

}