package traveling.com.belottescore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tag";

    Realm realm = Realm.getDefaultInstance();
    EditText addScoreText;
    Adapter adapter;
    RecyclerView recyclerView;
    RealmResults<Model> mModelsRealmResults;
    int b = 1;
    TextView weTextView;
    TextView youTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModelsRealmResults = realm.where(Model.class).findAll();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new Adapter(mModelsRealmResults);
        recyclerView.hasFixedSize();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
        addScoreText = (EditText) findViewById(R.id.editText_addScore);
        adapter.notifyDataSetChanged();

        weTextView = (TextView) findViewById(R.id.textView_we);
        youTextView = (TextView) findViewById(R.id.textView_you);
        if (realm.where(Model.class).findAll().isEmpty()) {

            realm.executeTransaction(new Realm.Transaction() {


                protected Model model;

                @Override
                public void execute(Realm realm) {
                    model = realm.createObject(Model.class);
                    model.setOurScore(0);
                    model.setYourScore(0);
                    model.setToldScore(0);


                }
            });
        }
        if (b % 2 != 0) {
            weTextView.setBackgroundColor(Color.BLUE);
        } else {
            weTextView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (b % 2 == 0) {
            youTextView.setBackgroundColor(Color.BLUE);
        } else {
            youTextView.setBackgroundColor(Color.TRANSPARENT);
        }
        weTextView.setBackgroundColor(Color.BLUE);
        Button countButton = (Button) findViewById(R.id.button_plus);
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {

                Log.d(TAG, "onClick: " + b);
                if (addScoreText.getText().toString().length() != 0) {


                    Bundle bundle = new Bundle();
                    bundle.putString("told", addScoreText.getText().toString());
                    bundle.putInt("team", b);

                    b++;
                    if (b % 2 != 0) {
                        weTextView.setBackgroundColor(Color.BLUE);
                    } else {
                        weTextView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    if (b % 2 == 0) {
                        youTextView.setBackgroundColor(Color.BLUE);
                    } else {
                        youTextView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    CountFragment countFragment = new CountFragment();
                    countFragment.setArguments(bundle);
                    countFragment.show(getFragmentManager(), "tag");


                } else {
                    Toast.makeText(getApplication(), "Լրացրեք տողը", Toast.LENGTH_SHORT).show();

                }
                addScoreText.setText("");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_Game) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.deleteAll();
                }
            });

            adapter.notifyDataSetChanged();
            realm.executeTransaction(new Realm.Transaction() {


                protected Model model;

                @Override
                public void execute(Realm realm) {
                    model = realm.createObject(Model.class);
                    model.setOurScore(0);
                    model.setYourScore(0);
                    model.setToldScore(0);


                }
            });
            b = 1;
            weTextView.setBackgroundColor(Color.BLUE);
            youTextView.setBackgroundColor(Color.TRANSPARENT);
            Log.d(TAG, "onOptionsItemSelected: " + b);
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onResume() {
        super.onResume();


        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
