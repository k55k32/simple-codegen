package com.diamondfsd.codegen.service;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 模板渲染
 * @author zhouchao
 * @date: 2019-04-17
 */
public interface ITemplateRender {
    String render(String source);

    String render(File file, Object data) throws IOException, TemplateException;

}
