package com.diamondfsd.codegen;

import com.diamondfsd.codegen.service.ICodeGenerator;
import com.diamondfsd.codegen.service.ITemplateRender;
import com.diamondfsd.codegen.service.impl.CodeGeneratorImpl;
import com.diamondfsd.codegen.service.impl.FreeMarkerTemplateRender;
import com.diamondfsd.codegen.vo.GeneratorConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * 闪回收代码生成器
 *
 * @author Diamond
 */
@Mojo(name = "codegen")
public class SimpleCodeGen extends AbstractMojo {

    @Parameter(property = "project.basedir", readonly = true)
    private File basedir;

    @Parameter(defaultValue = "classpath:/code-template")
    private String codeTempaltePath;

    @Parameter(defaultValue = "/src/main/java/")
    private String sourcePath;

    @Parameter
    private String[] pojos;

    @Override
    public void execute() {
        GeneratorConfig config = new GeneratorConfig();
        config.setTargetPath(basedir + sourcePath);
        ICodeGenerator codeGenerator = new CodeGeneratorImpl(config);
        ITemplateRender templateRender = new FreeMarkerTemplateRender();
        try {
            for (String pojo : pojos) {
                String[] split = pojo.split(",");
                ModelDefine modelDefine = new ModelDefine(StringUtils.trimToEmpty(split[0]),
                        StringUtils.trimToEmpty(split[1]));
                codeGenerator.codeGenerator(codeTempaltePath, modelDefine, templateRender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
