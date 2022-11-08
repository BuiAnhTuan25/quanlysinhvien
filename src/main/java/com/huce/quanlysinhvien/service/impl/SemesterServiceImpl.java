package com.huce.quanlysinhvien.service.impl;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import com.huce.quanlysinhvien.model.dto.SemestersDto;
import com.huce.quanlysinhvien.model.entity.SemestersEntity;
import com.huce.quanlysinhvien.model.response.Data;
import com.huce.quanlysinhvien.model.response.ListData;
import com.huce.quanlysinhvien.model.response.Pagination;
import com.huce.quanlysinhvien.model.response.Response;
import com.huce.quanlysinhvien.repository.SemesterRepository;
import com.huce.quanlysinhvien.service.SemesterService;
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
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public Data getById(Long id){
        Optional<SemestersEntity> semester = semesterRepository.findById(id);

        return semester.map(data -> response.responseData("Get semester successfully", mapper.map(data, SemestersDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData getAll(int page, int pageSize){
        Page<SemestersEntity> semesters = semesterRepository.findAll(PageRequest.of(page, pageSize));

        List<SemestersDto> semestersDto = semesters.stream().map(s->mapper.map(s,SemestersDto.class)).collect(Collectors.toList());

        return response.responseListData(semestersDto, new Pagination(semesters.getNumber(), semesters.getSize(), semesters.getTotalPages(),
                (int) semesters.getTotalElements()));
    }

    @Override
    public ListData getByTypeAndStatus(TypeEnum type, SemesterEnum status, int page, int pageSize){
        Page<SemestersEntity> semesters = semesterRepository.findByTypeAndStatus(type, status, PageRequest.of(page, pageSize));

        List<SemestersDto> semestersDto = semesters.stream().map(s->mapper.map(s,SemestersDto.class)).collect(Collectors.toList());

        return response.responseListData(semestersDto, new Pagination(semesters.getNumber(), semesters.getSize(), semesters.getTotalPages(),
                (int) semesters.getTotalElements()));
    }

    @Override
    public ListData getByType(TypeEnum type, int page, int pageSize){
        Page<SemestersEntity> semesters = semesterRepository.findByType(type, PageRequest.of(page, pageSize));

        List<SemestersDto> semestersDto = semesters.stream().map(s->mapper.map(s,SemestersDto.class)).collect(Collectors.toList());

        return response.responseListData(semestersDto, new Pagination(semesters.getNumber(), semesters.getSize(), semesters.getTotalPages(),
                (int) semesters.getTotalElements()));
    }

    @Override
    public Data createSemester(SemestersDto semestersDto){
        SemestersEntity semesterNew = new SemestersEntity(semestersDto);

        return response.responseData("Create successfully",mapper.map(semesterRepository.save(semesterNew),SemestersDto.class));
    }

    @Override
    public Data updateSemester(SemestersDto semestersDto, Long id){
        Optional<SemestersEntity> semester = semesterRepository.findById(id);
        if(semester.isEmpty()){
            return response.responseError("Entity not found");
        }

        SemestersEntity semesterUpdate = semester.get().mapper(semestersDto);

        return response.responseData("Update successfully",mapper.map(semesterRepository.save(semesterUpdate),SemestersDto.class));
    }

    @Override
    public Data deleteSemester(Long id){
        Optional<SemestersEntity> semester = semesterRepository.findById(id);
        if(semester.isEmpty()){
            return response.responseError("Entity not found");
        }
        semester.get().delete();

        return response.responseData("Delete successfully",mapper.map(semesterRepository.save(semester.get()),SemestersDto.class));
    }
}
