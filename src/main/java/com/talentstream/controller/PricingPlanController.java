package com.talentstream.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.dto.PricingPlanDTO;
import com.talentstream.entity.PricingPlan;
import com.talentstream.service.PricingPlanService;

import jakarta.validation.Valid;
 
@RestController
public class PricingPlanController {
    @Autowired
    private PricingPlanService planService;
 
    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        List<PricingPlan> plans = planService.getAllPlans();
        if (plans.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("No pricing plans available at the moment.");
        }
        return ResponseEntity.ok(plans);
    }
 
 
    
    @PostMapping("/savePlan")
    public ResponseEntity<Object> savePlan(@Valid @RequestBody PricingPlanDTO planDto, BindingResult br) {
        if (br.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();
            br.getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }
        try {
        	PricingPlan plan = convertToEntity(planDto);
            planService.savePlans(plan);
            return ResponseEntity.status(HttpStatus.CREATED).body("Plan Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Some External Error Occurred");
        }
    }
    private PricingPlan convertToEntity(PricingPlanDTO dto) {
        PricingPlan plan = new PricingPlan();
        plan.setPlanName(dto.getPlanName());
        plan.setDatabaseAccess(dto.getDatabaseAccess());
        plan.setJobListings(dto.getJobListings());
        plan.setEmailCommunication(dto.isEmailCommunication());
        plan.setPushNotifications(dto.isPushNotifications());
        plan.setCandidateManagement(dto.isCandidateManagement());
        plan.setValidity(dto.getValidity());
        plan.setWhatsappSupport(dto.isWhatsappSupport());
        plan.setDedicatedManager(dto.isDedicatedManager());
        plan.setCustomPrescreening(dto.isCustomPrescreening());
        plan.setPricing(dto.getPricing());
        plan.setContactDetails(dto.getContactDetails());
        return plan;
    }
}
