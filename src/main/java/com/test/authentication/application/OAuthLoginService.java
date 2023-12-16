package com.test.authentication.application;

import com.test.authentication.domain.oauth.OAuthInfoResponse;
import com.test.authentication.domain.oauth.OAuthLoginParams;
import com.test.authentication.domain.oauth.RequestOAuthInfoService;
import com.test.authentication.domain.tokens.AuthTokens;
import com.test.authentication.domain.tokens.AuthTokensGenerator;
import com.test.member.domain.Member;
import com.test.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
        private final MemberRepository memberRepository;
        private final AuthTokensGenerator authTokensGenerator;
        private final RequestOAuthInfoService requestOAuthInfoService;

        public AuthTokens login(OAuthLoginParams params) {
            OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
            Long memberId = findOrCreateMember(oAuthInfoResponse);
            return authTokensGenerator.generate(memberId);
        }
        private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
                return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                        .map(Member::getId)
                        .orElseGet(() -> newMember(oAuthInfoResponse));
        }
        private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
                Member member = Member.builder()
                        .email(oAuthInfoResponse.getEmail())
                        .nickname(oAuthInfoResponse.getNickname())
                        .gender(oAuthInfoResponse.getGender())
                        .age_range(oAuthInfoResponse.getAge_range())
                        .profile_image_url(oAuthInfoResponse.getProfile_image_url())
                        .birthday(oAuthInfoResponse.getBirthday())
                        .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                        .build();
                return memberRepository.save(member).getId();
        }
}
