package com.tustar.ushare.util

import android.content.Context
import android.util.Base64
import com.tustar.common.util.Logger
import java.io.*
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

class RsaUtils private constructor() {
    companion object {

        fun buildSignContent(parameters: Map<String, String>): String {
            val params = parametersFilter(parameters)
            return createLinkString(params)
        }

        /**
         * 过滤sign参数不验签
         *
         * @param parameters
         * @return
         */
        private fun parametersFilter(parameters: Map<String, String>?): Map<String, String> {
            val result = HashMap<String, String>()
            if (parameters == null || parameters.isEmpty()) {
                return result
            }
            for ((key, value) in parameters) {
                if (value == null || value == "" || key.equals(NetUtils.SIGN, ignoreCase = true)) {
                    continue
                }
                result[key] = value
            }
            return result
        }

        /**
         * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
         *
         * @param parameters 需要排序并参与字符拼接的参数组
         * @return 拼接后字符串
         */
        private fun createLinkString(parameters: Map<String, String>): String {
            val keys = ArrayList(parameters.keys)
            Collections.sort(keys)
            val sb = StringBuffer()
            for (i in keys.indices) {
                val key = keys[i]
                val value = parameters[key]
                if (i > 0) {
                    sb.append("&")
                }
                sb.append("$key=$value")
            }
            return sb.toString()
        }

        /**
         * 对content行进签名
         *
         * @param content
         * @param privateKey
         * @param charset
         * @return
         */
        fun rsaSign(content: String, privateKey: String, charset: String): String? {
            try {
                val priKey = getPrivateKeyFromPKCS8("RSA", ByteArrayInputStream(privateKey.toByteArray()))
                val signature = Signature.getInstance("SHA1WithRSA")
                signature.initSign(priKey)
                if (StringUtils.isEmpty(charset)) {
                    signature.update(content.toByteArray())
                } else {
                    signature.update(content.toByteArray(charset(charset)))
                }

                val signed = signature.sign()
                return String(Base64.encode(signed, Base64.DEFAULT))
            } catch (e: Exception) {
                Logger.e("RSAcontent = $content; charset = $charset", e)
                return null
            }

        }

        private fun getPrivateKeyFromPKCS8(algorithm: String, ins: InputStream?): PrivateKey? {
            if (ins == null || StringUtils.isEmpty(algorithm)) {
                return null
            }

            var keyFactory: KeyFactory?
            var encodedKey: ByteArray?
            try {
                keyFactory = KeyFactory.getInstance(algorithm)
                encodedKey = StreamUtils.readAll(ins)
                encodedKey = Base64.decode(encodedKey, Base64.DEFAULT)
                return keyFactory!!.generatePrivate(PKCS8EncodedKeySpec(encodedKey))
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            }

            return null
        }

        @Throws(SignatureException::class)
        fun rsaCheck(content: String, sign: String, publicKey: String, charset: String): Boolean {
            try {
                val pubKey = getPublicKeyFromX509("RSA", ByteArrayInputStream(publicKey.toByteArray()))

                val signature = Signature.getInstance("SHA1WithRSA")
                signature.initVerify(pubKey)
                signature.update(getContentBytes(content, charset))
                return signature.verify(Base64.decode(sign.toByteArray(), Base64.DEFAULT))
            } catch (e: Exception) {
                Logger.e("RSA验证签名[content = $content; charset = $charset; signature = $sign]发生异常!", e)
                throw SignatureException("RSA验证签名[content = $content; charset = $charset; signature = $sign]发生异常!", e)
            }

        }

        private fun getPublicKeyFromX509(algorithm: String, ins: InputStream): PublicKey? {
            try {
                val keyFactory = KeyFactory.getInstance(algorithm)

                var encodedKey = StreamUtils.readAll(ins)

                // 先base64解码
                encodedKey = Base64.decode(encodedKey, Base64.DEFAULT)
                return keyFactory.generatePublic(X509EncodedKeySpec(encodedKey))
            } catch (ex: IOException) {
                // 不可能发生
            } catch (ex: InvalidKeySpecException) {
                // 不可能发生
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return null
        }

        private fun getContentBytes(content: String, charset: String): ByteArray? {
            if (StringUtils.isEmpty(charset)) {
                return content.toByteArray()
            }
            try {
                return content.toByteArray(charset(charset))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return null
        }

        @JvmStatic
        fun main(args: Array<String>) {
        }

        fun getPrivateKey(context:Context, keyFile: String): String {
            val privateKey = StringBuffer()
            try {
                context.resources.assets.open(keyFile).bufferedReader().readLines().forEach {
                    if (!it.startsWith("-----")) {
                        privateKey.append(it)
                    }
                }
            } catch (e: FileNotFoundException) {
                Logger.e(e.message)
            }


            return privateKey.toString()
        }
    }
}
