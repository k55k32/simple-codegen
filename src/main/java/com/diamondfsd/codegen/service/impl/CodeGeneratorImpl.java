package com.diamondfsd.codegen.service.impl;

import com.diamondfsd.codegen.ModelDefine;
import com.diamondfsd.codegen.service.ICodeGenerator;
import com.diamondfsd.codegen.service.ITemplateRender;
import com.diamondfsd.codegen.vo.GeneratorConfig;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author zhouchao
 * @date: 2019-04-17
 */
public class CodeGeneratorImpl implements ICodeGenerator {
    static final String CLASS_PATH_PREFIX = "classpath:";
    static final String RESOURCE_PREFIX = "/";
    static final String FILE_TARGET_PREFIX = "!#";

    private final GeneratorConfig config;

    public CodeGeneratorImpl(GeneratorConfig config) {
        this.config = config;
    }

    public static class FileWriteVO {
        private String content;
        private String targetPath;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTargetPath() {
            return targetPath;
        }

        public void setTargetPath(String targetPath) {
            this.targetPath = targetPath;
        }
    }

    /**
     * 生成代码
     */
    @Override
    public void codeGenerator(String templatePath, ModelDefine modelDefine, ITemplateRender templateRender) throws IOException {

        File file = new File(templatePath);
        if (!file.exists()) {
            throw new IOException("file not found :" + file.getAbsolutePath());
        }

        List<File> files = new ArrayList<>();
        putAllFiles(file, files);

        if (files.isEmpty()) {
            System.err.println("files is empty, not any file found in path: " + file.getAbsolutePath());
        }

        files.forEach(item -> {
            try {
                String resultString = templateRender.render(item, modelDefine);
                FileWriteVO fileWriteVO = writeFilePathToTargetPath(resultString);
                if (fileWriteVO == null) {
                    System.err.println("file template not target define: " + item.getAbsolutePath());
                } else {
                    writeFile(fileWriteVO);
                }
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeFile(FileWriteVO fileWriteVO) throws IOException {
        File file = new File(fileWriteVO.getTargetPath());
        if (file.exists()) {
            System.err.println("file exists: " + file.getAbsolutePath());
        } else {
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
                if (!mkdirs) {
                    throw new IOException("create dir failed: " + file.getParentFile().getAbsolutePath());
                }
            }
            boolean newFile = file.createNewFile();
            if (!newFile) {
                throw new IOException("create new file failed: " + file.getAbsolutePath());
            }
            try (FileOutputStream output = new FileOutputStream(file)){
                output.write(StringUtils.trimToEmpty(fileWriteVO.getContent()).getBytes());
                output.flush();
                System.out.println("write file success: " + file.getAbsolutePath());
            }
        }
    }

    private FileWriteVO writeFilePathToTargetPath(String resultString) {
        String[] allLines = resultString.split("\n");
        String targetPathDefine = allLines[0];
        if (targetPathDefine.startsWith(FILE_TARGET_PREFIX)) {
            FileWriteVO fileWriteVO = new FileWriteVO();
            fileWriteVO.setContent(resultString.substring(targetPathDefine.length()));
            String targetPath = targetPathDefine.substring(FILE_TARGET_PREFIX.length());
            String basePath = config.getTargetPath();
            if (!basePath.endsWith("/")) {
                basePath = basePath + "/";
            }
            String absPath = basePath + StringUtils.trimToEmpty(targetPath);
            fileWriteVO.setTargetPath(absPath);
            return fileWriteVO;
        }
        return null;
    }

    private void putAllFiles(File file, List<File> files) {
        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                putAllFiles(listFile, files);
            }
        } else {
            files.add(file);
        }
    }
}
