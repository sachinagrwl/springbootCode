package com.nscorp.obis.common;

public class DomainValueConstants {
	
	private DomainValueConstants() {
	    throw new IllegalStateException("DomainValueConstants class");
	  }

	/* IMS02661 Domain values */
	
	public static final String[] EDI_RSN_VALUES = {"Y", "N", null};
	public static final String[] ALLOWED_GEN_FLAG_VALUES = {"B", "C", "T", null};
}
