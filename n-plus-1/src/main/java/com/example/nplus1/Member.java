package com.example.nplus1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne // 외래키가 생성
    @JoinColumn(name = "TEAM_ID") // 생략 가능
    private Team team;
}
