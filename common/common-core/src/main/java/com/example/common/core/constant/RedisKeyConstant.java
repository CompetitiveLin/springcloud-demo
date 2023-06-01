package com.example.common.core.constant;

public interface RedisKeyConstant {
    interface user {
        String USER_SIGN_IN = "user:sign_in:";
    }

    interface captcha {
        String CAPTCHA_UUID = "captcha:uuid:";

        String CAPTCHA_EMAIL_ADDRESS = "captcha:email_address:";
    }
}
