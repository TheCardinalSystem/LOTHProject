package com.Cardinal.LOTH.Util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WebUtils {
	public static boolean checkNetworkConnection(String webDomain, int timeout) throws UnknownHostException, IOException {
			return InetAddress.getByName(webDomain).isReachable(timeout);
	}

	public enum WebLang {
		ENG, LAT, BOTH;
		public String toFullString() {
			return this.equals(ENG) ? "English" : this.equals(LAT) ? "Latin" : "Latin - English";
		}
	}
}
