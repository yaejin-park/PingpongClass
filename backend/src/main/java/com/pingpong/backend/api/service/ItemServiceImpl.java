package com.pingpong.backend.api.service;

import com.pingpong.backend.Exception.CustomException;
import com.pingpong.backend.Exception.ErrorCode;
import com.pingpong.backend.api.domain.*;
import com.pingpong.backend.api.domain.request.ItemRequest;
import com.pingpong.backend.api.domain.request.NoticeRequest;
import com.pingpong.backend.api.domain.response.ItemStudentResponse;
import com.pingpong.backend.api.repository.ItemRepository;
import com.pingpong.backend.api.repository.ItemStudentRepository;
import com.pingpong.backend.api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ItemStudentRepository itemStudentRepository;

    //전체 아이템 목록 조회
    @Override
    public List<ItemEntity> loadItem() throws Exception {
        return itemRepository.findAll();
    }

    //보유 아이템 목록 조회
    @Override
    public List<ItemStudentResponse> findItemById(int studentId) throws Exception {
        StudentEntity student = studentRepository.getOne(studentId);
        List<ItemEntity> haveItemlist = itemRepository.findAll();
        List<ItemStudentResponse> result = new ArrayList<>();
        for(ItemEntity itemlist : haveItemlist){
            result.add(new ItemStudentResponse(itemlist, itemStudentRepository.countByStudentEntityAndItemEntity(student, itemlist)));
        }
        return result;
    }

    //아이템 사용
    @Transactional
    public void delete(final int studentId, final int itemId){
        StudentEntity student = studentRepository.getOne(studentId);
        ItemEntity item = itemRepository.getOne(itemId);
        ItemStudentEntity entity = itemStudentRepository.findFirstByStudentEntityAndItemEntity(student, item);
        itemStudentRepository.delete(entity);
    }

    //잔디색상 변경
    @Transactional
    public int updateJandiColor(final int studentId, final int jandiColor){
        StudentEntity student = studentRepository.getOne(studentId);
        student.updateJandiColor(jandiColor);
        return student.getJandiColor();
    }

    //테두리색상 변경
    @Transactional
    public int updateBorderColor(final int studentId, final int borderColor){
        StudentEntity student = studentRepository.getOne(studentId);
        student.updateBorderColor(borderColor);
        return student.getBorderColor();
    }

    //칭찬스티커 사용
    @Transactional
    public int usePoint(final int studentId){
        StudentEntity entity = studentRepository.findById(studentId).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.usePoint(entity.getPoint()-5);
        return entity.getPoint();
    }

    //아이템 저장
    @Transactional
    public String save(final ItemRequest request) throws IOException {
        System.out.println("---------------save : "+request.getStudentId()+"/"+request.getItemId());
        StudentEntity student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        ItemEntity item = itemRepository.getOne(request.getItemId());
        ItemStudentEntity entity = new ItemStudentEntity(student, item);
        student.usePoint(student.getPoint()-15);
        System.out.println("----------- item : "+item);

        if(item.getCategory().equals("REACTION")){
            //리액션 중복 확인
            int reaction = itemStudentRepository.countByStudentEntityAndItemEntity(student, item);
            if(reaction == 0){
                itemStudentRepository.save(entity);
                return "저장되었습니다.";
            } else{
                return "이미 보유한 리액션입니다.";
            }
        } else{
            itemStudentRepository.save(entity);
            return "저장되었습니다.";
        }
    }

    //보유 리액션 목록조회
    @Override
    public HashSet<String> findReaction(final int studentId) throws Exception {
        StudentEntity student = studentRepository.getOne(studentId);
        List<ItemStudentEntity> haveItemlist = itemStudentRepository.findAllByStudentEntity(student);
        HashSet<String> result = new HashSet<>();
        for(ItemStudentEntity itemlist : haveItemlist){
            if(itemlist.getItemEntity().getItemId()>4) {
//                System.out.println(itemlist.getItemEntity().getItemId()+"!!!");
                result.add(itemlist.getItemEntity().getName());
            }
        }
        return result;
    }

    @Override
    public String findItemName(int itemId) throws Exception {
        String name = itemRepository.getOne(itemId).getName();
        return name;
    }

}
