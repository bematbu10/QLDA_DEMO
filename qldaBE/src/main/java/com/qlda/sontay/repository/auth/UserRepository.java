package com.qlda.sontay.repository.auth;

import com.qlda.sontay.domain.User;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    // Tìm kiếm theo username
    Optional<User> findByUsername(String username);

    // Tìm kiếm user theo refresh token (có DBRef sang Token)
    Optional<User> findByToken_RefreshToken(String refreshToken);

    // Search theo keyword (fullName, username, phone, email) + taskStatus = ACTIVE
    @Query("""
            {
              "status": "ACTIVE",
              "$or": [
                { "fullName": { "$regex": ?0, "$options": "i" } },
                { "username": { "$regex": ?0, "$options": "i" } },
                { "phone": { "$regex": ?0, "$options": "i" } },
                { "email": { "$regex": ?0, "$options": "i" } }
              ]
            }
            """)
    Page<User> searchByKeyword(String keyword, Pageable pageable);

    // getByUsernameAndIsDeletedWithRoles
    @Query(value = "{ 'username': ?0, 'isDeleted': ?1 }")
    Optional<User> getByUsernameAndIsDeleted(String username, Boolean isDeleted);

    // findByUserName
    @Query(value = "{ 'username': ?0 }")
    Optional<User> findByUserName(String username);
}
