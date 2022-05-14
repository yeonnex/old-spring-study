package com.example.nplus1;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class QueryTest {
    @Autowired
    private EntityManager em;

    @Test
    public void queryTest(){
        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        Member member = new Member();
        member.setName("seoyeon");

        // 관계 주인인 Member 에 team 설정
        member.setTeam(team);
        em.persist(member);

        // 관계 주인이 아닌 Team 에 member 설정
        team.getMembers().add(member);
    }
}
