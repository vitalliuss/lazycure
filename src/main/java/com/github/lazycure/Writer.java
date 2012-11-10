package main.java.com.github.lazycure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Writer {
	
	public static Context context = LazyCureApplication.getAppContext();
	
	public static Boolean checkSDCard() {
	    String auxSDCardStatus = Environment.getExternalStorageState();

	    if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED))
	        return true;
	    else if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
	        Toast.makeText(
	                context,
	                "Warning, the SDCard it's only in read mode.\nthis does not result in malfunction"
	                        + " of the read aplication", Toast.LENGTH_LONG)
	                .show();
	        return true;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_NOFS)) {
	        Toast.makeText(
	                context,
	                "Error, the SDCard can be used, it has not a corret format or "
	                        + "is not formated.", Toast.LENGTH_LONG)
	                .show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_REMOVED)) {
	        Toast.makeText(
	                context,
	                "Error, the SDCard is not found, to use the reader you need "
	                        + "insert a SDCard on the device.",
	                Toast.LENGTH_LONG).show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_SHARED)) {
	        Toast.makeText(
	                context,
	                "Error, the SDCard is not mounted beacuse is using "
	                        + "connected by USB. Plug out and try again.",
	                Toast.LENGTH_LONG).show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTABLE)) {
	        Toast.makeText(
	                context,
	                "Error, the SDCard cant be mounted.\nThe may be happend when the SDCard is corrupted "
	                        + "or crashed.", Toast.LENGTH_LONG).show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTED)) {
	        Toast.makeText(
	                context,
	                "Error, the SDCArd is on the device but is not mounted."
	                        + "Mount it before use the app.",
	                Toast.LENGTH_LONG).show();
	        return false;
	    }

	    return true;
	}

	
	public static boolean writeFile(String directoryName, String filename, String data){
		boolean operationStatus = false;
		File root = new File(Environment.getExternalStorageDirectory(), directoryName);
		if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File(root, filename);
        try {
                if (checkSDCard() && root.canWrite()){
		                FileWriter filewriter = new FileWriter(file);
		                BufferedWriter out = new BufferedWriter(filewriter);
		                out.write(data);
		                out.close();
		                operationStatus = true;
                	}
        } catch (IOException e) {
            Log.e("Writer", "Could not write file " + e.getMessage());
        }
		return operationStatus;
	}

}
