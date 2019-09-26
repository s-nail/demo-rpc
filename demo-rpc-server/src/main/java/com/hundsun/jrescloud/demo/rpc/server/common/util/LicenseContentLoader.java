package com.hundsun.jrescloud.demo.rpc.server.common.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.hundsun.jrescloud.common.util.ConfigUtils;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class LicenseContentLoader {
    private static Logger logger = LoggerFactory.getLogger(LicenseContentLoader.class);
    //private static final String LICENSE_NO = ConfigUtils.get("hs.license.licenceNo", String.class);
    //private static final String MODULE_NAME = ConfigUtils.get("app.name", String.class);
    private static final String PERMIT_CENTER_SERVER_IP = ConfigUtils.get("hs.permit-center.server.ip", String.class);
    private static final String PERMIT_CENTER_SERVER_PORT = ConfigUtils.get("hs.permit-center.server.port", String.class);
    private static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnRtM9+pWlbmy+d/nlataUXHQAMYK6h6IYhRiC3FGF/zTDsKBGtxoy/bYL/0+0qBum7e9E+xEeDid0A5rf1nDTEatkoB/OSFPD1KM/OT+Oj1/rTBFmd1tVWCjzs9Soo9IonwYA4s3REv8atMQJzZDwcC+Iro+QB/UoVD0icJlrzwIDAQAB";
    private static final int REQUEST_FAILED_TIMES = 3;
    private static final String MQ_NOTICE_DEFAULT_VALUE = "111";

    private static class LicenseContentLoaderHolder {
        private static LicenseContentLoader loader = new LicenseContentLoader();
    }

    public static LicenseContentLoader getInstance() {
        return LicenseContentLoaderHolder.loader;
    }

    /**
     * 第一次通过HTTP调用，默认notice值
     *
     * @return
     */
    public String callPermitCenterApi() {
        return this.callPermitCenterApi("", getGsv(), MQ_NOTICE_DEFAULT_VALUE);
    }

    /**
     * 调用许可中心接口
     *
     * @param notice
     * @return
     */
    public String callPermitCenterApi(String licenceNo, String gsv, String notice) {
        //1.HTTP请求许可中心获取对应系统的许可文件
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("gsv", gsv));
        params.add(new BasicNameValuePair("notice", notice));
        params.add(new BasicNameValuePair("licenceNo", licenceNo));
        String licenceInfo = null;
        int i = 0;
        while (i < REQUEST_FAILED_TIMES) {
            try {
                licenceInfo = HttpClientUpgradesUtil.executePOST("http://" + PERMIT_CENTER_SERVER_IP + ":" + PERMIT_CENTER_SERVER_PORT + "/permit/toSDK", params);
                break;
            } catch (Exception e) {
                logger.error("第" + (++i) + "次HTTP请求许可中心失败，请检查配置文件，确认许可证编号和许可中心服务IP、Port是否正确! 异常信息：" + e.getMessage());
            }
        }
        return licenceInfo;
    }

    /**
     * 初始化加载license.lic文件内容
     *
     * @param licenseEncodeStr 加密字符串
     */
    public void init(String licenseEncodeStr) {
        String licenceInfo = null;
        byte[] decode;
        try {
            //对称解密
            decode = HSBlowfish.decode(licenseEncodeStr.getBytes("UTF-8"));
            //非对称解密
            /*RSA rsa = new RSA(null, RSA_PUBLIC_KEY);
            decode = rsa.decrypt(licenseEncodeStr.getBytes("UTF-8"), KeyType.PublicKey);*/
            licenceInfo = new String(decode, "UTF-8");
        } catch (Exception e) {
            logger.error("解密许可文件失败", e);
        }
        //测试使用
        //licenceInfo = XStreamUtil.getProduct();
        if (StringUtils.isEmpty(licenceInfo)) {
            logger.error("=================================================");
            logger.error("||***********许可证文件为空，请检查原因***********||");
            logger.error("=================================================");
            System.exit(0);
        } else {
            //解析许可文件，存放系统缓存中
            Product product = null;
            try {
                product = XStreamUtil.xmlToBean(licenceInfo, new Class[]{Product.class, Module.class, Api.class, PersonalizedElement.class, MachineCodeSet.class});
                logger.info("=================================================");
                logger.info("||**************加载许可证文件成功***************||");
                logger.info("=================================================");
            } catch (Exception e) {
                logger.error("解析许可文件失败", e);
            }
            if (product != null) {
                CacheUtil.getInstance().addCache(CacheUtil.PRODUCT_CACHE_NAME, product.getProductName(), product);
                this.putExtendField2Cache(product.getProductName(), product.getExtendField());
                if (CollectionUtils.isNotEmpty(product.getPersonalizedElementSet())) {
                    this.putPersonalizedElement2Cache(product.getProductName(), product.getPersonalizedElementSet());
                }
                if (CollectionUtils.isNotEmpty(product.getModules())) {
                    for (Module module : product.getModules()) {
                        CacheUtil.getInstance().addCache(CacheUtil.MODULE_CACHE_NAME, module.getGsv(), module);
                        this.putExtendField2Cache(module.getGsv(), module.getExtendField());
                        if (CollectionUtils.isNotEmpty(module.getPersonalizedElementSet())) {
                            this.putPersonalizedElement2Cache(module.getGsv(), module.getPersonalizedElementSet());
                        }
                        if (CollectionUtils.isNotEmpty(module.getApiSet())) {
                            for (Api api : module.getApiSet()) {
                                CacheUtil.getInstance().addCache(CacheUtil.API_CACHE_NAME, api.getFunctionId(), api);
                                this.putExtendField2Cache(api.getFunctionId(), api.getExtendField());
                                if (CollectionUtils.isNotEmpty(api.getPersonalizedElementSet())) {
                                    this.putPersonalizedElement2Cache(api.getFunctionId(), api.getPersonalizedElementSet());
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private void putPersonalizedElement2Cache(String foreignId, List<PersonalizedElement> personalizedElementSet) {
        if (CollectionUtils.isNotEmpty(personalizedElementSet)) {
            CacheUtil.getInstance().addCache(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, foreignId, personalizedElementSet);
        }
    }

    private void putExtendField2Cache(String foreignId, String mapJsonStr) {
        if (StringUtils.isNotEmpty(mapJsonStr)) {
            Map map = JSON.parseObject(mapJsonStr);
            CacheUtil.getInstance().addCache(CacheUtil.EXTEND_FIELD_CACHE_NAME, foreignId, map);
        }
    }

    public void clear() {
        CacheUtil.getInstance().deleteAll(CacheUtil.PRODUCT_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.MODULE_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.API_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.EXTEND_FIELD_CACHE_NAME);
    }

    public Map copy() {
        return CacheUtil.getInstance().copy();
    }

    private String getGsv() {
        String group = ConfigUtils.getAppGroup();
        String appName = ConfigUtils.getAppName();
        String version = ConfigUtils.getAppVersion();
        return (StringUtils.isEmpty(group) ? "g" : group) + "_" + appName + "_" + (StringUtils.isEmpty(version) ? "v" : version);
    }


    public static void main(String[] args) {
//        LicenseContentLoader.getInstance().clear();
//        String param = "qTHK0IinBUqFiyQJuyUANf5pu4msaNIFKQtnwrSuAxiiDrdrF2sAD6KlWt71yhYYA9+qPXleWp+UrjinzerNoPT8kBsvl2WrXlRe2LtAlFnfISlq3t73jNtOMXbp5FIUuf97mXZ6QEnRaeH5kHQcSYVpJqreXMATHIcmOE3yBpwO7zt88gDrWTwuoPTZLg+rvoVtwYweeFOl75RPxdm1MI/FDyyse3vnpe+UT8XZtTAwewJ+LCsTLIgWuU8c64+PRtGCKGELe94kkoXthnSUfaA72iR7Ikt0fXeO1lrpMLH/+TtmQ6aFIPqco/kYqjMzBc+aaS+uVNWHnEBektwefYPZgZt6SC2H9Mk449pF/2zzGjZij8LDLTCAJumknkblHWjSDS1o1Vcqic7oEk0/jJO4nhb2M5DqZ+m96RtiZpaD2YGbekgth4T8egdVpik8mUd503KCX7tjXWmBUBQsxcmEbGvBzLGsfCFRLHYNPiulsM93RwyO2m5d8Sgla3A7pbDPd0cMjtp8fdZrhDBHVkm1UkMRYmVHN+hwL1M7EQNZfhCTe6iaF5fiPBCDgLlYUczyT3EuNleS6T4xew8eCqIrGd5C0CHsNA2U+xdFGL7ZLMfmNV42TEGuRcAWG8TzLmVMiqa6vEGNUnKff9Y358sK7rcV4vITzEAPDZbiVt03+eA3n5eVXfkMtkj5uBIKWX4Qk3uomhd2U7WPG2mFsndpZ5cKPuck5VXoyz9orTvsr3Ob6bCwMNYwU9UVRf2CONkib4J5jNiNUnKff9Y357jSY4lNkykhWX4Qk3uomhfX9KKPrm37MGH50BPKWLUSvvOTrDuoLHUsZAiacpIevNtwqNG1ofdcGb1eEVGdEOCiCkTbUwwI6EgX7m0f2I9kEiaWMxEC9b3MJW7IGf7uYENf2d5lBigu2SzH5jVeNkzZLmvJHRV2VmqffaSKYmavvuXAtG03uVaHwgbYoGk8Ayw1XGApacc7JTMkbg==";
        LicenseContentLoader.getInstance().init(null);

    }
}
