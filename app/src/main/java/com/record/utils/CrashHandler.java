package com.record.utils;

import android.content.Context;

import com.record.App;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class CrashHandler implements UncaughtExceptionHandler {
    
   public static final String TAG = "CrashHandler";
    
   
//系统默认的UncaughtException处理类 
   private UncaughtExceptionHandler mDefaultHandler;
   
//CrashHandler实例
   private static CrashHandler INSTANCE = new CrashHandler();
   
//程序的Context对象
   private Context mContext;
   
//用来存储设备信息和异常信息
   private Map<String, String> infos = new HashMap<String, String>();

   
//用于格式化日期,作为日志文件名的一部分
   private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private Throwable exc;

   /** 保证只有一个CrashHandler实例 */
   private CrashHandler() {
   }

   /** 获取CrashHandler实例 ,单例模式 */
   public static CrashHandler getInstance() {
       return INSTANCE;
   }

   /**
    * 初始化
    * 
    * @param context
    */
   public void init(Context context) {
       mContext = context;
       
//获取系统默认的UncaughtException处理器
       mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
       
//设置该CrashHandler为程序的默认处理器
       Thread.setDefaultUncaughtExceptionHandler(this);
   }

   /**
    * 当UncaughtException发生时会转入该函数来处理
    */
   @Override
   public void uncaughtException(Thread thread, Throwable ex) {
       if (!handleException(ex) && mDefaultHandler != null) {
           
//如果用户没有处理则让系统默认的异常处理器来处理
           mDefaultHandler.uncaughtException(thread, ex);
     LogManager.e("=====Exception=====" + ex.getCause());

       } else {
           LogManager.e("=======Exception====="+ex.getCause());
//退出程序
         try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           App.getInstance().getForegroundCallbacks().AppExit();
           android.os.Process.killProcess(android.os.Process.myPid());
           System.exit(1);
       }
   }

   /**
    * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
    * 
    * @param ex
    * @return true:如果处理了该异常信息;否则返回false.
    */
   private boolean handleException(Throwable ex) {
       if (ex == null) {
           return false;
       }
//      exc=ex;
//       //使用Toast来显示异常信息
//       new Thread() {
//           @Override
//           public void run() {
//               Looper.prepare();
//               Toast.makeText(mContext, exc.getCause().getMessage(), Toast.LENGTH_LONG).show();
//              Looper.loop();
//           }
//       }.start();
       
//收集设备参数信息
//       collectDeviceInfo(mContext);
       ex.printStackTrace();
////保存日志文件
//       saveCrashInfo2File(ex);
   
       return true;
   }
    
   /**
    * 收集设备参数信息
    * @param ctx
    */
//   public void collectDeviceInfo(Context ctx) {
//       try {
//           PackageManager pm = ctx.getPackageManager();
//           PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
//           if (pi != null) {
//               String versionName = pi.versionName == null ? "null" : pi.versionName;
//               String versionCode = pi.versionCode + "";
//               infos.put("versionName", versionName);
//               infos.put("versionCode", versionCode);
//           }
//       } catch (NameNotFoundException e) {
//           Log.e(TAG, "an error occured when collect package info", e);
//       }
//       Field[] fields = Build.class.getDeclaredFields();
//       for (Field field : fields) {
//           try {
//               field.setAccessible(true);
//               infos.put(field.getName(), field.get(null).toString());
//               Log.d(TAG, field.getName() + " : " + field.get(null));
//           } catch (Exception e) {
//               Log.e(TAG, "an error occured when collect crash info", e);
//           }
//       }
//   }

   /**
    * 保存错误信息到文件中
    * 
    * @param ex
    * @return  返回文件名称,便于将文件传送到服务器
    */
//   private String saveCrashInfo2File(Throwable ex) {
//
//       StringBuffer sb = new StringBuffer();
//       for (Map.Entry<String, String> entry : infos.entrySet()) {
//           String key = entry.getKey();
//           String value = entry.getValue();
//           sb.append(key + "=" + value + "\n");
//       }
//
//       Writer writer = new StringWriter();
//       PrintWriter printWriter = new PrintWriter(writer);
//       ex.printStackTrace(printWriter);
//       Throwable cause = ex.getCause();
//       while (cause != null) {
//           cause.printStackTrace(printWriter);
//           cause = cause.getCause();
//       }
//       printWriter.close();
//       String result = writer.toString();
//       sb.append(result);
//       FileOutputStream fos = null;
//       try {
//           long timestamp = System.currentTimeMillis();
//           String time = formatter.format(new Date());
//           String fileName = "crash-" + time + ".txt";
//           if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//               String path = Environment.getExternalStorageDirectory().getPath()+"/crash/";
//               File dir = new File(path);
//               if (!dir.exists()) {
//                   dir.mkdirs();
//               }
//               fos = new FileOutputStream(path + fileName);
//               fos.write(sb.toString().getBytes());
//
//           }
//           return fileName;
//       } catch (Exception e) {
//           Log.e(TAG, "an error occured while writing file...", e);
//       }
//       finally {
//           if(null!=fos){
//               try {
//                   fos.close();
//               } catch (IOException e) {
//                  LogManager.e(e.getMessage());
//               }
//           }
//       }
//       return null;
//   }
 }
