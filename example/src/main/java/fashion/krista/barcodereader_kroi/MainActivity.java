package fashion.krista.barcodereader_kroi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import fashion.krista.barcodereader_kroi.Retrofit.NetworkService;
import fashion.krista.barcodereader_kroi.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int sklad_id = 987;
    int from_sklad = 985;

    RadioGroup radioGroup;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        findViewById(R.id.scan_qr_btn).setOnClickListener(this);
        findViewById(R.id.show_alert_btn).setOnClickListener(this);
        findViewById(R.id.scan_pack_qr_btn).setOnClickListener(this);
        findViewById(R.id.show_pack_alert_btn).setOnClickListener(this);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Минуточку ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        radioGroup = findViewById(R.id.radio_group_main);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.give_rb:
                        from_sklad = 985;
                        sklad_id = 987;
                        Log.e("TAG", "onCheckedChanged: ");
                        break;
                    case R.id.get_rb:
                        from_sklad = 987;
                        sklad_id = 985;
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_qr_btn:
                Intent intent = new Intent(MainActivity.this, QrScannerActivity.class);
                intent.putExtra("sklad_id", 14);
                intent.putExtra("mode", 0);
                intent.putExtra("from_sklad_id", 13);
                startActivity(intent);
                break;
            case R.id.show_alert_btn:
                showDialog(14, 13, 0);
                break;
            case R.id.scan_pack_qr_btn:
                Intent intentPack = new Intent(MainActivity.this, QrScannerActivity.class);
                intentPack.putExtra("sklad_id", sklad_id);
                intentPack.putExtra("mode", 1);
                intentPack.putExtra("from_sklad_id", from_sklad);
                startActivity(intentPack);
                break;
            case R.id.show_pack_alert_btn:
                showDialog(sklad_id, from_sklad, 1);
                break;
        }
    }


    private void showDialog(final int sklad_id, final int fromSklad, final int mode) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        dialogBuilder.setTitle(getResources().getString(R.string.enter_model));
        dialogBuilder.setView(editText);

        dialogBuilder.setPositiveButton(getResources().getString(R.string.search), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString().trim();
                if (input.length() > 0) {
                        getData(input, sklad_id, fromSklad, mode);
                    progressDialog.show();
                }
            }
        });
        dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.show();
    }

    private void getData(final String input, final int sklad_id, final int fromSklad, final int mode) {

        NetworkService.getInstance().getApi().getModelWithId(input, fromSklad).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, CountActivity.class);

                    if (response.isSuccessful()) {
                        List<Item> item = response.body();

                        Log.e("TAG", "onResponse: " + item.size());
                        if (item.size() > 0) {
                            if ((item != null ? item.get(0).getQty() : 0) > 0) {
                                intent.putExtra("qty", item.get(0).getQty());
                                intent.putExtra("sklad_id", sklad_id);
                                intent.putExtra("mode", mode);
                                intent.putExtra("from_sklad_id", fromSklad);
                                intent.putExtra("model_id", item.get(0).getModelSeriesId());
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_id), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_id), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });


    }


}
