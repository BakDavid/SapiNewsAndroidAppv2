package ro.sapientia.ms.sapinewsandroidappv2.application;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pedrohuan.sapinewsandroidappv2.R;
import ro.sapientia.ms.sapinewsandroidappv2.application.interfaces.BottomNavigationInterface;

/**
 * Ez az activity tartja ossze a fragmenteket. Ha a navigation barnal rakattintunk egy menupontra, akkor
 * ez az activity ertesitest kap rola es aszerint hogy mire kattintottunk kivalasztja, hogy melyik
 * fragment kovetkezik.
 */
public class Main2Activity extends AppCompatActivity implements BottomNavigationInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setOnNavigationItemSelectedListener(navListener);

        bottom_nav.setSelectedItemId(R.id.nav_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new ListNewsFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new ListNewsFragment();
                            break;
                        case R.id.nav_my_news:
                            selectedFragment = new MyNewsFragment();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new CreateNewsFragment(Main2Activity.this);
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,selectedFragment).commit();

                    return true;
                }
    };

    /**
     * Ez a fuggveny az interfeszbol jon. A create news-nel, mikor a hirdetes letrejon, akkor a bottom
     * navigation bar updatelodik es valt.
     */
    @Override
    public void changeToMain() {

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setSelectedItemId(R.id.nav_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,new ListNewsFragment()).commit();
    }
}
