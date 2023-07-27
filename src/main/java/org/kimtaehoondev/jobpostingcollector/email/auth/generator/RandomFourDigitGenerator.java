package org.kimtaehoondev.jobpostingcollector.email.auth.generator;

import org.springframework.stereotype.Component;

@Component
public class RandomFourDigitGenerator implements AuthCodeGenerator {
    @Override
    public String generate() {
        int randomNum = (int) (Math.random() * 8999 + 1000);
        return String.valueOf(randomNum);
    }
}
