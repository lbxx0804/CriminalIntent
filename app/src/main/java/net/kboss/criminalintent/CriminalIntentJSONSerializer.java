package net.kboss.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public ArrayList<Crime> loadCrimes()throws IOException,JSONException{
        ArrayList<Crime> crimes = new ArrayList<>();
        BufferedReader reader = null;
        try{
            //打开文件
            InputStream inputStream = _context.openFileInput(_fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            //读取文件
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0 ;i<array.length();i++){
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        }catch (FileNotFoundException e){
            //忽视
        }finally {
            if (reader != null){
                reader.close();
            }
        }

        return crimes;
    }
}
