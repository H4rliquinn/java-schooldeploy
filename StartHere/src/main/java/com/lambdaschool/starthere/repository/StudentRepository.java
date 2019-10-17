package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<User, Long>
{
    List<User> findByStudnameContainingIgnoreCase(String name);
}
