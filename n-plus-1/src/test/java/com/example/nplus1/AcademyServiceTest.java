package com.example.nplus1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AcademyServiceTest {
    @Autowired
    private AcademyRepository repository;

    @Autowired
    private AcademyService service;

    @AfterEach
    public void cleanAll(){
        repository.deleteAll();
    }

    @Test
    @BeforeEach
    public void setup(){
        List<Academy> academies = new ArrayList<>();

        for (int i=0; i<5; i++){
            Academy academy = Academy.builder().name("강남대성" + i).build();
            academy.addSubject(Subject.builder().academy(academy).name("자바 웹개발" + i).build());
            academies.add(academy);
        }
        System.out.println("db 저장 시작");
        repository.saveAll(academies);

    }

    /**
     * 하위 엔티티들을 첫 쿼리 실행시 한번에 가져오지 않고, Lazy Loading 으로 필요한 곳에서 사용되어 쿼리가 실행될 때 발생하는 문제가 N + 1 문제
     */
    @Test
    public void Acadmy여러개를_조회시_Subject가_N1_쿼리발생(){
        // given
        List<String> subjectNames = service.findAllSubjectNames();

        // then
        assertThat(subjectNames.size()).isEqualTo(5);

    }

    /**
     * 연관관계가 맺어진 엔티티를 한번에 가져오는 방법
     */

    /**
     * 1) join fetch
     */
    @Test
    public void 페치조인(){
        List<Academy> allJoinFetch = repository.findAllJoinFetch();
        assertThat(allJoinFetch.size()).isEqualTo(5);
    }

    /**
     * 1.2) join fetch (하위의 하위 엔티티까지)
     */
    @Test
    public void 페치조인2(){
        repository.findAllJoinFetchTeacher();
    }

    /**
     * 2) @EntityGraph 사용하기
     * - @EntityGraph 의 attributePaths 에 쿼리 수행시 가져올 필드를 지정하면 Eager 로 가져옴
     */
    @Test
    public void 엔티티그래프(){
        repository.findAllEntityGraph();
    }

    /**
     * 2.1) Academy + Subject + Teacher 를 @EntityGraph 로 조회
     */
    @Test
    public void 엔티티그래프2(){
        repository.findAllEntityGraphWithTeacher();
    }
    /**
     * 정리
     *
     * join fetch sms inner join, @EntityGraph 는 outer join 이 일어난다.
     * 공통적으로 카타시안곱이 발생하여 Subject 의 수만큼 Academy 가 중복 발생하게 된다.
     *
     * 해결 방법
     * 1) 일대다 필드의 타입을 Set 으로 선언. Set 은 순서가 보장되지 않기 때문에 LinkedHashSet 을 사용하여 순서보장
     *
     * 2) distinct 를 사용하여 중복제거
     *  - @Query(select DISTINCT a from Academy a)
     * */

}