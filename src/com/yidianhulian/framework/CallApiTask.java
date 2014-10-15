package com.yidianhulian.framework;

import org.json.JSONObject;

import android.os.AsyncTask;


public class CallApiTask extends AsyncTask<Object, Void, JSONObject> {
	private int what;
	private CallApiListener listener;
	private Object[] params;
	
	/**
	 * 调用api
	 * 
	 * @param what
	 * @param title
	 * @param listener
	 */
	public static  void doCallApi(int what, CallApiListener listener, Object...params){
		new CallApiTask(what, listener, params).execute(params);
	}
	
	public CallApiTask(int what, CallApiListener listener, Object...params){
		this.what 		= what;
		this.listener 	= listener;
		this.params 	= params;
	}
	
	
	@Override
	protected void onPreExecute() {
		
	}

	@Override
	protected JSONObject doInBackground(Object... params) {
		return listener.callApi(what, params);
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		listener.handleResult(what, result, params);
		this.cancel(true);
	}
	
	public interface CallApiListener{

		/**
		 * 调用接口，返回json对象，该方法会在Task线程中调用，<b><font color="red">所以不要在该方法中操作UI上的东西</font></b>
		 * what 一个activity调用多个api时what用于区分是那个api
		 * @return
		 */
		public abstract JSONObject callApi(int what, Object...params);

		/**
		 * 接口调用成功的回调
		 *  what 一个activity调用多个api时what用于区分是那个api
		 *  
		 * @param result JSONObject 服务返回的完整json结果
		 * 
		 */
		public abstract void handleResult(int what, JSONObject result, Object...params);
	}
}