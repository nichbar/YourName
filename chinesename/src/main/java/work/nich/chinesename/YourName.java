package work.nich.chinesename;

import android.content.Context;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nichbar on 2017/2/23.
 * Generate Chinese name at random.
 */

public class YourName {

    private Context mContext;
    private SparseArray<String> mFirstNameOneCharacterArray;
    private SparseArray<String> mFirstNameTwoCharactersArray;
    private SparseArray<String> mFirstNameManyCharactersArray;
    private SparseArray<String> mSecondNameArray;

    public static final int RANDOM_CHARACTER = -1;
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
     * Use YourName.THREE_CHARACTER , YourName.TWO_CHARACTER, YourName.RANDOM_CHARACTER
     *
     * @param length Length of name.
     * @return Random Chinese name.
     */
    public String generateName(int length){
        if (length != RANDOM_CHARACTER && length != THREE_CHARACTER && length != TWO_CHARACTER){
            return "Sorry bro, you can't get a name with that length.";
        }
        String name;

        if (length == TWO_CHARACTER){
            name = generateFirstName(ONE_CHARACTER) + generateSecondName();
        }else if(length == THREE_CHARACTER){
            if (positiveOrNegative()){
                name = generateFirstName(ONE_CHARACTER) + generateSecondName() + generateSecondName();
            }else {
                name = generateName(TWO_CHARACTER) + generateSecondName();
            }
        }else {
            name = generateFirstName(RANDOM_CHARACTER) + "·" + generateSecondName() + generateSecondName();
        }
        return name;
    }

    /**
     * Generate a random first name.
     * @return first name.
     */
    public String generateFirstName(int length) {
        if (mFirstNameOneCharacterArray == null) {
            loadTextFile(R.raw.firstname);
        }

        SparseArray<String> tempArray;
        if (length == ONE_CHARACTER){
            tempArray = mFirstNameOneCharacterArray;
        }else if(length == TWO_CHARACTER){
            tempArray = mFirstNameTwoCharactersArray;
        }else {
            tempArray = mFirstNameManyCharactersArray;
        }

        int position = (int) (Math.random() * tempArray.size());
        String x = tempArray.get(position);

        String[] subFirstNameArray = x.split(" ");
        int subPosition = (int) (Math.random() * subFirstNameArray.length);

        return subFirstNameArray[subPosition];
    }

    /**
     * Generate a random second name.
     * @return second name.
     */
    public String generateSecondName() {
        if (mSecondNameArray == null){
            loadTextFile(R.raw.secondname);
        }
        int position = (int) (Math.random() * mSecondNameArray.size());
        String x = mSecondNameArray.get(position);

        String[] subSecondNameArray = x.split(" ");

        return subSecondNameArray[1];
    }

    private void loadTextFile(final int id) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mContext.getApplicationContext().getResources().openRawResource(id), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            if (id == R.raw.firstname) {
                mFirstNameOneCharacterArray = new SparseArray<String>();
                mFirstNameTwoCharactersArray = new SparseArray<String>();
                mFirstNameManyCharactersArray =  new SparseArray<String>();
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
                        mFirstNameOneCharacterArray.append(i++, line);
                    }else if(!hitSecondDivider){
                        mFirstNameTwoCharactersArray.append(i++, line);
                    }else{
                        mFirstNameManyCharactersArray.append(i++, line);
                    }
                }
            } else {
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

    private boolean positiveOrNegative(){
        return Math.random() >= 0.5f;
    }
}
