package github.com.permissionlib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import github.com.permissionlib.util.PermissionUtil;


/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/22
 * Version  1.0
 * Description: 权限帮助类
 */

public class StonePermission {
    //参数
    private Object mObject;//activity  or  fragment
    private String[] mPermissions;
    private int  mRequestCode;//请求码

    private StonePermission(Object object){
        this.mObject=object;
    }

    /**
     * 直接请求权限
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Activity activity,int requestCode,String... permissions){
        realRequestPermission(activity,requestCode,permissions);
    }

    /**
     * 直接请求权限
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Fragment fragment, int requestCode, String... permissions){
        realRequestPermission(fragment,requestCode,permissions);
    }

    /**
     * 关联调用类
     * @param activity
     * @return
     */
    public static StonePermission with(Activity activity){
        return new StonePermission(activity);
    }
    public static StonePermission with(Fragment fragment){
        return new StonePermission(fragment);
    }

    /**
     * 添加请求码
     * @param requestCode
     * @return
     */
    public StonePermission addRequestCode(int requestCode){
        this.mRequestCode=requestCode;
        return this;
    }

    /**
     * 添加申请权限
     * @param permissions
     * @return
     */
    public StonePermission permissions(String... permissions){
        this.mPermissions=permissions;
        return this;
    }

    /**
     * 执行请求权限
     */
    public void request(){
        realRequestPermission(mObject,mRequestCode,mPermissions);
    }

    /**
     * 正在处理请求权限的地方
     * @param object
     * @param requestCode
     * @param permissions
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void realRequestPermission(Object object, int requestCode, String... permissions){
        //1.如果小于6.0，直接调用成功方法
        if(!PermissionUtil.isOverMarshmallow()){
            PermissionUtil.executeSuccessMethod(object,requestCode);
            return;
        }
        //2.0 检查没有申请的权限
        List<String> deniedPermissions= PermissionUtil.checkAndObtainDeniedPermissions(object,permissions);
        if(deniedPermissions.size()>0){
            //3.0 申请权限
            ActivityCompat.requestPermissions(PermissionUtil.getActivity(object),deniedPermissions.toArray(new String[deniedPermissions.size()]),requestCode);
        }else {
            PermissionUtil.executeSuccessMethod(object,requestCode);
        }
    }






    /**
     * 在 onRequestPermissionsResult 中调用，处理权限申请结果
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Activity activity,int requestCode,String[] permissions,int[] grantResults){
        requestPermissionsResult(activity,requestCode,permissions,grantResults);
    }
    /**
     * 在 onRequestPermissionsResult 中调用，处理权限申请结果
     * @param fragment
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions, int[] grantResults){
        requestPermissionsResult(fragment,requestCode,permissions,grantResults);
    }

    /**
     * 真正回调的地方
     * @param object
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    private static void requestPermissionsResult(Object object,int requestCode,String[] permissions,int[] grantResults){
        List<String> deniedPermissions=new ArrayList<>();
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                deniedPermissions.add(permissions[i]);
            }
        }

        if(deniedPermissions.size() > 0){
            PermissionUtil.executeFaildMethod(object,requestCode);
        }else {
            PermissionUtil.executeSuccessMethod(object,requestCode);
        }
    }









}
