package com.omoi.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.omoi.entity.Certificate;
import com.omoi.exception.ServiceException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author omoi
 * @date 2023/7/2
 */
public class RsaUtil {
    public static final RsaHolder holder = new RsaHolder();

    private RsaUtil() {
    }

    /**
     * 生成密钥对，将私钥缓存，并将公钥返回
     */
    public static String getPublicKey(String id, String salt){
        RSA rsa = new RSA();

        Certificate privateCertificate = new Certificate(id, rsa.getPrivateKeyBase64(), salt);
        holder.getPrivateKey().put(id, privateCertificate);

        return rsa.getPublicKeyBase64();
    }

    /**
     * 解密
     */
    public static String decrypt(String id, String encrypt){
        Map<String, Certificate> keyMap = holder.getPrivateKey();
        Certificate privateCertificate = keyMap.get(id);
        if (ObjectUtil.isNull(privateCertificate)){
            throw new ServiceException("登录已过期，请刷新页面重试");
        }

        String privateKey = privateCertificate.getKey();
        RSA rsa = new RSA(privateKey, null);

        byte[] bytes = HexUtil.decodeHex(encrypt);
        byte[] decrypt = rsa.decrypt(bytes, KeyType.PrivateKey);

        // 清除私钥缓存
        keyMap.remove(id);

        String passwordWithSlat = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        String slat = privateCertificate.getSalt();
        return passwordWithSlat.substring(0, passwordWithSlat.length() - slat.length());
    }

    /**
     * 缓存私钥
     * 实际应用可以换用redis
     */
    @Data
    private static class RsaHolder{
        private Map<String, Certificate> privateKey = new HashMap<>();
    }
}
