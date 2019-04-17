package com.diamondfsd.codegen;

import com.diamondfsd.codegen.service.ICodeGenerator;
import com.diamondfsd.codegen.service.ITemplateRender;
import com.diamondfsd.codegen.service.impl.CodeGeneratorImpl;
import com.diamondfsd.codegen.service.impl.FreeMarkerTemplateRender;
import com.diamondfsd.codegen.vo.GeneratorConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * 快速代码生成器
 * @author Diamond
 */
@Mojo(name = "codegen")
public class SimpleCodeGen extends AbstractMojo {

    @Parameter(property = "project.basedir", readonly = true)
    private File basedir;

    /**
     * 模板路径
     */
    @Parameter(defaultValue = "classpath:/code-template")
    private String templatePath;

    /**
     * 源码默认路径
     */
    @Parameter(defaultValue = "/src/main/java/")
    private String sourcePath;

    /**
     * pojo格式 ModeName, PrimaryKeyType
     */
    @Parameter
    private String[] pojos;

    @Parameter
    private Map<String, String> params;

    @Override
    public void execute() {
        GeneratorConfig config = new GeneratorConfig();
        config.setTargetPath(basedir + sourcePath);
        ICodeGenerator codeGenerator = new CodeGeneratorImpl(config);
        ITemplateRender templateRender = new FreeMarkerTemplateRender();
        Map<String, String> params = Optional.ofNullable(this.params).orElse(Collections.emptyMap());
        try {
            for (String pojo : pojos) {
                String[] split = pojo.split(",");
                ModelDefine modelDefine = new ModelDefine(StringUtils.trimToEmpty(split[0]), StringUtils.trimToEmpty(split[1]));
                modelDefine.setParams(params);
                codeGenerator.codeGenerator(templatePath, modelDefine, templateRender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
