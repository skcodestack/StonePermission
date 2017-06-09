# StonePermission

Android6.0运行时权限处理框架

### 使用方式：

	dependencies {
	   
	    compile 'github.skcodestack:stonepermissionlib:1.0.3'
	    
	}

	
	//请求权限
	StonePermission.with(this)
	  .addRequestCode(CALL_REQUEST_CODE)
	  .permissions(Manifest.permission.CALL_PHONE)
	  .request();

	
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//将申请权限处理交给StonePermission
		StonePermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
	}



	//成功授权后调用
	@PermissionSuccess(requestCode = CALL_REQUEST_CODE)
	public void success() {

		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri data = Uri.parse("tel:" + "10086");
		intent.setData(data);
		startActivity(intent);

	}
	//申请权限失败后调用
	@PermissionFail(requestCode = CALL_REQUEST_CODE)
	private void  faild(){

		Toast.makeText(MainActivity.this,"申请权限失败",Toast.LENGTH_SHORT).show();

	}
    
    
    
    
