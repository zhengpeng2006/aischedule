package com.asiainfo.monitor.tools.license;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.CharEncoding;


public class StringTransform {


    public static byte[] getBytesIso8859_1(String string) {
        return StringTransform.getBytesUnchecked(string, CharEncoding.ISO_8859_1);
    }

    public static byte[] getBytesUsAscii(String string) {
        return StringTransform.getBytesUnchecked(string, CharEncoding.US_ASCII);
    }

    public static byte[] getBytesUtf16(String string) {
        return StringTransform.getBytesUnchecked(string, CharEncoding.UTF_16);
    }

    public static byte[] getBytesUtf16Be(String string) {
        return StringTransform.getBytesUnchecked(string, CharEncoding.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(String string) {
        return StringTransform.getBytesUnchecked(string, CharEncoding.UTF_16LE);
    }

    public static byte[] getBytesUtf8(String string) {
        return StringTransform.getBytesUnchecked(string, CharEncoding.UTF_8);
    }

    public static byte[] getBytesUnchecked(String string, String charsetName) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw StringTransform.newIllegalStateException(charsetName, e);
        }
    }

    private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    public static String newString(byte[] bytes, String charsetName) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw StringTransform.newIllegalStateException(charsetName, e);
        }
    }


    public static String newStringIso8859_1(byte[] bytes) {
        return StringTransform.newString(bytes, CharEncoding.ISO_8859_1);
    }

    public static String newStringUsAscii(byte[] bytes) {
        return StringTransform.newString(bytes, CharEncoding.US_ASCII);
    }

    public static String newStringUtf16(byte[] bytes) {
        return StringTransform.newString(bytes, CharEncoding.UTF_16);
    }

    public static String newStringUtf16Be(byte[] bytes) {
        return StringTransform.newString(bytes, CharEncoding.UTF_16BE);
    }

    public static String newStringUtf16Le(byte[] bytes) {
        return StringTransform.newString(bytes, CharEncoding.UTF_16LE);
    }

    public static String newStringUtf8(byte[] bytes) {
        return StringTransform.newString(bytes, CharEncoding.UTF_8);
    }

}
