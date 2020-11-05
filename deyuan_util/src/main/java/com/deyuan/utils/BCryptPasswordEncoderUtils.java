package com.deyuan.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

  private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  public static String encodePassword(String password){
      return bCryptPasswordEncoder.encode(password);
  }

    public static void main(String[] args) {
        String s = BCryptPasswordEncoderUtils.encodePassword("dstjava");
//        \$2a$10$25nuaWMfJUW/wf5JN4LbCuloHclLCrUoZzkQapcb4fLIZ7FjFIjwS
        System.out.println(s);
    }
}
