package fashion.krista.barcodereader_kroi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import fashion.krista.barcodereader_kroi.Retrofit.NetworkService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView;
    private ProgressDialog progressDialog;
    private int sklad_id;
    private int fromSklad;
    private int qty;
    private int model_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        button = findViewById(R.id.btnPut);
        init();

    }

    private void init() {
        editText = findViewById(R.id.editText_count);
        textView = findViewById(R.id.title_count);

        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText(String.valueOf(getIntent().getIntExtra("qty", 0)));
        int mode = getIntent().getIntExtra("mode", 0);
//        int mode = getIntent().getIntExtra("mode",0);
//        button.setText(mode==0?R.string.get_kroi :R.string.pack);

        progressDialog = new ProgressDialog(CountActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Минуточку ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editText.requestFocus();
        editText.selectAll();


        if (getIntent().hasExtra("sklad_id")) {
            sklad_id = getIntent().getIntExtra("sklad_id", 10);
            textView.setText(sklad_id==987?R.string.give:R.string.get);
            fromSklad = getIntent().getIntExtra("from_sklad_id", 0);
            qty = getIntent().getIntExtra("qty", 0);
            model_id = getIntent().getIntExtra("model_id", 0);
        }

        findViewById(R.id.btnPut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = editText.getText().toString().trim();

                if (count.length() > 0) {
                    progressDialog.show();
                    NetworkService.getInstance().getApi().postModel(String.valueOf(model_id), String.valueOf(sklad_id), fromSklad, Integer.valueOf(count)).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                Log.e("TAG", "onResponse: " + response.raw());
                                int ok = 0;
                                try {
                                    ok = Integer.valueOf(response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                switch (ok) {
                                    case 1:
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                startActivity(new Intent(CountActivity.this, MainActivity.class));
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            progressDialog.dismiss();
                        }
                    });
                } else
                    editText.setText(String.valueOf(getIntent().getIntExtra("qty", 0)));

            }
        });
    }
}
