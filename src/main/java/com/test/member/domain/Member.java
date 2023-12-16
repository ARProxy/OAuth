package com.test.member.domain;

import com.test.authentication.domain.oauth.OAuthProvider;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String gender;

    private String age_range;

    private String profile_image_url;

    private String birthday;

    private OAuthProvider oAuthProvider;

    @Builder
    public Member(String email, String nickname, String gender, String age_range, String profile_image_url, String birthday, OAuthProvider oAuthProvider) {
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.age_range = age_range;
        this.profile_image_url = profile_image_url;
        this.birthday = birthday;
        this.oAuthProvider = oAuthProvider;
    }
}
