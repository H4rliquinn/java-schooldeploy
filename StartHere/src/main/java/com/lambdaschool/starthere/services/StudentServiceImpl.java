package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceFoundException;
import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.repository.RoleRepository;
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

    @Autowired
    private RoleRepository rolerepos;

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

//    @Transactional
//    @Override
//    public User save(User student)
//    {
//        User newStudent = new User();
//        newStudent.setStudname(student.getStudname());
//        return studrepos.save(newStudent);
//    }
@Transactional
@Override
public User save(User user)
{
    if (studrepos.findByUsername(user.getUsername().toLowerCase()) != null)
    {
        throw new ResourceFoundException(user.getUsername() + " is already taken!");
    }

    User newUser = new User();
    newUser.setUsername(user.getUsername().toLowerCase());
    newUser.setPasswordNoEncrypt(user.getPassword());
    newUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());
    newUser.setStudname(user.getStudname());
    ArrayList<UserRoles> newRoles = new ArrayList<>();
    for (UserRoles ur : user.getUserroles())
    {
        long id = ur.getRole()
                .getRoleid();
        Role role = rolerepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
        newRoles.add(new UserRoles(newUser,
                ur.getRole()));
    }
    newUser.setUserroles(newRoles);

    for (Course uc : user.getCourses())
    {
        newUser.getCourses()
                .add(new Course(uc.getCoursename(),uc.getInstructor()));
    }

    return studrepos.save(newUser);
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
