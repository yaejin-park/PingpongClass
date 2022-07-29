package com.pingpong.backend.api.controller;

import com.pingpong.backend.api.domain.request.LogRequest;
import com.pingpong.backend.api.domain.request.RecordRequest;
import com.pingpong.backend.api.domain.response.RecordResponse;
import com.pingpong.backend.api.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Api(value = "강의 다시보기 API", tags={"다시보기"})
@RestController
@RequestMapping("/ssafy/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    //강의 다시보기 저장
    @ApiOperation(value = "강의다시보기 저장", notes = "teacherId, classTitle, classId, subjectCode, timatableId, videoUrl을 저장한다.")
    @PostMapping("")
    public void insertRecord(@RequestBody final RecordRequest req){
        recordService.save(req);
    }

    //강의 다시보기 목록 조회 및 검색
    @ApiOperation(value = "강의다시보기 목록 조회 및 검색", notes = "userId로 강의다시보기를 조회하고, 수업명에 따라 검색합니다.")
    @GetMapping("")
    public Page<RecordResponse> findBySearch(@RequestParam(value="id") int userId, @RequestParam(value="title", required = false) String classTitle, Pageable pageable){
        Page<RecordResponse> recordResponsePage =null;
        //학생일때 - 검색(수업명)
        if(userId>1000000000){
            recordResponsePage = recordService.findRecordsForStudent(userId, classTitle, pageable);
        }else{//선생님일때
            recordResponsePage = recordService.findRecordsForTeacher(userId, pageable);
        }
        return recordResponsePage;
    }

    @ApiOperation(value = "로그 저장", notes = "")
    @PostMapping("/log")
    public void insertLog(@RequestBody LogRequest req){
        recordService.logSave(req);
    }

}
