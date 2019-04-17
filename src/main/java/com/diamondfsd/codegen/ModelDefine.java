package com.diamondfsd.codegen;

/**
 * @author zhouchao
 * @date: 2019-04-17
 */
public class ModelDefine {
    /**
     * 全名例如  ModelDefine
     */
    private String name;
    /**
     * 通过横杠分割后的  model-define
     */
    private String nameSplit;
    /**
     * 驼峰  modelDefine
     */
    private String nameCamelCase;

    private String primaryKeyType;

    public ModelDefine(String name, String primaryKeyType) {
        this.setName(name);
        this.primaryKeyType = primaryKeyType;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        this.primaryKeyType = primaryKeyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        processPreDefineName(name);
        this.name = name;
    }

    private void processPreDefineName(String name) {
        StringBuilder nameSplitBuilder = new StringBuilder();
        this.nameCamelCase = name.substring(0, 1).toLowerCase() + name.substring(1);
        for (int i = 0; i < nameCamelCase.length(); i++) {
            char item = nameCamelCase.charAt(i);
            if (nameCamelCase.charAt(i) >= 'A' && nameCamelCase.charAt(i) <= 'Z') {
                item -= 'A' - 'a';
                nameSplitBuilder.append("-");
            }
            nameSplitBuilder.append(item);
        }
        this.nameSplit = nameSplitBuilder.toString();
    }

    public String getNameSplit() {
        return nameSplit;
    }

    public void setNameSplit(String nameSplit) {
        this.nameSplit = nameSplit;
    }

    public String getNameCamelCase() {
        return nameCamelCase;
    }

    public void setNameCamelCase(String nameCamelCase) {
        this.nameCamelCase = nameCamelCase;
    }
}
