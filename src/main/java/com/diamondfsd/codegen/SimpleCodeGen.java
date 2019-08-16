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
    @Parameter(defaultValue = "src/main/resources/code-template")
    private String templatePath;

    /**
     * 源码默认路径
     */
    @Parameter(defaultValue = "src/main/java/")
    private String sourcePath;

    @Parameter
    private String packageName;


    /**
     * pojo格式 ModeName, PrimaryKeyType
     */
    @Parameter
    private String[] pojos;

    @Parameter
    private Map<String, String> params;

    public SimpleCodeGen() {
    }

    @Override
    public void execute() {
        GeneratorConfig config = new GeneratorConfig();
        config.setBaseDir(basedir.getAbsolutePath());
        config.setTargetPath(ifAbsolutePath(sourcePath, basedir.getAbsolutePath()));
        ICodeGenerator codeGenerator = new CodeGeneratorImpl(config);
        ITemplateRender templateRender = new FreeMarkerTemplateRender();
        Map<String, String> params = Optional.ofNullable(this.params).orElse(Collections.emptyMap());

        try {
            for (String pojo : pojos) {
                String[] split = pojo.split(",");
                ModelDefine modelDefine = new ModelDefine(StringUtils.trimToEmpty(split[0]), StringUtils.trimToEmpty(split[1]));
                modelDefine.setParams(params);
                modelDefine.setPackageName(packageName);

                codeGenerator.codeGenerator(ifAbsolutePath(templatePath, basedir.getAbsolutePath()), modelDefine, templateRender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String ifAbsolutePath(String path, String basePath) {
        boolean isAbsolutePath = path.startsWith("/") || path.startsWith("\\") || path.contains(":");
        if (isAbsolutePath) {
            return path;
        }
        return basePath + "/" + path;
    }
}
