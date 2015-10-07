package com.R.adapter;

import java.util.List;

import com.R.CopyDemo.R;
import com.R.util.TextManager;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {

	private List<String> mTitleArray;// 标题列表
	private LayoutInflater inflater = null;
	private Context mContext;
	private PopupWindow popupWindow;
	private static final int SHOW_TIME = 1000;

	/**
	 * Adapter构造方法
	 * 
	 * @param titleArray
	 */
	public MyAdapter(Context context, List<String> titleArray) {
		// TODO Auto-generated constructor stub
		this.mTitleArray = titleArray;
		this.mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initPopupWindow(inflater);
	}

	/**
	 * 获取总数
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitleArray.size();
	}

	/**
	 * 获取Item对象
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTitleArray.get(position);
	}

	/**
	 * 获取Item的ID
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_layout, null);
			holder.titleTv = (TextView) convertView.findViewById(R.id.item_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置
		holder.titleTv.setText(mTitleArray.get(position));
		holder.titleTv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {// 长按事件
				// TODO Auto-generated method stub
				showPop(v);
				copyTv.setOnTouchListener(new tvOnTouch(mContext, position));
				deleteTv.setOnTouchListener(new tvOnTouch(mContext, position));
				return false;
			}
		});

		return convertView;
	}

	private TextView copyTv, deleteTv;

	/**
	 * 初始化Popupwindow
	 * 
	 * @param inflater
	 */
	private void initPopupWindow(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.pop_item_layout, null);
		popupWindow = new PopupWindow(view, 200, 100);
		copyTv = (TextView) view.findViewById(R.id.pop_copy_tv);
		deleteTv = (TextView) view.findViewById(R.id.pop_delete_tv);
	}

	/**
	 * 触摸事件
	 * 
	 * @author zihao
	 * 
	 */
	class tvOnTouch implements OnTouchListener {
		private Context mContext;
		private int mPosition;

		public tvOnTouch(Context context, int position) {
			// TODO Auto-generated method stub
			this.mContext = context;
			this.mPosition = position;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.pop_copy_tv) {
				TextView tv = (TextView) v;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下
					tv.setTextColor(0xff00CD66);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开
					tv.setTextColor(0xffffffff);
					TextManager.copyText(mContext, mTitleArray.get(mPosition));
					Toast.makeText(mContext, "复制成功", SHOW_TIME).show();

					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			} else {
				TextView tv = (TextView) v;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下
					tv.setTextColor(0xff00CD66);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开
					tv.setTextColor(0xffffffff);
					mTitleArray.remove(mPosition);
					notifyDataSetChanged();
					Toast.makeText(mContext, "删除成功", SHOW_TIME).show();

					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			}
			return true;
		}

	}

	/**
	 * Popupwindow显示
	 * 
	 * @param v
	 */
	@SuppressWarnings("deprecation")
	private void showPop(View v) {
		popupWindow.setFocusable(false);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());// 设置此项可点击Popupwindow外区域消失，注释则不消失

		// 设置出现位置
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.getWidth() / 2 - popupWindow.getWidth() / 2,
				location[1] - popupWindow.getHeight());
	}

	static class ViewHolder {
		TextView titleTv;
	}

}
