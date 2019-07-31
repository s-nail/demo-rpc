/**
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * 20110809		HYin	注释main函数
 */
/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: JRES
 * 文件名称: HSBlowfish.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *             zhengbin  建立
 * 20110809	   HYin	      注释main函数
 * 20130704    ZZhen     STORY #6238 [证券/魏文杰][TS:201306280001]-Java版T2SDK增加对密文传输许可证串的支持
 *                       添加方法encodeWithBase64和构造方法等
 * ========    =======  ============================================
 */
package com.hundsun.jrescloud.demo.rpc.server.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * hundsun BLOWFISH
 *
 * @author zhengbin
 * @version 1.0
 * @history
 */
public class HSBlowfish {

    /**
     * 日志记录接口
     */

    private static String KEYLOCATION = "client_license.dat";
    private static byte[] KEY = new byte[]{90, 65, 81, 33, 120, 115, 119, 50,
            67, 68, 69, 35, 118, 102, 114, 52};                        // "ZAQ!xsw2CDE#vfr4";
    private static byte[] encryption = null;
    private static SecretKeySpec sksSpec = null;
    private static Cipher cipher = null;

    private static void initDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException {
        sksSpec = new SecretKeySpec(KEY, "Blowfish");
        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, sksSpec);
    }

    /**
     * 使用解密算法前有新KEY，通过此方法初始化KEY.
     *
     * @param userKey
     */
    private static void setKey(byte[] userKey) {
        if (userKey != null) {
            KEY = userKey;
        }
    }

    public static byte[] getStringInputStream(InputStream is) throws IOException {
        // int bufferSize = 1024 * 100;//max size 100k
        byte[] byteArrays = new byte[256];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int length;
        while ((length = is.read(byteArrays)) > 0) {
            bos.write(byteArrays, 0, length);
        }
        return bos.toByteArray();
    }

    /**
     * 设置密钥路径
     *
     * @param userKeyLocation
     */
    private static void setEncryption(String userKeyLocation) {
        if (userKeyLocation != null && !userKeyLocation.equals("")) {
            KEYLOCATION = userKeyLocation;
        }
        try {
            encryption = getStringInputStream(new FileInputStream(KEYLOCATION));
        } catch (Exception e) {
//			ContextUtil.getServiceContext().getLogFactory().getSysLog(
//					ICEPLog.LOG_T2_CHANNEL).error("HSBlowfish::setEncryption");
        }
    }

    /**
     * 解密基于BLOWFISH加密过的BASE64字符串
     *
     * @param userKey         指定key
     * @param userKeyLocation 输入基本BASE64的字符串
     * @return
     */
    private static byte[] decode(byte[] userKey, String userKeyLocation) {
        setKey(userKey);
        try {
            setEncryption(userKeyLocation);
            return decode(encryption);
        } catch (Exception e) {
            // ContextUtil.getServiceContext().getLogFactory().getSysLog(
            // ICEPLog.LOG_T2_CHANNEL).error("HSBlowfish::decode - " + "解密出错，解密文件不对 " + e.getClass().toString());
        }
        return null;
    }

    /**
     * 解密基于BLOWFISH加密过的BASE64字符串
     *
     * @param userKey         指定key
     * @param userKeyLocation 输入基本BASE64的字符串
     * @return
     */
    private static byte[] decodeWithoutBase64(byte[] userKey, String userKeyLocation) {
        setKey(userKey);
        try {
            setEncryption(userKeyLocation);
            return decodeWithoutBase64(encryption);
        } catch (Exception e) {
//			ContextUtil.getServiceContext().getLogFactory().getSysLog(
//					ICEPLog.LOG_T2_CHANNEL).error("HSBlowfish::decode - " + "解密出错，解密文件不对 " + e.getClass().toString());
        }
        return null;
    }

    /**
     * 解密基于BLOWFISH加密过的BASE64字符串
     *
     * @param base64Code 输入基本BASE64的字符串
     * @return
     */
    public static byte[] decode(byte[] base64Code) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] encrypted = Base64.decodeBase64(base64Code);
        int length = encrypted.length;
        int superfluity = length % 8;
        byte[] encrypted_tmp = new byte[length - superfluity];
        System.arraycopy(encrypted, 0, encrypted_tmp, 0, length - superfluity);
        byte[] decrypted, decrypted_tmp = null;
        initDecrypt();
        decrypted = cipher.doFinal(encrypted_tmp);
        decrypted_tmp = new byte[length];
        System.arraycopy(decrypted, 0, decrypted_tmp, 0, decrypted.length);
        for (int i = superfluity; i > 0; i--) {
            decrypted_tmp[length - i] = (byte) ((byte) (76 + superfluity) ^ encrypted[length - i]);
        }
        return decrypted_tmp;
    }

    /**
     * 解密基于BLOWFISH加密过的BASE64字符串
     *
     * @param base64Code 输入基本BASE64的字符串
     * @return
     */
    private static byte[] decodeWithoutBase64(byte[] base64Code) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] encrypted = base64Code;
        int length = encrypted.length;
        int superfluity = length % 8;
        byte[] encrypted_tmp = new byte[length - superfluity];
        System.arraycopy(encrypted, 0, encrypted_tmp, 0, length - superfluity);
        byte[] decrypted, decrypted_tmp = null;
        initDecrypt();
        decrypted = cipher.doFinal(encrypted_tmp);
        decrypted_tmp = new byte[length];
        System.arraycopy(decrypted, 0, decrypted_tmp, 0, decrypted.length);
        for (int i = superfluity; i > 0; i--) {
            decrypted_tmp[length - i] = (byte) ((byte) (76 + superfluity) ^ encrypted[length - i]);
        }
        return decrypted_tmp;
    }

    /**
     * 根据指定密钥,加密文件路径获得许可证号
     *
     * @param userKeyLocation 加密文件绝对路径(相对路径可能会有问题),null表示默认路径（就是当前路径）
     * @return null表示解密过程中出现异常
     */
    public static String getLicenseNo(String userKeyLocation) {
        byte[] data = decode(null, userKeyLocation);
        if (data != null) {
            return new String(data);
        } else {
            return null;
        }
    }

    public static byte[] decodeData(byte[] key, String userKeyLocation) {
        return decode(key, userKeyLocation);
    }

    public static byte[] decodeDataWithoutBase64(byte[] key, String userKeyLocation) {
        return decodeWithoutBase64(key, userKeyLocation);
    }

