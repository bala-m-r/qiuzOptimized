package com.kgisl.newQuiz.service;

import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.kgisl.newQuiz.entity.Results;
import com.kgisl.newQuiz.entity.Student;
import com.kgisl.newQuiz.entity.Subject;
import com.kgisl.newQuiz.repository.ResultsRepository;
import com.kgisl.newQuiz.repository.ResultsRepositoryCustom;
import com.kgisl.newQuiz.repository.StudentRepository;
import com.kgisl.newQuiz.repository.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
public class ResultsService {

	
    private JdbcTemplate jdbcTemplate;
    private ResultsRepository resultsRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private ResultsRepositoryCustom resultsRepositoryCustom;

    ResultsService(ResultsRepository resultsRepository, JdbcTemplate jdbcTemplate, ResultsRepositoryCustom resultsRepositoryCustom, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.resultsRepository = resultsRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.resultsRepositoryCustom = resultsRepositoryCustom;
    }

    @Transactional
    public ResponseEntity<List<Results>> getAll() {
        return new ResponseEntity<>(resultsRepository.findAll(), HttpStatus.OK);
    }


    @Transactional
    public Results addResults(int mark, int fullmark, Long rollno, String subjectcode) {
        Results result = new Results();

        Student student = this.studentRepository.findByRollno(rollno);
        Subject subject = this.subjectRepository.findBySubjectcode(subjectcode);

        Integer id = resultsRepositoryCustom.getIdByRollnoAndSubjectcode(rollno, subjectcode);

        if(id != null){
            result.setId(id);
        }
        
        result.setMark(mark);
        result.setFullmark(fullmark);
        result.setStudent(student);
        result.setSubject(subject);

        return resultsRepository.save(result);
    }

    @Transactional
    public void deleteResult(int id){
        resultsRepository.deleteById(id);
    }

    public void deleteAll(){
        resultsRepository.deleteAll();
    }
    
    public Page<Results> getResults(Pageable pageable, String sortField, String sortOrder) {
        int pageSize = pageable.getPageSize();
        int offset = pageable.getPageNumber() * pageSize;

        String sql = "SELECT id, mark, fullmark, rollno, subjectcode " +
                     "FROM results " +
                     "ORDER BY " + sortField + " " + sortOrder + " " +
                     "LIMIT ? OFFSET ?";

        List<Results> resultsList = jdbcTemplate.query(sql, resultsRowMapper, pageSize, offset);

        // Get the total number of records
        String countSql = "SELECT COUNT(*) FROM results";
        int totalRecords = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(resultsList, pageable, totalRecords);
    }
    
    private RowMapper<Results> resultsRowMapper = (rs, rowNum) -> {
        Results result = new Results();
        result.setId(rs.getInt("id"));
        result.setMark(rs.getInt("mark"));
        result.setFullmark(rs.getInt("fullmark"));
        
        Student student = studentRepository.findByRollno(rs.getLong("rollno"));
        result.setStudent(student);
        
        Subject subject = subjectRepository.findBySubjectcode(rs.getString("subjectcode"));
        result.setSubject(subject);
        
        return result;
    };
}
