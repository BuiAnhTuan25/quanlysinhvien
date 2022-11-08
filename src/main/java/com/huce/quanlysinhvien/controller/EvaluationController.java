package com.huce.quanlysinhvien.controller;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.EvaluationsDto;
import com.huce.quanlysinhvien.service.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/evaluations")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvaluation(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(evaluationService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getByType(
            @RequestParam("type") TypeEnum type,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(evaluationService.getByType(type, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createEvaluation(
            @RequestBody EvaluationsDto evaluation
    ) throws MessagingException {
        return new ResponseEntity<>(evaluationService.createEvaluation(evaluation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvaluation(
            @RequestBody EvaluationsDto evaluation,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(evaluationService.updateEvaluation(evaluation, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvaluation(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(evaluationService.deleteEvaluation(id), HttpStatus.OK);
    }
}
