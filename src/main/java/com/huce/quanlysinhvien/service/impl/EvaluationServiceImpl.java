package com.huce.quanlysinhvien.service.impl;

import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.EvaluationsDto;
import com.huce.quanlysinhvien.model.entity.EvaluationsEntity;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;
import com.huce.quanlysinhvien.model.response.Pagination;
import com.huce.quanlysinhvien.model.response.Response;
import com.huce.quanlysinhvien.repository.EvaluationRepository;
import com.huce.quanlysinhvien.service.EvaluationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public Data getById(Long id){
        Optional<EvaluationsEntity> evaluation = evaluationRepository.findById(id);

        return evaluation.map(data -> response.responseData("Get evaluation successfully", mapper.map(data, EvaluationsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }
    @Override
    public Data getByStudentId(Long id, TypeEnum type){
        Optional<EvaluationsEntity> evaluation = evaluationRepository.findByStudentIdAndType(id, type);

        return evaluation.map(data -> response.responseData("Get evaluation successfully", mapper.map(data, EvaluationsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData getAll(int page, int pageSize){
        Page<EvaluationsEntity> evaluations = evaluationRepository.findAll(PageRequest.of(page, pageSize));

        List<EvaluationsDto> evaluationsDto = evaluations.stream().map(s->mapper.map(s,EvaluationsDto.class)).collect(Collectors.toList());

        return response.responseListData(evaluationsDto, new Pagination(evaluations.getNumber(), evaluations.getSize(), evaluations.getTotalPages(),
                (int) evaluations.getTotalElements()));
    }

    @Override
    public ListData getByType(TypeEnum type, int page, int pageSize){
        Page<EvaluationsEntity> evaluations = evaluationRepository.findByType(type, PageRequest.of(page, pageSize));

        List<EvaluationsDto> evaluationsDto = evaluations.stream().map(s->mapper.map(s,EvaluationsDto.class)).collect(Collectors.toList());

        return response.responseListData(evaluationsDto, new Pagination(evaluations.getNumber(), evaluations.getSize(), evaluations.getTotalPages(),
                (int) evaluations.getTotalElements()));
    }

    @Override
    public Data createEvaluation(EvaluationsDto evaluationsDto){
        EvaluationsEntity evaluationNew = new EvaluationsEntity(evaluationsDto);

        return response.responseData("Create successfully",mapper.map(evaluationRepository.save(evaluationNew),EvaluationsDto.class));
    }

    @Override
    public Data updateEvaluation(EvaluationsDto evaluationsDto, Long id){
        Optional<EvaluationsEntity> evaluation = evaluationRepository.findById(id);
        if(evaluation.isEmpty()){
            return response.responseError("Entity not found");
        }

        EvaluationsEntity evaluationUpdate = evaluation.get().mapper(evaluationsDto);

        return response.responseData("Update successfully",mapper.map(evaluationRepository.save(evaluationUpdate),EvaluationsDto.class));
    }

    @Override
    public Data deleteEvaluation(Long id){
        Optional<EvaluationsEntity> evaluation = evaluationRepository.findById(id);
        if(evaluation.isEmpty()){
            return response.responseError("Entity not found");
        }
        evaluation.get().delete();

        return response.responseData("Delete successfully",mapper.map(evaluationRepository.save(evaluation.get()),EvaluationsDto.class));
    }
}
