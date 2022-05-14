package com.example.nplus1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcademyService {
    private final AcademyRepository repository;

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNames(){
        return extractSubjectNames(repository.findAll());
    }

    /**
     * Lazy 로드를 수행하기 위해 메서드 별도 생성
     */

    private List<String> extractSubjectNames(List<Academy> academies){
        log.info(">>>>> 모든 과목을 추출한다 <<<<<<");
        log.info("Academy Size : {}", academies.size());

        return (List<String>) academies.stream()
                .map(academy -> academy.getSubjects().get(0).getName())
                .peek(n -> System.out.println("출력: " + n))
                .collect(Collectors.toList());
    }

}
