package com.diamondfsd.codegen.utils;

import com.diamondfsd.codegen.vo.GeneratorConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CurdGenerator {

    private GeneratorConfig config;

    public CurdGenerator(GeneratorConfig config) {
        this.config = config;
    }

    public void codeGenerator(String[] args) throws IOException {
        final String ControllerTemplate = config.getResourceSource() + "/curd-temp/ContronllerTemplate.temp";
        final String ServiceTemplate = config.getResourceSource() + "/curd-temp/ServiceTemplate.temp";
        final String ModelTemplate = config.getResourceSource() + "/curd-temp/ModelTemplate.temp";
        final String ServiceInterfaceTemplate = config.getResourceSource() + "/curd-temp/ServiceTemplateInterface.temp";

        final String controllerPackage = config.getPackageName() + "." + config.getControllerPackage();
        final String servicePackage = config.getPackageName() + "." + config.getServicePackage() + ".impl";
        final String serviceInterfacePackage = config.getPackageName() + "." + config.getServicePackage();
        final String modelPackage = config.getPackageName() + "." + config.getModelPackage();
        if (args.length > 0) {
            Arrays.asList(args).forEach(pojo -> {
                GeneratorCodeByModelClass(pojo, ControllerTemplate,
                        ServiceTemplate, ModelTemplate, controllerPackage,
                        servicePackage, modelPackage,
                        ServiceInterfaceTemplate, serviceInterfacePackage);
                System.out.println("Generator \"" + pojo + "\" finish");
            });
        }
    }

    private final void GeneratorCodeByModelClass(String modelPojoName, String contronllerTemplatePath,
                                                 String ServiceTemplate, String ModelTemplate,
                                                 String controllerPackage, String servicePackage,
                                                 String modelPackage, String ServiceInterfaceTemplate,
                                                 String serviceInterfacePackage) {
        String primaryType = "String";
        modelPojoName = covertToUp(modelPojoName);
        System.out.println("begin to gen code : " + modelPojoName);

        if (modelPojoName.contains(",")) {
            String[] params = modelPojoName.split(",");
            modelPojoName = params[0];
            primaryType = params[1];
        }
        if (!modelPojoName.endsWith("Pojo")) {
            modelPojoName = modelPojoName + "Pojo";
        }
        String modelName = modelPojoName.substring(0, modelPojoName.lastIndexOf("Pojo"));
        String serviceClass = modelName + "Service";
        String controllerClass = modelName + "Controller";
        String serviceName = serviceClass.substring(0, 1).toLowerCase() + serviceClass.substring(1);
        String mappingName = humpToUnderline(modelName, "-");
        String tableName = humpToUnderline(modelName, null).toUpperCase();
        Map<String, String> map = new HashMap<>();
        map.put("modelPojoName", modelPojoName);
        map.put("model", modelName);
        map.put("serviceClass", serviceClass);
        map.put("serviceName", serviceName);
        map.put("packageName", config.getPackageName());
        map.put("mappingName", mappingName);
        map.put("primaryType", primaryType);
        map.put("controllerClass", controllerClass);
        map.put("tableName", tableName);
        try {
            String controllerFileContent = TemplateRenderUtil.renderFile(contronllerTemplatePath, map);
            writeContentToPackage(controllerFileContent, controllerPackage, controllerClass);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        try {
            String serviceFileContent = TemplateRenderUtil.renderFile(ServiceInterfaceTemplate, map);
            writeContentToPackage(serviceFileContent, serviceInterfacePackage, serviceClass);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        if (config.getUseServiceInterface()) {
            try {
                String serviceFileContent = TemplateRenderUtil.renderFile(ServiceTemplate, map);
                writeContentToPackage(serviceFileContent, servicePackage, serviceClass + "Impl");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            String modelFileContent = TemplateRenderUtil.renderFile(ModelTemplate, map);
            writeContentToPackage(modelFileContent, modelPackage, modelName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void writeContentToPackage(String content, String packageName, String fileName) throws IOException {
        String targetFilePath = config.getSrcPath() + "/" + packageName.replaceAll("\\.", "/") + "/";
        if (!new File(targetFilePath).exists()) {
            new File(targetFilePath).mkdirs();
        }
        targetFilePath += fileName + ".java";
        writeStringToPath(content, targetFilePath);
    }

    public void writeStringToPath(String content, String targetFilePath) throws IOException {
        File file = new File(targetFilePath);
        if (file.exists()) {
            System.err.println(file.getAbsolutePath() + " is exists");
        } else {
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.flush();
            out.close();
            System.out.println("write to" + file.getAbsolutePath() + " success");
        }
    }

    private static String humpToUnderline(String humpString, String underline) {
        underline = Optional.ofNullable(underline).orElse("_");
        if (humpString == null || humpString.trim().length() == 0) {
            return "";
        }
        ;
        String reg = "[A-Z]";
        Matcher matcher = Pattern.compile(reg).matcher(humpString);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, underline + matcher.group().toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.charAt(0) == underline.charAt(0)) {
            sb.delete(0, 1);
        }
        return sb.toString();
    }

    private String covertToUp(String modelPojoName) {
        return Stream.of(modelPojoName.split("_"))
                .map(a -> a.substring(0, 1).toUpperCase() + a.substring(1, a.length()))
                .reduce((a, b) -> a + b).orElse(modelPojoName);
    }
}
