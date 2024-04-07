package dev.medicalhub.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class GenerateKeys {

    public static void main(String[] args) {
        System.out.println(generateKey());
        System.out.println(generateKey());
    }

    private static String generateKey() {
        return Encoders.BASE64.encode(Jwts.ENC.A256CBC_HS512.key().build().getEncoded());
    }

}
