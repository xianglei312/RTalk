package com.R.popdemo;

import java.util.ArrayList;
import java.util.List;

import com.R.CopyDemo.R;
import com.R.adapter.MyAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * 主界面
 * 
 * @author Reginer
 * 
 */
public class MainActivity extends Activity {

	private ListView mListView;// ListView
	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.my_list);
		myAdapter = new MyAdapter(this, getListData());
		mListView.setAdapter(myAdapter);
	}

	/**
	 * 获取ListView数据
	 * 
	 * @return
	 */
	private List<String> getListData() {
		List<String> titleArray = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			titleArray.add("这是第" + i + "个子项");
		}
		return titleArray;
	}

}