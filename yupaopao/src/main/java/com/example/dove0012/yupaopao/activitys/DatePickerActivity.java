package com.example.dove0012.yupaopao.activitys;

import android.content.Intent;
import com.example.dove0012.yupaopao.R;
import java.util.Calendar;
import butterknife.Bind;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class DatePickerActivity extends BaseActivity {

    @Bind(R.id.datePicker) DatePicker datePickerView;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_date_picker);
    }

    @Override
    public void initWidget() {
        Calendar now = Calendar.getInstance();
        datePickerView.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1);
        datePickerView.setMode(DPMode.SINGLE);
        datePickerView.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Intent intent = new Intent();
                intent.putExtra("value", date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}