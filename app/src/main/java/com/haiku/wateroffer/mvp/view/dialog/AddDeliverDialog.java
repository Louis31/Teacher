package com.haiku.wateroffer.mvp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.haiku.wateroffer.R;

public class AddDeliverDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private EditText et_phone;
    private Button btn_confirm;

    public AddDeliverDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AddDeliverDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_add_deliver, null);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        et_phone = (EditText) view.findViewById(R.id.et_phone);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public AddDeliverDialog setClickListener(View.OnClickListener clickListener) {
        btn_confirm.setOnClickListener(clickListener);
        return this;
    }

    public String getInputText() {
        return et_phone.getText().toString().trim();
    }
}
