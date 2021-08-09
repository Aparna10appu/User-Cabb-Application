package com.cabapplication.user.status;

public class CustomStatus {

	// FOR USER LOGIN
	public static final int BADCREDENTIALS = 560;
	public static final int USERBLOCKED = 561;
	public static final int SESSIONEXPIRED = 562;

	//for book a cab
	public static final int PENDING = 227;
	public static final int INPROGRESS = 228;
	public static final int TIMEOUT = 231;

	//for completed trip
	public static final int ALREADY_REGISTERED = 501;

	private CustomStatus() {
		super();
	}

}
