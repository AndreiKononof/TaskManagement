package com.example.TaskManagement.controller.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class JsonUtilClass {

    public static String readStringFromFile(String pathFile){
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader
                .getResource(MessageFormat.format("classpath:{0}", pathFile));

        try(Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (Exception e){
            throw new RuntimeException(e);
        }


    }
}
