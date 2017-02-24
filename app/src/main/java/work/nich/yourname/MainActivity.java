package work.nich.yourname;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import work.nich.chinesename.YourName;

/**
 * Created by nich on 2017/2/23.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
    }

    public void showName(View view) {
        YourName yourName = new YourName(this);
        Toast.makeText(this,yourName.generateName(),Toast.LENGTH_SHORT).show();
    }
}
