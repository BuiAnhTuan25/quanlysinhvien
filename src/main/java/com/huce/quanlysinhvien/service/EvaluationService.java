package com.huce.quanlysinhvien.service;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.EvaluationsDto;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;

public interface EvaluationService {
    Data getById(Long id);
    ListData getAll(int page, int pageSize);
    ListData getByType(TypeEnum type, int page, int pageSize);
    Data createEvaluation(EvaluationsDto evaluation);
    Data updateEvaluation(EvaluationsDto evaluation, Long id);
    Data deleteEvaluation(Long id);
}
