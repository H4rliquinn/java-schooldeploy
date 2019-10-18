package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceFoundException;
import com.lambdaschool.starthere.models.Instructor;
import com.lambdaschool.starthere.models.Role;
import com.lambdaschool.starthere.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;

    @Override
    public Instructor save(Instructor instructor)
    {
//        Role newRole = new Role();
//        newRole.setName(role.getName());
//        if (role.getUserroles()
//                .size() > 0)
//        {
//            throw new ResourceFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
//        }

        return instructrepos.save(instructor);
    }
}
