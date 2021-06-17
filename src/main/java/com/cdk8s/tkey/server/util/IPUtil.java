package com.cdk8s.tkey.server.util;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.extra.servlet.ServletUtil;

public final class IPUtil {

	public static String getIp(HttpServletRequest request) {
		return ServletUtil.getClientIP(request);
	}
}
