package com.diamondfsd.codegen.service.impl;

import com.diamondfsd.codegen.service.ITemplateRender;
import freemarker.template.Configuration;
import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * @author zhouchao
 * @date: 2019-04-17
 */
public class FreeMarkerTemplateRender implements ITemplateRender {
    Configuration configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    @Override
    public String render(String source) {
        return null;
    }

    @Override
    public String render(File file, Object data) throws IOException, TemplateException {
        Template template = new Template(file.getName(), new FileReader(file), configuration);
        try (StringWriter writer = new StringWriter()) {
            template.process(data, writer);
            return writer.getBuffer().toString();
        }
    }
}
