package com.diamondfsd.codegen;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.diamondfsd.codegen.utils.CurdGenerator;
import com.diamondfsd.codegen.vo.GeneratorConfig;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Arrays;

/**
 * 闪回收代码生成器
 *
 * @author Diamond
 */
@Mojo(name = "gen")
public class SimpleCodeGen extends AbstractMojo {

    @Parameter(property = "project.basedir", readonly = true)
    private File basedir;

    @Parameter(required = true)
    private String packageName;

    @Parameter(defaultValue = "mvc.controllers")
    private String controllerPackage;

    @Parameter(defaultValue = "services")
    private String servicePackage;

    @Parameter(defaultValue = "model")
    private String modelPackage;

    @Parameter(defaultValue = "false")
    private Boolean useServiceInterface;

    @Parameter
    private String[] pojos;

    @Override
    public void execute() {
        System.out.println("useServiceInterface: " + useServiceInterface);
        System.out.println("execute maven info: " + packageName);
        System.out.println("basedir:" + basedir);
        System.out.println("gen pojo: " + Arrays.asList(pojos));
        String srcPath = basedir + "/src/main/java/";
        String resourceSource = basedir + "/src/main/resources/";

        GeneratorConfig config = GeneratorConfig.builder()
                .packageName(packageName)
                .srcPath(srcPath)
                .useServiceInterface(useServiceInterface)
                .resourceSource(resourceSource)
                .controllerPackage(controllerPackage)
                .servicePackage(servicePackage)
                .modelPackage(modelPackage)
                .build();

        CurdGenerator curdGenerator = new CurdGenerator(config);
        try {
            curdGenerator.codeGenerator(pojos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
