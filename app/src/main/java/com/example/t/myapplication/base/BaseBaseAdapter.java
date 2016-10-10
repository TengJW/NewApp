package com.example.t.myapplication.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBaseAdapter<DataType> extends BaseAdapter {
	protected Context context;
	private LayoutInflater layoutInflater;
	/** 当前适配器内所用数据 */
	private List<DataType> datalist = new ArrayList<DataType>();

	/**
	 * 构造方法 加载布局时需要用到layoutinflater ，在构造方法中初始化
	 *
	 * @param context
	 */
	public BaseBaseAdapter(Context context) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/** 获取适配器内所用数据列表(List集合) */
	public List<DataType> getDataList() {
		return datalist;
	}

	/** 向适配器内所用数据列表(List集合),添加一个数据 */
	public void addDataToAdapter(DataType data) {
		datalist.add(data);
	}

	/** 设置适配器内所用数据列表(List集合)内数据,为指定数据(将先清理原数据) */
	public void setDataTypeToAdapter(DataType data) {
		datalist.clear();
		datalist.add(data);
	}

	/** 设置适配器内所用数据列表(List集合)内数据,为指定集合(将先清理原数据) */
	public void setDataTypeListToAdapter(List<DataType> datas) {
		datalist.clear();
		datalist.addAll(datas);
	}

	@Override
	public int getCount() {
		return datalist == null ? 0 : datalist.size();
	}

	@Override
	public DataType getItem(int position) {
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView, parent);
	}

	/**
	 * @see
	 * @see  //当列表项第一次显示出来,当列表项滑出滑进屏幕时，当列表项数据变化，调用notifyDataSetChanged()方法时 ，都将来调用
	 * @param position
	 *            当前列表项在列表中的位置
	 */
	public abstract View getItemView(int position, View convertView,
									 ViewGroup parent);

}
