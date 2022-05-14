package com.example.nplus1;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcademyRepository extends JpaRepository<Academy, Long> {

    /**
     * 1. join fetch 사용
     */

    // join fetch 사용
    @Query(value = "select a from Academy a join fetch a.subjects s")
    List<Academy> findAllJoinFetch();

    // Subjects 의 하위 엔티티까지 한번에 가져옴
    @Query(value = "select a from Academy a join fetch a.subjects s join fetch s.teacher")
    List<Academy> findAllJoinFetchTeacher();

    /**
     * 2. @EntityGraph 사용
     */
    @EntityGraph(attributePaths = "subjects")
    @Query("select a from Academy a")
    List<Academy> findAllEntityGraph();

    // Subject 와 그 하위 엔티티인 Teacher 도 가져옴
    @EntityGraph(attributePaths = {"subjects", "subjects.teacher"})
    @Query("select a from Academy a")
    List<Academy> findAllEntityGraphWithTeacher();

}
