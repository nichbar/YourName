package work.nich.chinesename;

import android.content.Context;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nich on 2017/2/23.
 * 生成名字
 */

public class YourName {

    private Context mContext;
    private SparseArray<String> mFirstNameArray;
    private SparseArray<String> mSecondNameArray;

    public YourName(Context context) {
        mContext = context;
    }

    public String generateName() {
        long s = System.currentTimeMillis();
        String name = generateFirstName() + generateSecondName() + generateSecondName();
        long e = System.currentTimeMillis();
        System.out.println(e - s);
        return name;
    }

    public String generateFirstName() {
        if (mFirstNameArray == null) {
            loadTextFile(R.raw.firstname);
        }
        int position = (int) (Math.random() * mFirstNameArray.size());
        String x = mFirstNameArray.get(position);

        String[] subFirstNameArray = x.split(" ");
        int subPosition = (int) (Math.random() * subFirstNameArray.length);

        return subFirstNameArray[subPosition];
    }

    public String generateSecondName() {
        if (mSecondNameArray == null){
            loadTextFile(R.raw.secondname);
        }
        int position = (int) (Math.random() * mSecondNameArray.size());
        String x = mSecondNameArray.get(position);

        String[] subSecondNameArray = x.split(" ");

        return subSecondNameArray[1];
    }

    private void loadTextFile(int id){
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mContext.getApplicationContext().getResources().openRawResource(id), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            if (id == R.raw.firstname) {
                mFirstNameArray = new SparseArray<String>();
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    mFirstNameArray.put(i++, line);
                }
            }else{
                mSecondNameArray = new SparseArray<String>();
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    mSecondNameArray.put(i++, line);
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