//	public static SysLog getLog()
//	{
//		try {
//			SysLog LOG = ContextUtil.getServiceContext().getLogFactory().getSysLog(
//					ICEPLog.LOG_T2_CHANNEL);
//			return LOG;
//		} catch (Exception e) {
//			return null;
//		}
//	}


    //20130704 add by ZhaoZ
    public static byte[] encodeWithBase64(byte[] data) throws Exception {
        byte[] tmp_buff = encode(data);
        tmp_buff = Base64.encodeBase64(tmp_buff);
        return tmp_buff;
    }

    public static byte[] encode(byte[] data) throws Exception {
        if (encryptCipher == null) {
            initEncrypt();
        }
        byte[] encrypted = data;
        int length = encrypted.length;

        int superfluity = length % 8;
        byte[] encrypted_tmp = new byte[length - superfluity];
        System.arraycopy(encrypted, 0, encrypted_tmp, 0, length - superfluity);
        byte[] decrypted, decrypted_tmp = null;
        // initDecrypt();
        decrypted = encryptCipher.doFinal(encrypted_tmp);
        decrypted_tmp = new byte[length];
        System.arraycopy(decrypted, 0, decrypted_tmp, 0, decrypted.length);
        for (int i = superfluity; i > 0; i--) {
            decrypted_tmp[length - i] = (byte) ((byte) (76 + superfluity) ^ encrypted[length - i]);
        }
        // byte[] tmp_buff = Base64.encodeBase64(decrypted_tmp);
        return decrypted_tmp;
    }

    /**
     * 加密 cipher
     */
    private static Cipher encryptCipher = null;

    protected static void initEncrypt() throws Exception {
        if (encryptCipher == null) {
            encryptCipher = _initEncrypt(KEY);
        }
    }

    private static Cipher _initEncrypt(byte[] key) throws Exception {
        SecretKeySpec sksSpec = new SecretKeySpec(key, "Blowfish");
        Cipher encryptCipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, sksSpec);
        return encryptCipher;
    }

    public HSBlowfish(byte[] key) {
        if (key != null) {
            KEY = key;
        }
    }
    //20130704 add end

    public static void main(String[] args) {
        byte[] encode = null;
        try {
            //加密
            encode = HSBlowfish.encodeWithBase64("TEST".getBytes("UTF-8"));
            //解密
            byte[] decode = HSBlowfish.decode(encode);
            String str = new String(decode, "UTF-8");
            System.out.println("str：" + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("encode：" + encode);
    }
}
