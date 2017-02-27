package work.nich.chinesename;

import android.content.Context;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nich on 2017/2/23.
 * Generate Chinese name at random.
 */

public class YourName {

    private Context mContext;
    private SparseArray<String> mFamilyNameWithOneCharacterArray;
    private SparseArray<String> mFamilyNameWithTwoCharactersArray;
    private SparseArray<String> mFamilyNameWithMoreThanTwoCharactersArray;
    private SparseArray<String> mGivenNameCharactersArray;

    public static final int MORE_THEN_THREE_CHARACTER = -1;
    public static final int THREE_CHARACTER = 3;
    public static final int TWO_CHARACTER = 2;

    private static final int ONE_CHARACTER = 1;

    public YourName(Context context) {
        mContext = context;
    }

    /**
     * Generate your name with all random character.
     *
     * @return Common 3 character Chinese name.
     */
    public String generateName() {
        return generateName(THREE_CHARACTER);
    }

    /**
     * Use YourName.THREE_CHARACTER , YourName.TWO_CHARACTER, YourName.MORE_THEN_THREE_CHARACTER
     *
     * @param length Length of name.
     * @return Random Chinese name.
     */
    public String generateName(int length){
        if (length != MORE_THEN_THREE_CHARACTER && length != THREE_CHARACTER && length != TWO_CHARACTER){
            return "Sorry bro, you can't get a name with that length.\n Only YourName.TWO_CHARACTER , YourName.THREE_CHARACTER and MORE_THEN_THREE_CHARACTER is supported.";
        }
        String name;

        if (length == TWO_CHARACTER){
            name = generateFamilyName(ONE_CHARACTER) + generateGivenName();
        }else if(length == THREE_CHARACTER){
            if (positiveOrNegative()){
                name = generateFamilyName(ONE_CHARACTER) + generateGivenName() + generateGivenName();
            }else {
                name = generateName(TWO_CHARACTER) + generateGivenName();
            }
        }else {
            name = generateFamilyName(MORE_THEN_THREE_CHARACTER) + "·" + generateGivenName() + generateGivenName();
        }
        return name;
    }

    /**
     * Generate a random family name.
     * @param length the length of family name, YourName.THREE_CHARACTER , YourName.TWO_CHARACTER, YourName.MORE_THEN_THREE_CHARACTER is advocated.
     * @return first name.
     */
    public String generateFamilyName(int length) {
        if (mFamilyNameWithOneCharacterArray == null) {
            loadTextFile(R.raw.family_name);
        }

        SparseArray<String> tempArray;
        if (length == ONE_CHARACTER){
            tempArray = mFamilyNameWithOneCharacterArray;
        }else if(length == TWO_CHARACTER){
            tempArray = mFamilyNameWithTwoCharactersArray;
        }else {
            tempArray = mFamilyNameWithMoreThanTwoCharactersArray;
        }

        int position = (int) (Math.random() * tempArray.size());
        String x = tempArray.get(position);

        String[] subFirstNameArray = x.split(" ");
        int subPosition = (int) (Math.random() * subFirstNameArray.length);

        return subFirstNameArray[subPosition];
    }

    /**
     * Generate a random given name, will return only one character. 
     * If you want your given name contains two character, you need to
     * call this method twice.
     * @return given name.
     */
    public String generateGivenName() {
        if (mGivenNameCharactersArray == null){
            loadTextFile(R.raw.given_name);
        }
        int position = (int) (Math.random() * mGivenNameCharactersArray.size());
        String x = mGivenNameCharactersArray.get(position);

        String[] subSecondNameArray = x.split(" ");

        return subSecondNameArray[1];
    }

    private void loadTextFile(final int id) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mContext.getApplicationContext().getResources().openRawResource(id), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            if (id == R.raw.family_name) {
                mFamilyNameWithOneCharacterArray = new SparseArray<String>();
                mFamilyNameWithTwoCharactersArray = new SparseArray<String>();
                mFamilyNameWithMoreThanTwoCharactersArray =  new SparseArray<String>();
                int i = 0;
                boolean hitFirstDivider = false;
                boolean hitSecondDivider = false;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("") && !hitFirstDivider){
                        hitFirstDivider = true;
                        i = 0;
                        continue;
                    }else if(line.equals("") && !hitSecondDivider){
                        hitSecondDivider = true;
                        i = 0;
                        continue;
                    }

                    if (!hitFirstDivider && !hitSecondDivider) {
                        mFamilyNameWithOneCharacterArray.append(i++, line);
                    }else if(!hitSecondDivider){
                        mFamilyNameWithTwoCharactersArray.append(i++, line);
                    }else{
                        mFamilyNameWithMoreThanTwoCharactersArray.append(i++, line);
                    }
                }
            } else {
                mGivenNameCharactersArray = new SparseArray<String>();
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    mGivenNameCharactersArray.put(i++, line);
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * fake random
     */
    private boolean positiveOrNegative(){
        return Math.random() >= 0.5f;
    }
}
