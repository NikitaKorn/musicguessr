package com.petproject.musicguessr.service.code;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InviteCodeServiceImpl implements InviteCodeService {
    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new Random();
    private String inviteCode;

    @PostConstruct
    public void init() {
        generateInviteCode();
    }

    @Override
    public void generateInviteCode(){
        int length = 5;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int pos = random.nextInt(ALLOWED_CHARS.length());
            sb.append(ALLOWED_CHARS.charAt(pos));
        }
        inviteCode = sb.toString();
    }

    @Override
    public String getInviteCode() {
        return inviteCode;
    }
}
