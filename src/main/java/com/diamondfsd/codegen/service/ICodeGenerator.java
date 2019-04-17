package com.diamondfsd.codegen.service;

import com.diamondfsd.codegen.ModelDefine;

import java.io.IOException;

/**
 * 代码生成器
 * @author zhouchao
 * @date: 2019-04-17
 */
public interface ICodeGenerator {
    /**
     * 生成文件
     * @param templatePath 模板路径
     * @param modelDefine 模型定义
     * @param templateRender 模板渲染类
     * @throws IOException
     */
    void codeGenerator(String templatePath, ModelDefine modelDefine, ITemplateRender templateRender) throws IOException;
}
