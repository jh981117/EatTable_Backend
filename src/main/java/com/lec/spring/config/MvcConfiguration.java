package com.lec.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration {






    public static class LocalMvcConfiguration implements WebMvcConfigurer {
        @Value("${app.upload_user.path}")
        private String userUploadDir;

        @Value("${app.upload_partner.path}")
        private String partnerUploadDir;

        @Value("${app.upload_menu.path}")
        private String menuUploadDir;


        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            System.out.println("어테치먼트 핸들러");

          registry
                  .addResourceHandler("/upload_user/**")
                  .addResourceLocations("file:" + userUploadDir + "/");

          registry
                  .addResourceHandler("/upload_partner.path/**")
                  .addResourceLocations("file:" + partnerUploadDir + "/");

          registry
                  .addResourceHandler("/upload_menu.path/**")
                  .addResourceLocations("file:" + menuUploadDir + "/");
        }


    }
}
