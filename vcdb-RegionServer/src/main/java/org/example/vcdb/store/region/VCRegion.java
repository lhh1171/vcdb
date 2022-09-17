package org.example.vcdb.store.region;

import java.util.List;

/**
 * @ClassName VCRegion
 * @Description TODO
 * @Author lqc
 * @Date 2022/8/14 上午11:35
 * @Version 1.0
 */
//按rowKey切分，里面包含了几个列族的数据(其中n列的数据)
/*Table                    (VCDB table)
Region               (Regions for the table)
    Store            (Store per ColumnFamily for each Region for the table)
        MemStore     (MemStore for each Store for each Region for the table)
        FileStore    (StoreFiles for each Store for each Region for the table)
            KV    (Blocks within a StoreFile within a Store for each Region for the table)*/
    /*表---》rowKey----》列族*/
public class VCRegion {
    RegionServerMeta regionServerMeta;
    //regionID,VCRegionStores
    byte[] regionServerID;
    List<FileStore> fileStores;
    List<FileStoreMeta> fileStoreMetas;
}
