package com.example.common.core.constant;

public interface RedisKeyConstant {
    interface user {
        String USER_CHECKOUT = "user:checkout:";

        String USER_CHECKOUT_RANK = "user:checkout:rank:";
    }

    interface captcha {
        String CAPTCHA_UUID = "captcha:uuid:";

        String CAPTCHA_EMAIL_ADDRESS = "captcha:email_address:";
    }
}
