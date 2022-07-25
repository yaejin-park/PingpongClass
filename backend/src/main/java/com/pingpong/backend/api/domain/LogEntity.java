package com.pingpong.backend.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name="log")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    @ManyToOne
    @JoinColumn(name="class_id", nullable = false)
    private ClassEntity classentity;

    @ManyToOne
    @JoinColumn(name="student_id", nullable = false)
    private StudentEntity studententity;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date regDate;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false)
    private boolean attendance;

    @Column(nullable = false)
    private int presentCnt;

    @Builder
    public LogEntity(ClassEntity classEntity, StudentEntity studententity, Date regDate, int point, boolean attendance, int presentCnt){
        this.classentity = classEntity;
        this.studententity = studententity;
        this.regDate = regDate;
        this.point = point;
        this.attendance = attendance;
        this.presentCnt = presentCnt;
    }
}