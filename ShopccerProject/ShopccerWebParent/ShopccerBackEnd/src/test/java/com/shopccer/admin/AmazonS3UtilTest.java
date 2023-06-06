package com.shopccer.admin;

import com.shopccer.admin.utils.AmazonS3Util;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AmazonS3UtilTest {

    @Test
    public void testListFolder() {
        String folderName = "fotos-productos/3";
        List<String> listKeys = AmazonS3Util.listFolder(folderName);
        listKeys.forEach(System.out::println);
    }
}
