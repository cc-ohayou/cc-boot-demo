package com.qunhe.toilet.facade.domain.common.enums.toilet;

/**
 * @Author bupo
 * @DATE 2020/8/21 15:27
 * @Description
 */
public enum SexTypeEnum {


    WOMAN(0,"woman","女士"),
    MAN(1,"man","男士"),
    BOTH(2,"both","共用型"),
    ;

    private int  typeId;
    private String  dbType;
    private String  describe;


    SexTypeEnum(final int typeId, final String dbType, final String describe) {
        this.typeId = typeId;
        this.dbType = dbType;
        this.describe = describe;
    }


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }

    public String getDbType() {
        return dbType;
    }
    public void setDbType(final String dbType) {
        this.dbType = dbType;
    }


    public static String   getTypeDescById(int id){
        for(SexTypeEnum   sexTypeEnum:SexTypeEnum.values()){
            if(sexTypeEnum.getTypeId()==id){
                return  sexTypeEnum.getDbType();
            }
        }
        return "";
    }




    public String getDescribe() {
        return describe;
    }

    public void setDescribe(final String describe) {
        this.describe = describe;
    }
}
