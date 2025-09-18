package com.qlda.sontay.repository.auth;



import com.qlda.sontay.domain.Token;
import com.qlda.sontay.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends MongoRepository<Token, Long> {

    Token findByToken(String token);

    Optional<Token> findByUserId(UUID userId);

    Optional<Token> findByUser(User user);

    Optional<Token> findByRefreshToken(String refreshToken);
}
