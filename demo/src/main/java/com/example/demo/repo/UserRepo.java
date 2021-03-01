package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
        User findByUsername(String username);
}
