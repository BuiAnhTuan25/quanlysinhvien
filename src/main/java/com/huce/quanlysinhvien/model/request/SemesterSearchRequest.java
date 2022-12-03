package com.huce.quanlysinhvien.model.request;

import com.huce.quanlysinhvien.constains.SemesterEnum;
import com.huce.quanlysinhvien.constains.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterSearchRequest {
    private String year;
    private String semester;
    private SemesterEnum status;
    private TypeEnum type;
    private String sortBy;
}
