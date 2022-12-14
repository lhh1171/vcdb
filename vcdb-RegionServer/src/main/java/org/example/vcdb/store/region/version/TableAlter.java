package org.example.vcdb.store.region.version;

import org.example.vcdb.util.Bytes;

import java.util.Arrays;

/**
 * @ClassName TableAlter
 * @Description TODO
 * @Author lqc
 * @Date 2022/10/20 下午6:23
 * @Version 1.0
 */

public class TableAlter {
    byte[] data;
    public TableAlter(long timestamp, byte method, byte type,boolean unique, boolean isNull,
                      String min, String max, String tableName,
                      String cfName, String oldCfName){
        data=new byte[8+1+1+1+1+
                4+min.getBytes().length+
                4+max.getBytes().length+
                4+tableName.getBytes().length+
                4+cfName.getBytes().length+
                4+oldCfName.getBytes().length];
        int pos=0;
        byte uni=0;
        byte isNil=0;
        if (unique){
            uni=1;
        }
        if (isNull){
            isNil=1;
        }
        pos= Bytes.putLong(this.data,pos,timestamp);

        pos= Bytes.putByte(this.data,pos,method);

        pos= Bytes.putByte(this.data,pos,type);

        pos= Bytes.putByte(this.data,pos,uni);

        pos= Bytes.putByte(this.data,pos,isNil);

        pos= Bytes.putInt(this.data,pos,min.getBytes().length);
        pos=Bytes.putBytes(this.data,pos,min.getBytes(),0,min.getBytes().length);

        pos= Bytes.putInt(this.data,pos,max.getBytes().length);
        pos=Bytes.putBytes(this.data,pos,max.getBytes(),0,max.getBytes().length);

        pos= Bytes.putInt(this.data,pos,tableName.getBytes().length);
        pos=Bytes.putBytes(this.data,pos,tableName.getBytes(),0,tableName.getBytes().length);

        pos= Bytes.putInt(this.data,pos,cfName.getBytes().length);
        pos=Bytes.putBytes(this.data,pos,cfName.getBytes(),0,cfName.getBytes().length);

        pos= Bytes.putInt(this.data,pos,oldCfName.getBytes().length);
        pos=Bytes.putBytes(this.data,pos,oldCfName.getBytes(),0,oldCfName.getBytes().length);
    }

    public long getTime(){
        return Bytes.toLong(this.data,0,8);
    }

    public byte getMethod(){
        return this.data[8];
    }

    public byte getType(){
        return this.data[9];
    }

    public boolean getUni(){
        return this.data[10]==1;
    }

    public boolean getNil(){
        return this.data[11]==1;
    }

    public int getMinLength(){
        return Bytes.toInt(this.data,12,4);
    }

    public String getMin(){
        return Bytes.toString(this.data,16,getMinLength());
    }

    public int getMaxLength(){
        return Bytes.toInt(this.data,16+getMinLength(),4);
    }

    public String getMax(){
        return Bytes.toString(this.data,20+getMinLength(),getMaxLength());
    }

    public int getTabLength(){
        return Bytes.toInt(this.data,20+getMinLength()+getMaxLength(),4);
    }

    public String getTab(){
        return Bytes.toString(this.data,24+getMinLength()+getMaxLength(),getTabLength());
    }

    public int getCfLength(){
        return Bytes.toInt(this.data,24+getMinLength()+getMaxLength()+getTabLength(),4);
    }

    public String getCf(){
        return Bytes.toString(this.data,
                28+getMinLength()+getMaxLength()+getTabLength(),getCfLength());
    }

    public int getOldCfLength(){
        return Bytes.toInt(this.data,28+getMinLength()+getMaxLength()+getTabLength()+getCfLength(),4);
    }

    public String getOldCf(){
        return Bytes.toString(this.data,32+getMinLength()+getMaxLength()+getTabLength()+getCfLength(),getOldCfLength());
    }

    public TableAlter(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TableAlter{" +
                "time=" + getTime() +
                "method=" + getMethod() +
                "type=" + getType() +
                "unique=" + getUni() +
                "isNil=" + getNil() +
                "min=" + getMin() +
                "max=" + getMax() +
                "tableName=" + getTab() +
                "cfName=" + getCf() +
                "oldCf=" + getOldCf() +
                '}';
    }
}
