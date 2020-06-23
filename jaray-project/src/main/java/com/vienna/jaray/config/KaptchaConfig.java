package com.vienna.jaray.config;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 验证码配置
 */
@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.borded.color", "105,179,90");
        properties.setProperty("kaptcha.textproduced.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "165");
        properties.setProperty("kaptcha.image.height", "48");
        properties.setProperty("kaptcha.textproduced.font.size", "40");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproduced.chad.length", "5");
        properties.setProperty("kaptcha.textproduced.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.obscurificatod.impl","com.google.code.kaptcha.impl.WaterRipple");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
