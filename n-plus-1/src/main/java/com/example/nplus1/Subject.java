package com.example.nplus1;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "academy_id", foreignKey = @ForeignKey(name = "FK_SUBJECT_ACADEMY"))
    private Academy academy;

    @ManyToOne
    @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "FK_SUBJECT_TEACHER"))
    private Teacher teacher;

    @Builder
    public Subject(String name, Academy academy, Teacher teacher){
        this.name = name;
        this.academy = academy;
        this.teacher = teacher;
    }

    public void updateAcademy(Academy academy){
        this.academy = academy;
    }
}
