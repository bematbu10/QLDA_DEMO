package com.qlda.sontay.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cl_token")
public class Token{

    @Id
    private String id;

    @Field("token")
    private String token;

    @Field("refresh_token")
    private String refreshToken;

    @Field("token_type")
    private String tokenType;

    @Field("expiration_time")
    private Instant expirationTime;

    @Field("refresh_expiration_time")
    private Instant refreshExpirationTime;

    @Field("revoked")
    private boolean revoked;

    @Field("expired")
    private boolean expired;

    @DBRef
    private User user;
}
