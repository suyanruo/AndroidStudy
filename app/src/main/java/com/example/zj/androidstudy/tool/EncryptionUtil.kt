package com.example.zj.androidstudy.tool

import android.text.TextUtils
import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.Key
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.Throws

object EncryptionUtil {
    // 定义加密算法，DESede即3DES
    private const val Algorithm = "DESede"
    private const val Key = "123456781234567812345678"

    /**
     * MD5加密
     */
    fun getMD5String(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        var md5: MessageDigest?
        try {
            md5 = MessageDigest.getInstance("MD5")
            val bytes = md5.digest(string.toByteArray())
            var result: String? = ""
            for (b in bytes) {
                var temp = Integer.toHexString(b.toInt() and 0xff)
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result += temp
            }
            return result!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 3DES加密
     */
    fun get3DesString(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        val enBytes = encryptMode(Key, string.toByteArray())
        return Base64.encodeToString(enBytes, Base64.DEFAULT)
    }

    /**
     * 加密
     */
    private fun encryptMode(key: String, src: ByteArray): ByteArray {
        return try {
            val desKey = SecretKeySpec(build3DesKey(key), Algorithm)
            val cipher = Cipher.getInstance(Algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, desKey)
            cipher.doFinal(src)
        } catch (e: Exception) {
            e.printStackTrace()
            ByteArray(0)
        }
    }

    /**
     * 3DES解密
     */
    fun getDe3DesString(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        val enBytes = Base64.decode(string, Base64.DEFAULT)
        val deBytes = decryptMode(Key, enBytes)
        return String(deBytes)
    }

    /**
     * 解密
     */
    private fun decryptMode(key: String, src: ByteArray): ByteArray {
        return try {
            val desKey = SecretKeySpec(build3DesKey(key), Algorithm)
            val cipher = Cipher.getInstance(Algorithm)
            cipher.init(Cipher.DECRYPT_MODE, desKey)
            cipher.doFinal(src)
        } catch (e: Exception) {
            e.printStackTrace()
            ByteArray(0)
        }
    }

    /**
     * 根据字符串生成密钥24位的字节数组
     * @param keyStr
     * @return
     * @throws UnsupportedEncodingException
     */
    @Throws(UnsupportedEncodingException::class)
    private fun build3DesKey(keyStr: String): ByteArray? {
        val key = ByteArray(24)
        val temp = keyStr.toByteArray(charset("UTF-8"))
        if (key.size > temp.size) {
            System.arraycopy(temp, 0, key, 0, temp.size)
        } else {
            System.arraycopy(temp, 0, key, 0, key.size)
        }
        return key
    }

    /**
     * 3DES加密，湖北移动项目IPTV平台账号密码加密使用
     * @param key data
     * @return
     */
    @Throws(java.lang.Exception::class)
    private fun des3EncodeECB(key: ByteArray, data: ByteArray): ByteArray? {
        var deskey: Key? = null
        val spec = DESedeKeySpec(key)
        val keyfactory: SecretKeyFactory = SecretKeyFactory.getInstance("DESede")
        deskey = keyfactory.generateSecret(spec)
        val cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, deskey)
        return cipher.doFinal(data)
    }
}