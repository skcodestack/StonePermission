package github.com.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import github.com.permissionlib.StonePermission;
import github.com.permissionlib.annotation.PermissionFail;
import github.com.permissionlib.annotation.PermissionSuccess;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/8/11
 * Version  1.0
 * Description:
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final int BASE_REQUEST_CODE = 0x00213;

    public void baseTest(){

        StonePermission.with(this)
                .addRequestCode(BASE_REQUEST_CODE)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();

    }

    @PermissionSuccess(requestCode = BASE_REQUEST_CODE)
    public void basesuccess() {

        Toast.makeText(this,"base申请权限成功",Toast.LENGTH_SHORT).show();

    }

    @PermissionFail(requestCode = BASE_REQUEST_CODE)
    private void  basefaild(){

        Toast.makeText(this,"base申请权限失败",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        StonePermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

}
