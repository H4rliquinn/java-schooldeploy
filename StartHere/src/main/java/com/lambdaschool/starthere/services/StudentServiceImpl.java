package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "studentService")
public class StudentServiceImpl implements StudentService
{
    @Autowired
    private StudentRepository studrepos;

    @Override
    public List<User> findAllPageable(Pageable pageable)
    {
        List<User> list = new ArrayList<>();
        studrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        studrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findStudentById(long id) throws EntityNotFoundException
    {
        return studrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public List<User> findStudentByNameLike(String name)
    {
        List<User> list = new ArrayList<>();
        studrepos.findByStudnameContainingIgnoreCase(name).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (studrepos.findById(id).isPresent())
        {
            studrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User student)
    {
        User newStudent = new User();

        newStudent.setStudname(student.getStudname());

        return studrepos.save(newStudent);
    }

    @Override
    public User update(User student, long id)
    {
        User currentStudent = studrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if (student.getStudname() != null)
        {
            currentStudent.setStudname(student.getStudname());
        }

        return studrepos.save(currentStudent);
    }
}
