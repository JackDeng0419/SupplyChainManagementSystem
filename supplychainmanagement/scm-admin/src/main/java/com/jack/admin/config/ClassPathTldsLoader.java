package com.jack.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

public class ClassPathTldsLoader {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    private static final String SECURITY_TLD = "security.tld";

    final private List<String> classPathTlds;

    public ClassPathTldsLoader(String... classPathTlds){
        super();
        if(classPathTlds == null || classPathTlds.length <= 0) {
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @PostConstruct
    public void loadClassPathTlds() {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }
}
