package com.example.nplus1;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 양방향 매핑을 위해 추가
    @OneToMany(mappedBy = "team") // mappedBy 는 양방향 매핑일 때 사용하며, 연관관계의 주인이 아니라는 뜻. db 에 FK 컬럼이 생기지 않음
    private List<Member> members = new ArrayList<>();

}
