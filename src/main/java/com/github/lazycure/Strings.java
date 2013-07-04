package main.java.com.github.lazycure;

import android.content.Context;

public class Strings {
	
	public static final Context context = LazyCureApplication.getAppContext();
	
	public static final String YES = context.getResources().getString(R.string.yes);
	public static final String NO = context.getResources().getString(R.string.no);
	public static final String EXPORTED_AS_PLAINT_TEXT = context.getResources().getString(R.string.export_SuccessfullPlainText);
	public static final String EXPORTED_AS_XLS = context.getResources().getString(R.string.export_SuccessfullXLS);
	public static final String EXPORTED_AS_TIMELOG = context.getResources().getString(R.string.export_SuccessfullTIMELOG);
	public static final String EXPORT_FAILED = context.getResources().getString(R.string.export_Failed);
	public static final String DEFAULT_SPLIT_SEPARATOR = ",";
	public static final String DEFAULT_ACTIVITY_NAME_ORIGINAL = context.getResources().getString(R.string.defaultActivityName);
	public static final String START = context.getResources().getString(R.string.start);
	public static final String DURATION = context.getResources().getString(R.string.duration);
	public static final String FINISH = context.getResources().getString(R.string.finish);
	public static final String NAME = context.getResources().getString(R.string.name);
	public static final String DELETE_TITLE = context.getResources().getString(R.string.deleteTitle);
	public static final String DELETE_MESSAGE = context.getResources().getString(R.string.deleteMessage);

}
