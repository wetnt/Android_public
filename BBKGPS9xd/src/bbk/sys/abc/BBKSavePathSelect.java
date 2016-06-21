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
		// SharedPreferences的四种操作模式:
		// Context.MODE_PRIVATE
		// 为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
		// Context.MODE_APPEND 模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件.
		// Context.MODE_WORLD_READABLE 和 Context.MODE_WORLD_WRITEABLE
		// 用来控制其他应用是否有权限读写该文件.
		// MODE_WORLD_READABLE 表示当前文件可以被其他应用读取.
		// MODE_WORLD_WRITEABLE 表示当前文件可以被其他应用写入.
		// ------------------------------------------------------
		SharedPreferences preferences = act.getSharedPreferences("count", Context.MODE_PRIVATE);
		// ------------------------------------------------------
		int count = preferences.getInt("count", 0);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
		if (count == 0) {
		}
		Editor editor = preferences.edit();
		editor.putInt("count", ++count);// 存入数据
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
		editor.putString("SDPath", RunPath);// 存入数据
		BBKSoft.PathSD = RunPath;
		// ------------------------------------------------------
		editor.commit();// 提交修改
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
		editor.putString("SDPath", "");// 存入数据
		editor.commit();// 提交修改
		// ------------------------------------------------------
	}

	private static String Dialog_SavePathSelect(Activity act, String[] items) {
		int x = SynSelectDialog.showComfirmDialogSelect(act, "请选择地图存储位置", items);
		return items[x];
	}

	// private static String[] items = { "选项1", "选项2", "选项3", "选项4" };

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
	// // .setNegativeButton("取消", null)//
	// .show();
	// // ------------------------------------------------------
	// }
	//
	// public interface CallBackInterface {// 定义回调接口
	// public void doSome();
	//
	// public void exectueMethod();
	// }

}
