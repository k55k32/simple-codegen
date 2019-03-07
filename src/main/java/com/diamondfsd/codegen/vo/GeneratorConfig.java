package com.diamondfsd.codegen.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratorConfig {
   String srcPath;
   String packageName;
   Boolean useServiceInterface;
   String resourceSource;
   String controllerPackage;
   String servicePackage;
   String modelPackage;
}
