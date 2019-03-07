package com.diamondfsd.codegen.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratorConfig {
   private String srcPath;
   private String packageName;
   private Boolean useServiceInterface;
   private String resourceSource;
   private String controllerPackage;
   private String servicePackage;
   private String modelPackage;
}
