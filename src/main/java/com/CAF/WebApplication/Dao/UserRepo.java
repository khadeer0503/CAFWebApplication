package com.CAF.WebApplication.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CAF.WebApplication.Model.User;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

@Query("SELECT u FROM User u WHERE CONCAT(u.name, u.codicefiscale, u.id) LIKE %?1%")  //METHOD-2
     List<User> searchUsers(String keyword);
    Page<User> findAll(Pageable pageable);

}








//METHOD -1
    //public List<User> findByNameAndId(String name,int id);
//  @Query("select u From User u WHERE u.name = :n and u.id = :userID")

    //METHOD-2
//	@Query("SELECT u FROM User u WHERE CONCAT(u.name, u.codicefiscale, u.id) LIKE %:keyword%")
//    @Query("select u from User u where u.name like :n ")
//   List<User> searchUsers(@Param("n") String name);


            //METHOD -3
          /*   @Query("SELECT u FROM User u WHERE " +
                   "u.id LIKE CONCAT('%',:keyword,'%')" +
                    "OR u.name LIKE CONCAT('%',:keyword,'%')" +
                    "OR u.codicefiscale LIKE CONCAT('%',:keyword,'%')" ) */