package github.com.permission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.com.permissionlib.StonePermission;
import github.com.permissionlib.annotation.PermissionFail;
import github.com.permissionlib.annotation.PermissionSuccess;


public class MainActivity extends AppCompatActivity {

    private static final int CALL_REQUEST_CODE = 0x00001;

    @InjectView(R.id.btn_call)
    Button btn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });
    }

    private void callPhone(){
        //第一种方式
//        StonePermission.requestPermission(this,CALL_REQUEST_CODE,Manifest.permission.CALL_PHONE);
        //第二种方式
        StonePermission.with(this)
                .addRequestCode(CALL_REQUEST_CODE)
                .permissions(Manifest.permission.CALL_PHONE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        StonePermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }


    @PermissionSuccess(requestCode = CALL_REQUEST_CODE)
    public void success() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);

    }

    @PermissionFail(requestCode = CALL_REQUEST_CODE)
    private void  faild(){

        Toast.makeText(MainActivity.this,"申请权限失败",Toast.LENGTH_SHORT).show();

    }

}
