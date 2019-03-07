package com.diamondfsd.codegen;

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
@Mojo(name = "codegen")
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
    public void execute() throws MojoExecutionException {
        System.out.println("useServiceInterface: " + useServiceInterface);
        System.out.println("execute maven info: " + packageName);
        System.out.println("basedir:" + basedir);
        System.out.println("gen pojo: " + Arrays.asList(pojos));
        String srcPath = basedir + "/src/main/java/";
        String resourceSource = basedir + "/src/main/resources/";

        GeneratorConfig config = new GeneratorConfig();
        config.setPackageName(packageName);
        config.setSrcPath(srcPath);
        config.setUseServiceInterface(useServiceInterface);
        config.setResourceSource(resourceSource);
        config.setControllerPackage(controllerPackage);
        config.setServicePackage(servicePackage);
        config.setModelPackage(modelPackage);

        CurdGenerator curdGenerator = new CurdGenerator(config);
        try {
            curdGenerator.codeGenerator(pojos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
