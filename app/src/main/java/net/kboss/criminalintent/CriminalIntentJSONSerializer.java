package net.kboss.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/16.
 */
public class CriminalIntentJSONSerializer {
    private Context _context;
    private  String _fileName;

    public  CriminalIntentJSONSerializer(Context context,String fileName){
        this._context = context;
        this._fileName = fileName;
    }

    /**
     * 将列表保存为文件JSON格式
     * @param crimes
     * @throws JSONException
     * @throws IOException
     */
    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,IOException{
        JSONArray array = new JSONArray();
        for(Crime crime : crimes){
            array.put(crime.toJSON());
        }
        //写入磁盘
        Writer writer = null;
        try{
            OutputStream out = _context.openFileOutput(_fileName,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if (writer != null){
                writer.close();
            }
        }
    }
}
