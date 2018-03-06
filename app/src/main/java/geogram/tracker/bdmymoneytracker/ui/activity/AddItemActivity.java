package geogram.tracker.bdmymoneytracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.tracker.bdmymoneytracker.R;
import geogram.tracker.bdmymoneytracker.model.Items;


/**
 * Created by geogr on 21.02.2018.
 */

public class AddItemActivity extends AppCompatActivity{
    public static final String RESULT_ITEM = "item";
    public static final int RC_ADD_ITEM = 99;


    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.add)
    TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                add.setEnabled(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(price.getText()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                add.setEnabled(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(price.getText()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Items item=new Items();
                item.setName(name.getText().toString());
                item.setPrice(Integer.valueOf(price.getText().toString()));
                Intent result = new Intent();
                result.putExtra(RESULT_ITEM, item);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }


}
