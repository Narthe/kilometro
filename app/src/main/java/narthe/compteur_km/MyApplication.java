package narthe.compteur_km;

import android.app.Application;
import android.content.Context;

/**
 * Created by narthe on 02/07/2015.
 *
 * @brief :
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}