package com.example.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 상태 공유. 모든 테스트에서 동일한 객체 사용.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {
    int value = 1;
    @Test
    @DisplayName("스터디 생성 ୧(๑•̀ᗝ•́)૭")
    @EnabledIfEnvironmentVariable(named = "MY_ENV", matches = "TEST_ENV") // 어노테이션으로 환경변수 조건 걸기
    @Tag("fast")
    @EnabledOnOs(OS.MAC)
    @Order(3)
    void studyTest(){
        // 코드로 환경 변수 조건 걸기
//        String my_env = System.getenv("MY_ENV");
//        System.out.println(my_env);
//        assumeTrue("TEST_ENV".equals(my_env)); // false 를 반환하면 이후의 문장 실행되지 않음.
        System.out.println("현재 환경변수 MY_ENV 는 TEST_ENV 이며, assumeTrue 가 true 를 반환하여 출력된 문장.");
        Study study = new Study();
        assertNotNull(study);
    }

    /**
     * junit 은 테스트 메소듣마다 테스트 인스턴스를 만든다.
     * 테스트 간의 의존성을 없애기 위해 매 테스트마다 junit 은 새로은 객체를 만들어서 쓴다.
     */
    @Order(1)
    @FastTest
    void customTest(){
        System.out.println(value++);
        System.out.println(this); // 매 메서드 실행마다 다른 객체를 씀
    }

    @Order(2)
    @SlowTest
    void customTest2(){
        System.out.println(value++);
        System.out.println(this); // 매 메서드 실행마다 다른 객체를 씀
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