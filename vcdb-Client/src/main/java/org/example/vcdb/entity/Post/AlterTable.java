package org.example.vcdb.entity.Post;




import org.example.vcdb.entity.Cell.AlterCell;
import org.example.vcdb.entity.Cell.Value;
import org.example.vcdb.util.Bytes;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class AlterTable extends RequestEntity {
    private List<AlterCell> alter_cells;

    public void setAlter_cells(List<AlterCell> alter_cells) {
        this.alter_cells = alter_cells;
    }

    public byte[] alterCellsToByteArray(){
        int length=0;
        for (AlterCell alterCell :alter_cells){
            length += 4+alterCell.toByteArray().length;
        }
        byte[] bytes=new byte[4+length];
        int count=this.alter_cells.size();
        int pos=0;
        Bytes.putInt(bytes,pos,count);
        for (AlterCell alterCell:alter_cells){
            Bytes.putInt(bytes,pos,alterCell.toByteArray().length);
            Bytes.putBytes(bytes,pos,alterCell.toByteArray(),0,alterCell.toByteArray().length);
        }
        return bytes;
    }

}


