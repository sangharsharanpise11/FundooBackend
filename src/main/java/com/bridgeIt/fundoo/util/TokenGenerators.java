package com.bridgeIt.fundoo.util;

import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

@Component
public class TokenGenerators
{
	 static String Token="sangharsha";//secreat token

	public String generateToken(long id) throws UnsupportedEncodingException 
	{

		Algorithm algorithm = null;

		try 
		{
			algorithm = Algorithm.HMAC256(Token);//encoding via token
		} 
		catch (IllegalArgumentException e) 
		{

			e.printStackTrace();
		}

		String token = JWT.create().withClaim("ID", id).sign(algorithm);
		return token;
	}

	public long decodeToken(String token) throws IllegalArgumentException, UnsupportedEncodingException {
	
		System.out.println(token);
		long userid;
        //here verify the given token's algorithm

		Verification verification = JWT.require(Algorithm.HMAC256(Token));

		JWTVerifier jwtverifier = verification.build();
		DecodedJWT decodedjwt = jwtverifier.verify(token);
		Claim claim = decodedjwt.getClaim("ID");
		userid = claim.asLong();
		System.out.println(userid);
		return userid;
	}
}