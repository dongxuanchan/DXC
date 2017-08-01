package com.blch.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 4/24/17
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

  public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
  public static SimpleDateFormat dateFileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

  public static String enscriptPassMD5(String pass){
    String hashtext = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] messageDigest = md.digest(pass.getBytes());
      BigInteger number = new BigInteger(1, messageDigest);
      hashtext = number.toString(16);
      // Now we need to zero pad it if you actually want the full 32 chars.
      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }
    }
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return hashtext;
  }

  public static String enscriptBCrypt(String pass){
    String hashtext = null;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    hashtext = passwordEncoder.encode(pass);
    return hashtext;
  }

  public static Date parseDate(String date){
    Date rs=null ;
    try {
      rs =  dateFormat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return rs;
  }

  public static boolean isObjectEmpty(Object object) {
    if(object == null) return true;
    else if(object instanceof String) {
      if (((String)object).trim().length() == 0) {
        return true;
      }
    } else if(object instanceof Collection) {
      return isCollectionEmpty((Collection<?>)object);
    }
    return false;
  }

  private static boolean isCollectionEmpty(Collection<?> collection) {
    if (collection == null || collection.isEmpty()) {
      return true;
    }
    return false;
  }

}
