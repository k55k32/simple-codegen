!# com/diamondfsd/report/mvc/controllers/${name}Controller.java
<#assign serviceName = name + "Service">
<#assign serviceNameCs = nameCamelCase + "Service">
package com.diamondfsd.report.mvc.controllers;

import com.diamondfsd.fast.dao.entity.PageResult;
import com.diamondfsd.report.model.${name};
import com.diamondfsd.report.services.${serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("${nameSplit}")
public class ${name}Controller {

    @Autowired
    private  ${serviceName} ${serviceNameCs};

    @PostMapping("save")
    public ${name} save(${name} model) {
        model.setCreateTime(null);
        model.setUpdateTime(null);
        return ${serviceName}.save(model);
    }

    @PostMapping("update")
    public ${name} update(${name} model) {
        model.setCreateTime(null);
        model.setUpdateTime(null);
        return ${serviceName}.update(model);
    }

    @PostMapping("get-by-id")
    public ${name} getById(@NotNull ${primaryKeyType} id) {
        return ${serviceName}.get(id);
    }

    @PostMapping("page")
    public PageResult<${name}> page(PageResult<${name}> page) {
        return ${serviceName}.page(page);
    }

    @PostMapping("delete")
    public void delete(@NotNull ${primaryKeyType} id) {
        ${serviceName}.delete(id);
    }

    @PostMapping("find-all")
    public List<${name}> findAll() {
        return ${serviceName}.findAll();
    }
}
