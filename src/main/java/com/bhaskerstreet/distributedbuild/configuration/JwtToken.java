
package com.bhaskerstreet.distributedbuild.configuration;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Objects;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.Requirement;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

public class JwtToken {

private String jwtToken;
private JWTClaimsSet jwtClaimsSet;


private JwtToken(JWTbuilder jwTbuilder) {
	this.jwtToken = jwTbuilder.jwtToken;
	this.jwtClaimsSet=jwTbuilder.jwtClaimsSet;
}

public String getJwtToken() {
	return jwtToken;
}

public JWTClaimsSet getJwtClaimsSet() {
	return jwtClaimsSet;
}

public static class JWTbuilder {

	private JWSHeader jwsHeader;
	private JWSObject jwsObject;

	private JWEHeader jweHeader;
	private JWEObject jweObject;


	private JWTClaimsSet.Builder claimSetBuilder;
	private Payload payload;
	private byte[] secretKey;
	private String jwtToken;
	private JWTClaimsSet jwtClaimsSet;


	public JWTbuilder(String jwsAlgo) {
		Objects.requireNonNull(jwsAlgo);
		this.jwsHeader = new JWSHeader(new JWSAlgorithm(jwsAlgo));

	}

	public JWTbuilder(String jweAlgo, String jweEncriptioMethod) {
	

		this.jweHeader = new JWEHeader(new JWEAlgorithm(jweAlgo, Requirement.REQUIRED), getEncriptionMethod(jweEncriptioMethod));

	}

	public JWTbuilder setSecretKey(String secretKey) throws   UnsupportedEncodingException {

		Objects.nonNull(secretKey);

		this.secretKey = secretKey.getBytes("UTF-8");
		return this;


	}

	public JWTbuilder setClaims(String key, String value) {
		Objects.nonNull(key);
		if (this.claimSetBuilder == null) {
			this.claimSetBuilder = new JWTClaimsSet.Builder().claim(key, value);
		} else {
			this.claimSetBuilder.claim(key, value);
		}

		return this;
	}

	private EncryptionMethod getEncriptionMethod(String jweEncriptioMethod) {

		if (true) {
			return EncryptionMethod.A256GCM;
		}
		return null;
	}

	public JwtToken build() throws Exception {

		try {
			if (this.claimSetBuilder != null) {
				this.payload = new Payload(this.claimSetBuilder.build().toJSONObject());
			} else {
				this.payload = new Payload("");
			}

			if (this.jwsHeader != null) {
				this.jwsObject = new JWSObject(this.jwsHeader, this.payload);
				jwsObject.sign(new MACSigner(secretKey));
				System.out.println();
				this.jwtToken = jwsObject.serialize();
			} else {
				this.jweObject = new JWEObject(this.jweHeader, payload);
				jweObject.encrypt(new DirectEncrypter(this.secretKey));

				this.jwtToken = jweObject.serialize();
			}


		} catch (Exception e) {

			throw new Exception(e.getMessage());
		}

		return new JwtToken(this);
	}


	public JwtToken getClaims(String token) throws ParseException, BadJOSEException,JOSEException {

		ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
		JWKSource keySource =new ImmutableSecret(this.secretKey);

		if (this.jwsHeader != null) {
			JWSKeySelector keySelector = new JWSVerificationKeySelector<>(this.jwsHeader.getAlgorithm(), keySource);
			jwtProcessor.setJWSKeySelector(keySelector);
		} else {
			JWEKeySelector jweKeySelector=new JWEDecryptionKeySelector(this.jweHeader.getAlgorithm(),this.jweHeader.getEncryptionMethod(),keySource);
			jwtProcessor.setJWEKeySelector(jweKeySelector);

		}

		this.jwtClaimsSet=jwtProcessor.process(token, null);

		return new JwtToken(this);
	}

}

}
