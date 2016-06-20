package bbk.map.bbd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BBDWrite {

	// ============д�뺯��================================================
	private void WriteNewBBD(String bbdPath) {
		// --------------------------------------------------------
		try {
			// --------------------------------------------------------
			File rf = new File(bbdPath);
			if (!rf.exists()) {// BBD���ڼ��
				// ----------------------------------------------------
				rf.createNewFile();
				// ----------------------------------------------------
				RandomAccessFile rs = new RandomAccessFile(rf, "rw");
				rs.seek(80000);
				// ----------------------------------------------------
				rs.writeLong(0);
				rs.writeLong(0);
				rs.close();
				// ----------------------------------------------------
				rs = null;
				rf = null;
				// ----------------------------------------------------
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		// --------------------------------------------------------
	}

	public void WritePicBBD(String bbdPath, byte fd[], int pf) {
		// --------------------------------------------------------
		WriteNewBBD(bbdPath);
		// --------------------------------------------------------
		try {
			// --------------------------------------------------------
			RandomAccessFile rs = new RandomAccessFile(bbdPath, "rw");
			// --------------------------------------------------------
			int fs = (int) rs.length();
			int fl = fd.length;
			// --------------------------------------------------------
			rs.seek(fs);
			rs.write(fd);
			// --------------------------------------------------------
			rs.seek(pf);
			// --------------------------------------------------------
			byte[] fsb = getBytesFormInt(fs);
			byte[] flb = getBytesFormInt(fl);
			rs.write(fsb);
			rs.write(flb);
			// --------------------------------------------------------
			rs.close();
			rs = null;
			fsb = null;
			flb = null;
			// --------------------------------------------------------
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		// --------------------------------------------------------
	}

	// ============д�뺯��================================================

	public static byte[] getBytesFormInt(int val) {
		// --------------------------------------------------------
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.putInt(val);
		return buf.array();
		// --------------------------------------------------------
	}

}