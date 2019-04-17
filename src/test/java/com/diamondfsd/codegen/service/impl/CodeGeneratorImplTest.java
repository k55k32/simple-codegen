package com.diamondfsd.codegen.service.impl;

import com.diamondfsd.codegen.ModelDefine;
import com.diamondfsd.codegen.vo.GeneratorConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * @author zhouchao
 * @date: 2019-04-17
 */
public class CodeGeneratorImplTest {

    String targetPath = this.getClass().getResource("/").getPath() + "/generator-code-test";

    @Test
    public void codeGenerator() throws IOException {
        File genFile = new File(targetPath + "/com/diamondfsd/report/mvc/controllers/GrReportController.java");
        boolean del = genFile.exists() && genFile.delete();
        System.out.println("is file exists: " + del);
        GeneratorConfig config = new GeneratorConfig();
        config.setTargetPath(targetPath);
        config.setBaseDir(this.getClass().getResource("/").getPath());
        CodeGeneratorImpl codeGenerator = new CodeGeneratorImpl(config);
        ModelDefine modelDefine = new ModelDefine("GrReport", "Integer");
        codeGenerator.codeGenerator(new File("").getAbsolutePath() + "/src/test/resources/", modelDefine, new FreeMarkerTemplateRender());
        Assert.assertTrue(genFile.exists());
        Assert.assertTrue(genFile.isFile());
        FileInputStream inputStream = new FileInputStream(genFile);
        InputStreamReader re = new InputStreamReader(inputStream);
        BufferedReader r = new BufferedReader(re);
        Assert.assertEquals("package com.diamondfsd.report.mvc.controllers;", r.readLine());
        Assert.assertEquals("", r.readLine());
        Assert.assertEquals("import com.diamondfsd.fast.dao.entity.PageResult;", r.readLine());
        Assert.assertEquals("import com.diamondfsd.report.model.GrReport;", r.readLine());
        Assert.assertEquals("import com.diamondfsd.report.services.GrReportService;", r.readLine());


    }
}