package com.pingpong.backend.api.domain.request;

import com.pingpong.backend.api.domain.StudentEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class ClassRequest {
    private int teacherId;
    private int subjectCode;
    private String classTitle;
    private byte classDay;
    private byte timetableId;
    private String classDesc;
    private String classUrl;
    private List<StudentEntity> students;
}