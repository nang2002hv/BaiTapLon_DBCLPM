package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long>{
    User findByUsernameAndPassword(String username,String password);
}
