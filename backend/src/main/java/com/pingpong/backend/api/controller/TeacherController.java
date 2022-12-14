package com.pingpong.backend.api.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.pingpong.backend.api.domain.TeacherEntity;
import com.pingpong.backend.api.domain.request.StudentRequest;
import com.pingpong.backend.api.domain.request.TeacherRequest;
import com.pingpong.backend.api.domain.request.UserRequest;
import com.pingpong.backend.api.domain.response.TeacherResponse;
import com.pingpong.backend.api.repository.TeacherRepository;
import com.pingpong.backend.api.service.S3Service;
import com.pingpong.backend.api.service.TeacherServiceImpl;
import com.pingpong.backend.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(value = "선생님 API", tags={"선생님"})
@RestController
@CrossOrigin("*")
@RequestMapping("/be/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherRepository repository;
    private final TeacherServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @ApiOperation(value = "선생님 회원가입", notes = "선생님 정보 삽입, 임시비밀번호 제공")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@RequestBody UserRequest.TeacherSignUp teacher){
        try {
            Integer maxTeacherId = 0;
            //관리자체크
            if (teacher.getIsAdmin() == 0) {        //선생님
                maxTeacherId = repository.getMaxTeacherId();
                //아이디 유효성 검사
                if(maxTeacherId == null || maxTeacherId < 4030001 ){
                    maxTeacherId = 4030001;
                } else if(maxTeacherId > 4039999 ){
                    return new ResponseEntity<String>("선생님 pk 범위 초과",HttpStatus.FORBIDDEN);
                }
            } else if (teacher.getIsAdmin() == 1) {  //관리자(행정실)
                maxTeacherId = repository.getMaxAdminId();
                //아이디 유효성 검사
                if(maxTeacherId == null || maxTeacherId < 5030001 ){
                    maxTeacherId = 5030001;
                } else if(maxTeacherId > 5039999 ){
                    return new ResponseEntity<String>("관리자 pk 범위 초과",HttpStatus.FORBIDDEN);
                }
            }

            TeacherEntity teacherEntity = TeacherEntity.builder()
                    .teacherId(maxTeacherId)
                    .name(teacher.getName())
                    .password(passwordEncoder.encode("ssafy"+maxTeacherId))
                    .birth(teacher.getBirth())
                    .manageGrade(teacher.getManageGrade())
                    .isAdmin(teacher.getIsAdmin())
                    .build();
            service.register(teacherEntity);
            return new ResponseEntity<String>("선생님(관리자) 회원가입 성공",HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("선생님(관리자) 회원가입 실패",HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "선생님 삭제", notes = "선생님 삭제")
    @DeleteMapping("/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTeacher(@PathVariable int teacherId){
        try{
            service.delete(teacherId);
            return new ResponseEntity<String>("선생님(관리자) 삭제 성공", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("선생님(관리자) 삭제 실패"+e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "선생님 목록 조회(이름검색까지)", notes = "이름으로 검색하면 이름까지 검색, 아니면 전체 검색")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "전체") String name){
        try{
            List<TeacherEntity> list = service.findByName(name);
            return new ResponseEntity<List<TeacherEntity>>(list, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("선생님 조회 실패"+e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "선생님 정보 조회", notes = "선생님번호로 선생님 정보 조회")
    @GetMapping("/{teacherId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<?> findByTeacherId(@PathVariable int teacherId){
        try{
            TeacherEntity teacher = repository.findByTeacherId(teacherId);
            if(teacher!=null){
                return new ResponseEntity<TeacherResponse>(new TeacherResponse(teacher),HttpStatus.OK);
            } else{
                return new ResponseEntity<String>("해당 회원을 찾을 수 없습니다.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("선생님 정보 조회 시 오류 발생",HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호 수정")
    @PatchMapping("/password")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> modifyPassword(@RequestBody TeacherRequest teacher){
        try{
            service.modifyPassword(teacher.getTeacherId(),teacher.getPassword());
            return new ResponseEntity<String>("선생님 비밀번호 수정 성공", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("선생님 비밀번호 수정 실패"+e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "이메일 수정", notes = "이메일 수정")
    @PatchMapping("/email")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> modifyEmail(@RequestBody TeacherRequest teacher){
        try {
            service.modifyEmail(teacher.getTeacherId(), teacher.getEmail());
            System.out.println(teacher.getTeacherId()+","+ teacher.getEmail());
            return new ResponseEntity<String>("선생님 이메일 수정 성공", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("선생님 이메일 수정 실패"+e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/modify")
    @ApiOperation(value = "선생님 정보 수정", notes = "선생님정보 수정")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<String> modify(@RequestPart(value="teacher") TeacherRequest teacher, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            if(file!=null) {
                if (file.getSize() >= 10485760) {
                    return new ResponseEntity<String>("이미지 크기 제한은 10MB 입니다.", HttpStatus.FORBIDDEN);
                }
                String originFile = file.getOriginalFilename();
                String originFileExtension = originFile.substring(originFile.lastIndexOf("."));
                if (!originFileExtension.equalsIgnoreCase(".jpg") && !originFileExtension.equalsIgnoreCase(".png")
                        && !originFileExtension.equalsIgnoreCase(".jpeg")) {
                    return new ResponseEntity<String>("jpg, jpeg, png의 이미지 파일만 업로드해주세요", HttpStatus.FORBIDDEN);
                }
                TeacherEntity entity = repository.getOne(teacher.getTeacherId());
                String imgPath = s3Service.upload(entity.getProfile(), file);
                teacher.setProfile(imgPath);
                service.modify(teacher);
            } else if(teacher.getProfile()!=null && teacher.getProfile().equals("reset")) {
                TeacherEntity entity = repository.getOne(teacher.getTeacherId());
                //이미지 있으면 s3 버킷에서 지움
                s3Service.delete(entity.getProfile());

                //이미지 컬럼 null로 변경
                teacher.setProfile("null");
                service.modify(teacher);
            } else {
                service.modify(teacher);
            }
            return new ResponseEntity<String>("선생님 정보수정 성공.", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("선생님 정보수정 실패", HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping
    @ApiOperation(value = "첫 방문 이메일/비밀번호 수정", notes = "첫 방문시 이메일 및 비밀번호 수정")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<String> modifyEmailAndPWD(@RequestBody TeacherRequest request) throws IOException {
        try {
            service.modify(request);
            return new ResponseEntity<String>("선생님 첫 이메일 및 비밀번호 설정 성공.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>("선생님 첫 이메일 및 비밀번호 설정 실패", HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "선생님 선택 삭제", notes = "선생님 정보 선택 삭제")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/teacher/select")
    public ResponseEntity<String> selectiveDelete(@RequestBody List<Integer> teacherlist){
        try{
            service.selectiveDelete(teacherlist);
            return new ResponseEntity<String>("선택 삭제 성공", HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
