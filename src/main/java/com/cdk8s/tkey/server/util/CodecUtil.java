package com.cdk8s.tkey.server.util;

import org.apache.commons.codec.binary.Base64;

public final class CodecUtil {

	public static String base64DecodeBySafe(final String base64String) {
		return new String(Base64.decodeBase64(base64String));
	}

}
