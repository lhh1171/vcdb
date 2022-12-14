package org.example.vcdb.store.region.Region;

import org.example.vcdb.store.mem.KV;
import org.example.vcdb.store.region.Region.RegionMeta;
import org.example.vcdb.store.region.fileStore.FileStore;
import org.example.vcdb.store.region.fileStore.FileStoreMeta;
import org.example.vcdb.store.region.fileStore.KVRange;
import org.example.vcdb.util.Bytes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    /*一般来说一个region就对应一个表*/
public class VCRegion {
    String regionMetaName;
    RegionMeta regionMeta;
    List<FileStoreMeta> fileStoreMetas;
    List<FileStore> fileStores;
}
