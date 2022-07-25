package com.pingpong.backend.api.service;

import com.pingpong.backend.Exception.CustomException;
import com.pingpong.backend.Exception.ErrorCode;
import com.pingpong.backend.api.domain.LogEntity;
import com.pingpong.backend.api.domain.StudentEntity;
import com.pingpong.backend.api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentRepository repository;

    @Override
    public void register(StudentEntity student) {
        repository.save(student);
    }

    @Override
    public Optional<StudentEntity> findByStudentId(int studentId) {
        return repository.findByStudentId(studentId);
    }

    @Override
    public boolean hasEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void modify(StudentEntity student) {
        repository.save(student);
    }

    @Transactional
    @Override
    public void modifyEmail(int studentId, String email) {
        StudentEntity studentEntity = repository.findById(studentId).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        studentEntity.setEmail(email);
        repository.save(studentEntity);
    }

    @Override
    public void modifyIntroduce(int studentId, String introduce) {
        StudentEntity studentEntity = repository.findById(studentId).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        studentEntity.setIntroduce(introduce);
        repository.save(studentEntity);
    }

    @Override
    public void delete(int studentId) {
        repository.deleteById(studentId);
    }

    @Override
    public List<StudentEntity> getRanking() {
        return repository.getRanking();
    }

    @Override
    public List<LogEntity> getPoint(int studentId) {
        return repository.getPoint(studentId);
    }
}