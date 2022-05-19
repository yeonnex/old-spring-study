package com.example.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {
    @Test
    @DisplayName("스터디 생성 ୧(๑•̀ᗝ•́)૭")
    @EnabledIfEnvironmentVariable(named = "MY_ENV", matches = "TEST_ENV") // 어노테이션으로 환경변수 조건 걸기
    @Tag("fast")
    @EnabledOnOs(OS.MAC)
    void studyTest(){
        // 코드로 환경 변수 조건 걸기
//        String my_env = System.getenv("MY_ENV");
//        System.out.println(my_env);
//        assumeTrue("TEST_ENV".equals(my_env)); // false 를 반환하면 이후의 문장 실행되지 않음.
        System.out.println("현재 환경변수 MY_ENV 는 TEST_ENV 이며, assumeTrue 가 true 를 반환하여 출력된 문장.");
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @Tag("slow")
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