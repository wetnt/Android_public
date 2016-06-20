package bbk.sys.abc;

import bbk.bbk.box.BBKSoft;
import bbk.zzz.debug.bd;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BBKSavePathSelect {

	public static void GetRunCount(Activity act, String[] items) {
		// ------------------------------------------------------
		// SharedPreferences�����ֲ���ģʽ:
		// Context.MODE_PRIVATE
		// ΪĬ�ϲ���ģʽ,������ļ���˽������,ֻ�ܱ�Ӧ�ñ������,�ڸ�ģʽ��,д������ݻḲ��ԭ�ļ�������
		// Context.MODE_APPEND ģʽ�����ļ��Ƿ����,���ھ����ļ�׷������,����ʹ������ļ�.
		// Context.MODE_WORLD_READABLE �� Context.MODE_WORLD_WRITEABLE
		// ������������Ӧ���Ƿ���Ȩ�޶�д���ļ�.
		// MODE_WORLD_READABLE ��ʾ��ǰ�ļ����Ա�����Ӧ�ö�ȡ.
		// MODE_WORLD_WRITEABLE ��ʾ��ǰ�ļ����Ա�����Ӧ��д��.
		// ------------------------------------------------------
		SharedPreferences preferences = act.getSharedPreferences("count", Context.MODE_PRIVATE);
		// ------------------------------------------------------
		int count = preferences.getInt("count", 0);
		// �жϳ�����ڼ������У�����ǵ�һ����������ת������ҳ��
		if (count == 0) {
		}
		Editor editor = preferences.edit();
		editor.putInt("count", ++count);// ��������
		// ------------------------------------------------------
		String RunPath = preferences.getString("SDPath", "");
		boolean isInGetSdPath = false;
		// ------------------------------------------------------
		if (RunPath == "") {
			isInGetSdPath = false;
		} else {
			for (int i = 0; i < items.length; i++) {
				if (items[i].indexOf(RunPath) > -1)
					isInGetSdPath = true;
			}
		}
		if (!isInGetSdPath) {
			RunPath = Dialog_SavePathSelect(act, items);
			RunPath = RunPath.split(" ")[0];
		}
		// ------------------------------------------------------
		editor.putString("SDPath", RunPath);// ��������
		BBKSoft.PathSD = RunPath;
		// ------------------------------------------------------
		editor.commit();// �ύ�޸�
		// ------------------------------------------------------
		bd.d("run time = " + count, true, false);
		bd.d(BBKSoft.PathSD, true, false);
		// ------------------------------------------------------
	}

	public static void SetSoftPathEmpty(Activity act) {
		// ------------------------------------------------------
		SharedPreferences preferences = act.getSharedPreferences("count", Context.MODE_PRIVATE);
		// ------------------------------------------------------
		Editor editor = preferences.edit();
		editor.putString("SDPath", "");// ��������
		editor.commit();// �ύ�޸�
		// ------------------------------------------------------
	}

	private static String Dialog_SavePathSelect(Activity act, String[] items) {
		int x = SynSelectDialog.showComfirmDialogSelect(act, "��ѡ���ͼ�洢λ��", items);
		return items[x];
	}

	// private static String[] items = { "ѡ��1", "ѡ��2", "ѡ��3", "ѡ��4" };

	// private static String itemStr = "";
	// private static int itemIndex = -1;

	// private static void Dialog_Select(Context ctx, String Title, final
	// String[] items, final CallBackInterface abc) {
	// // ------------------------------------------------------
	// new AlertDialog.Builder(ctx)//
	// .setTitle(Title)//
	// .setIcon(android.R.drawable.ic_dialog_info)//
	// .setSingleChoiceItems(//
	// items, //
	// 0, //
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// itemIndex = which;
	// itemStr = items[itemIndex];
	// abc.doSome();
	// dialog.dismiss();
	// }
	// }//
	// )//
	// // .setView(img)
	// // .setNegativeButton("ȡ��", null)//
	// .show();
	// // ------------------------------------------------------
	// }
	//
	// public interface CallBackInterface {// ����ص��ӿ�
	// public void doSome();
	//
	// public void exectueMethod();
	// }

}
