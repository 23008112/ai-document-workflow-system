package com.project.documentworkflow.service;

import com.project.documentworkflow.model.*;
import com.project.documentworkflow.repository.*;
import org.springframework.stereotype.Service;

@Service
public class DecisionEngineService {

    private final DocumentRepository documentRepository;
    private final OCRDataRepository ocrDataRepository;
    private final RuleRepository ruleRepository;
    private final DecisionRepository decisionRepository;

    public DecisionEngineService(
            DocumentRepository documentRepository,
            OCRDataRepository ocrDataRepository,
            RuleRepository ruleRepository,
            DecisionRepository decisionRepository) {

        this.documentRepository = documentRepository;
        this.ocrDataRepository = ocrDataRepository;
        this.ruleRepository = ruleRepository;
        this.decisionRepository = decisionRepository;
    }

    public Decision evaluateDecision(Long documentId, Long ocrDataId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        OCRData ocrData = ocrDataRepository.findById(ocrDataId)
                .orElseThrow(() -> new RuntimeException("OCR data not found"));

        Rule rule = ruleRepository.findAll().get(0);

        Decision decision = new Decision();

        if (ocrData.getConfidenceScore() < rule.getThresholdValue()) {
            decision.setDecisionType("REVIEW");
        } else {
            decision.setDecisionType("APPROVE");
        }

        decision.setDecisionSource("SYSTEM");
        decision.setDocument(document.getDocumentId());

        return decisionRepository.save(decision);
    }
}
