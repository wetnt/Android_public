package com.boboking.brushos.data;

import java.util.Date;

public class SecData {
	public Date d = new Date();
	public int x, y, z, a;

	public SecData() {
		d = new Date(System.currentTimeMillis());
		x = 0;
		y = 0;
		z = 0;
		a = 0;
	}

	public SecData(int _x, int _y, int _z) {
		d = new Date(System.currentTimeMillis());
		x = _x;
		y = _y;
		z = _z;
		a = x + y + z;
	}

	public void set(int _x, int _y, int _z) {
		d = new Date(System.currentTimeMillis());
		x = _x;
		y = _y;
		z = _z;
		a = x + y + z;
	}

	public void clear() {
		d = new Date(0);
		x = 0;
		y = 0;
		z = 0;
		a = 0;
	}
}
