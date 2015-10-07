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

	private List<String> mTitleArray;// �����б�
	private LayoutInflater inflater = null;
	private Context mContext;
	private PopupWindow popupWindow;
	private static final int SHOW_TIME = 1000;

	/**
	 * Adapter���췽��
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
	 * ��ȡ����
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitleArray.size();
	}

	/**
	 * ��ȡItem����
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTitleArray.get(position);
	}

	/**
	 * ��ȡItem��ID
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

		// ����
		holder.titleTv.setText(mTitleArray.get(position));
		holder.titleTv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {// �����¼�
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
	 * ��ʼ��Popupwindow
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
	 * �����¼�
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
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// ����
					tv.setTextColor(0xff00CD66);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// �ɿ�
					tv.setTextColor(0xffffffff);
					TextManager.copyText(mContext, mTitleArray.get(mPosition));
					Toast.makeText(mContext, "���Ƴɹ�", SHOW_TIME).show();

					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			} else {
				TextView tv = (TextView) v;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// ����
					tv.setTextColor(0xff00CD66);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// �ɿ�
					tv.setTextColor(0xffffffff);
					mTitleArray.remove(mPosition);
					notifyDataSetChanged();
					Toast.makeText(mContext, "ɾ���ɹ�", SHOW_TIME).show();

					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			}
			return true;
		}

	}

	/**
	 * Popupwindow��ʾ
	 * 
	 * @param v
	 */
	@SuppressWarnings("deprecation")
	private void showPop(View v) {
		popupWindow.setFocusable(false);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());// ���ô���ɵ��Popupwindow��������ʧ��ע������ʧ

		// ���ó���λ��
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.getWidth() / 2 - popupWindow.getWidth() / 2,
				location[1] - popupWindow.getHeight());
	}

	static class ViewHolder {
		TextView titleTv;
	}

}
