package com.diamondfsd.codegen.vo;



public class GeneratorConfig {
   private String srcPath;
   private String packageName;
   private Boolean useServiceInterface;
   private String resourceSource;
   private String controllerPackage;
   private String servicePackage;
   private String modelPackage;

   public String getSrcPath() {
      return srcPath;
   }

   public void setSrcPath(String srcPath) {
      this.srcPath = srcPath;
   }

   public String getPackageName() {
      return packageName;
   }

   public void setPackageName(String packageName) {
      this.packageName = packageName;
   }

   public Boolean getUseServiceInterface() {
      return useServiceInterface;
   }

   public void setUseServiceInterface(Boolean useServiceInterface) {
      this.useServiceInterface = useServiceInterface;
   }

   public String getResourceSource() {
      return resourceSource;
   }

   public void setResourceSource(String resourceSource) {
      this.resourceSource = resourceSource;
   }

   public String getControllerPackage() {
      return controllerPackage;
   }

   public void setControllerPackage(String controllerPackage) {
      this.controllerPackage = controllerPackage;
   }

   public String getServicePackage() {
      return servicePackage;
   }

   public void setServicePackage(String servicePackage) {
      this.servicePackage = servicePackage;
   }

   public String getModelPackage() {
      return modelPackage;
   }

   public void setModelPackage(String modelPackage) {
      this.modelPackage = modelPackage;
   }
}
