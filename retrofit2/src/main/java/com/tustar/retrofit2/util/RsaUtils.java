package com.tustar.retrofit2.util;

import android.util.Base64;

import com.tustar.common.util.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class RsaUtils {

    public static String buildSignContent(Map<String, String> parameters) {
        Map<String, String> params = parametersFilter(parameters);
        return createLinkString(params);
    }

    /**
     * 过滤sign参数不验签
     *
     * @param parameters
     * @return
     */
    private static Map<String, String> parametersFilter(Map<String, String> parameters) {
        Map<String, String> result = new HashMap<String, String>();
        if (parameters == null || parameters.size() <= 0) {
            return result;
        }
        for (Entry<String, String> entry : parameters.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (value == null || value.equals("") || key.equalsIgnoreCase(NetUtils.SIGN)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param parameters 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> parameters) {
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = parameters.get(key);
            if (i > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

    /**
     * 对content行进签名
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     */
    public static String rsaSign(String content, String privateKey, String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encode(signed, Base64.DEFAULT));
        } catch (Exception e) {
            Logger.e("RSAcontent = " + content + "; charset = " + charset, e);
            return null;
        }
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = null;
        byte[] encodedKey;
        try {
            keyFactory = KeyFactory.getInstance(algorithm);
            encodedKey = StreamUtils.readAll(ins);
            encodedKey = Base64.decode(encodedKey, Base64.DEFAULT);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean rsaCheck(String content, String sign, String publicKey, String charset) throws SignatureException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(getContentBytes(content, charset));
            return signature.verify(Base64.decode(sign.getBytes(), Base64.DEFAULT));
        } catch (Exception e) {
            Logger.e("RSA验证签名[content = " + content + "; charset = " + charset + "; signature = " + sign + "]发生异常!", e);
            throw new SignatureException("RSA验证签名[content = " + content + "; charset = " + charset + "; signature = " + sign + "]发生异常!", e);
        }
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            byte[] encodedKey = StreamUtils.readAll(ins);

            // 先base64解码
            encodedKey = Base64.decode(encodedKey, Base64.DEFAULT);
            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (IOException ex) {
            // 不可能发生
        } catch (InvalidKeySpecException ex) {
            // 不可能发生
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (StringUtils.isEmpty(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private RsaUtils() {
        super();
    }

    public static void main(String[] args) {
    }
}
