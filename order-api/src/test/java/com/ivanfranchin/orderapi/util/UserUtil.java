package com.ivanfranchin.orderapi.util;

import com.ivanfranchin.orderapi.dto.auth.SignUpRequest;
import net.bytebuddy.utility.RandomString;

import java.text.MessageFormat;

public class UserUtil {

    public static SignUpRequest getRandomUser() {
        return SignUpRequest.builder()
                .username(RandomString.make(10))
                .email(getRandomEmail())
                .password(RandomString.make(10))
                .name(RandomString.make(10))
                .build();
    }

    public static SignUpRequest getRandomUserWithUsername(String username) {
        return SignUpRequest.builder()
                .username(username)
                .email(getRandomEmail())
                .password(RandomString.make(10))
                .name(RandomString.make(10))
                .build();
    }

    public static String getRandomEmail() {
        return MessageFormat.format("{0}@slawek.com", RandomString.make(10));
    }

}
