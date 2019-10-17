package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService
{
    List<User> findAll();

    User findStudentById(long id);

    List<User> findStudentByNameLike(String name);

    void delete(long id);

    User save(User student);

    User update(User student, long id);

    List<User> findAllPageable(Pageable pageable);
}
