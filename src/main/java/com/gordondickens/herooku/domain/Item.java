package com.gordondickens.herooku.domain;

import javax.validation.constraints.Size;
import org.springframework.roo.addon.entity.RooJpaEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Item {

    @Size(min = 3, max = 30)
    private String name;

    @Size(max = 255)
    private String description;
}
