package com.boboking.brushos.data;

import java.util.Date;

public class MinTenData {

	public long d = new Date().getTime();
	public int i;
	public int a;

	public MinTenData() {
		d = System.currentTimeMillis();// new
										// Date(System.currentTimeMillis()).getTime();
		i = 0;
		a = 0;
	}

	public MinTenData(int _i, int _a) {
		d = System.currentTimeMillis();// new
										// Date(System.currentTimeMillis()).getTime();
		i = _i;
		a = _a;
	}

	public void set(long _l, int _i, int _a) {
		d = _l;
		i = _i;
		a = _a;
	}

	public void clear() {
		d = 0;
		i = 0;
		a = 0;
	}

}
