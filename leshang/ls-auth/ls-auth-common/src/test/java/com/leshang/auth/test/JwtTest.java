package com.leshang.auth.test;

import com.leshang.common.pojo.UserInfo;
import com.leshang.common.utils.JwtUtils;
import com.leshang.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "D:\\coding\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\coding\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU2ODUzODA2N30.gz6miNcdL2w23zGMqKhn8C0HlXogo8OkYbe9ebFFVUHWvXvhP0LxyisD_Q8ifdf3ICxav10jRB9tWGpquUFvZ3GhW6rq_8m14SxHxHskmDrNKk6U3jWy7Lfz6GFksvVZurEHWA6jkdhHKeDDnxF5Hhx9uqr-bDxLHLf9if-G_Dk";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
