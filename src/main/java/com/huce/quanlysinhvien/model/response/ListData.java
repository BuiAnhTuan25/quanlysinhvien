package com.huce.quanlysinhvien.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListData {
    private boolean success;
    private String message;
    private Integer code;
    private List<?> data;
    private Pagination pagination;
}
