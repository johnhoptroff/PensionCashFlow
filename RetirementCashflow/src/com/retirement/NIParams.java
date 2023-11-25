package com.retirement;

public class NIParams {
	private double dbNIhighPaypc;
	private double dbNIlowPaypc;
	private double dbNIhighwk;
	private double dbNIlowwk;


	public NIParams(double dbNIhighpc, double dbNIlowpc, double dbNIhighwk, double dbNIlowwk) {
		super();
		this.dbNIhighPaypc = dbNIhighpc;
		this.dbNIlowPaypc = dbNIlowpc;
		this.dbNIhighwk = dbNIhighwk;
		this.dbNIlowwk = dbNIlowwk;
	}

	public double getNIhighPaypc() {
		return this.dbNIhighPaypc;
	}

	public double getNIlowPaypc() {
		return this.dbNIlowPaypc;
	}

	public double getNIhiWk() {
		return this.dbNIhighwk;
	}

	public double getNIlowWk() {
		return this.dbNIlowwk;
	}

}
