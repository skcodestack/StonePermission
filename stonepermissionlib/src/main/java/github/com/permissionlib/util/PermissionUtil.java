package github.com.permissionlib.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import github.com.permissionlib.annotation.PermissionFail;
import github.com.permissionlib.annotation.PermissionSuccess;


/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/5/22
 * Version  1.0
 * Description:
 */

public class PermissionUtil {

    /**
     * 判断当前版本是否大于等于6.0
     * @return
     */
    public static boolean  isOverMarshmallow(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.M;
    }

    /**
     * 执行成功的方法
     * @param object
     * @param requestCode
     */
    public static void executeSuccessMethod(Object object, int  requestCode){
        Method method = findMethodWithCode(object.getClass(), PermissionSuccess.class, requestCode);
        executeMethod(object,method);
    }

    /**
     * 执行失败的方法
     * @param object
     * @param requestCode
     */
    public static void executeFaildMethod(Object object, int  requestCode){
        Method method = findMethodWithCode(object.getClass(), PermissionFail.class, requestCode);
        executeMethod(object,method);
    }

    /**
     * 执行方法
     * @param object
     * @param method
     */
    private static void executeMethod(Object object,Method method){
        if(method!=null){;
            if(!method.isAccessible())
                method.setAccessible(true);

            try {
                Object[] obj=new Object[]{};
                method.invoke(object,obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 找到类中带有指定注解的方法
     * @param clazz
     * @param annotation
     * @param requestCode
     * @param <T>
     * @return
     */
    private static <T extends Annotation> Method findMethodWithCode(Class clazz, Class<T> annotation, int  requestCode){
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(annotation)){
                if(annotation.equals(PermissionSuccess.class)){
                    if(requestCode == method.getAnnotation(PermissionSuccess.class).requestCode())
                        return method;

                }else if(annotation.equals(PermissionFail.class)){
                    if(requestCode == method.getAnnotation(PermissionFail.class).requestCode())
                        return method;
                }
            }
        }
        return null;
    }

    /**
     * 返回没有申请的权限
     * @param object
     * @param permissions
     * @return
     */
    public static List<String> checkAndObtainDeniedPermissions(Object object, String[] permissions) {
        List<String> deniedPermissions=new ArrayList<>();
        if(permissions==null){
            return deniedPermissions;
        }

        for (String permission : permissions) {
            if(ContextCompat.checkSelfPermission(getActivity(object),permission) != PackageManager.PERMISSION_GRANTED){
                deniedPermissions.add(permission);
            }
        }


        return deniedPermissions;
    }

    /**
     * 获取Activity
     * @param object
     * @return
     */
    public static Activity getActivity(Object object){
        if(object instanceof Fragment){
            return ((Fragment)object).getActivity();
        }else if(object instanceof Activity){
            return (Activity)object;
        }
        return null;
    }
}
