package com.example.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {
    @Test
    @DisplayName("스터디 생성 ୧(๑•̀ᗝ•́)૭")
    void studyTest(){
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    void studyTest2(){
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @Disabled
    void studyTest3(){
        Study study = new Study();
    }

    @BeforeAll // 모든 테스트가 실행되기 전 단 한번 실행. static 으로 선언해야 함
    static void baTest(){
        System.out.println("Before All");
    }

    @AfterAll // 모든 테스트 실행 완료 후 단 한번 실행. static 으로 선언해야 함
    static void aaTest(){
        System.out.println("After All");
    }

    @BeforeEach // 각 테스트를 실행하기 전마다 호출. 굳이 static 아니어도 딤
    void beTest(){
        System.out.println("BeforeEach");
    }

    @AfterEach // 각 테스트를 실행완료 할때마다 호출. 굳이 static 아니어도 됨
    void aeTest(){
        System.out.println("AfterEach");
    }


}