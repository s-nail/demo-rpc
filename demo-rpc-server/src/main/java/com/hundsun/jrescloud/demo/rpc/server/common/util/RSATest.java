package com.hundsun.jrescloud.demo.rpc.server.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.security.KeyPair;

/**
 * Created by jiayq24996 on 2019-08-15
 */
public class RSATest {

    public static void main(String[] args) {
       /* RSA rsa = new RSA();

        //获得私钥
        System.out.println("---------" + rsa.getPrivateKey());
        System.out.println("---------" + rsa.getPrivateKeyBase64());

        //获得公钥
        System.out.println("---------" + rsa.getPublicKey());
        System.out.println("---------" + rsa.getPublicKeyBase64());


        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);

        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        System.out.println();
        System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);

        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
        System.out.println();
        System.out.println(StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));*/

        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        String PRIVATE_KEY = Base64.encode(pair.getPrivate().getEncoded());
        String PUBLIC_KEY = Base64.encode(pair.getPublic().getEncoded());
        System.out.println("PRIVATE_KEY:" + PRIVATE_KEY);
        System.out.println("PUBLIC_KEY:" + PUBLIC_KEY);
        RSA rsa = new RSA(PRIVATE_KEY, null);
        RSA rsa1 = new RSA(null, PUBLIC_KEY);
        String str = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnRtM9+pWlbmy+d/nlataUXHQAMYK6h6IYhRiC3FGF/zTDsKBGtxoy/bYL/0+0qBum7e9E+xEeDid0A5rf1nDTEatkoB/OSFPD1KM/OT+Oj1/rTBFmd1tVWCjzs9Soo9IonwYA4s3REv8atMQJzZDwcC+Iro+QB/UoVD0icJlrzwIDAQAB";
        RSA rsa2 = new RSA(null, str);

        //公钥加密，私钥解密
        byte[] encrypt = rsa1.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);

        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        System.out.println();
        System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------");

        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试BBBB", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa1.decrypt(encrypt2, KeyType.PublicKey);
        System.out.println(StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------");
        byte[] decrypt3 = rsa2.decrypt(encrypt2, KeyType.PublicKey);
        System.out.println(StrUtil.str(decrypt3, CharsetUtil.CHARSET_UTF_8));
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(IdUtil.createSnowflake(1, 1).nextId());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(IdUtil.createSnowflake(1, 1).nextId());
    }
}
