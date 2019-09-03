package com.fsd.um.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties properties = new Properties();
        properties.put(Constants.KAPTCHA_BORDER, "no");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "10");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH,"150");
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT,"34");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"25");
        properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, "code");
        properties.put(Constants.KAPTCHA_NOISE_IMPL,"com.google.code.kaptcha.impl.NoNoise");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");
        
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
    
}
