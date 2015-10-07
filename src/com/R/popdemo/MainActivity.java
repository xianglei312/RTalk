package com.R.popdemo;

import java.util.ArrayList;
import java.util.List;

import com.R.CopyDemo.R;
import com.R.adapter.MyAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * ������
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
	 * ��ʼ����ͼ
	 */
	private void initView() {
		mListView = (ListView) findViewById(R.id.my_list);
		myAdapter = new MyAdapter(this, getListData());
		mListView.setAdapter(myAdapter);
	}

	/**
	 * ��ȡListView����
	 * 
	 * @return
	 */
	private List<String> getListData() {
		List<String> titleArray = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			titleArray.add("���ǵ�" + i + "������");
		}
		return titleArray;
	}

}