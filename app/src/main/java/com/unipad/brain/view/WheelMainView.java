package com.unipad.brain.view;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.unipad.brain.R;
/**
 *   
 * @author Administrator
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WheelMainView extends RelativeLayout {
	/** Called when the activity is first created. */
	private LinearLayout layout = null ;
	private WheelView yearWV = null;
	private WheelView monthWV = null;
	private WheelView dayWV = null;
	private WheelView hourWV = null;
	private WheelView minuteWV = null;
//	private Button reset_Btn = null;
	int year;
	int month;
	// 滚轮上的数据，字符串数组
	String[] yearArrayString = null;
	String[] dayArrayString = null;
	String[] monthArrayString = null;
	String[] hourArrayString = null;
	String[] minuteArrayString = null;
	Calendar c = null;
	private Context context;
	
	public WheelMainView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public WheelMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}



	public WheelMainView(Context context) {
		super(context);
		this.context = context;
		setView();
	}


	public void setView() {
		View view  = View.inflate(context, R.layout.linearlayout_imitate_wheel_main, this);
		layout = (LinearLayout)view.findViewById(R.id.llayout);
		System.out.println("layout.getgetTop()-->" + layout.getTop());
		System.out.println("layout.getBottom()-->" + layout.getBottom());
		
		// 得到相应的数组
		c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		yearArrayString = getYEARArray(y - 100, 101);
		monthArrayString = getDayArray(12);
		hourArrayString = getHMArray(24);
		minuteArrayString = getHMArray(60);
		// 获取当前系统时间
		initView(view);
	}
	public void initView(View view) {
//		reset_Btn = (Button) view.findViewById(R.id.reset_btn);
		yearWV = (WheelView) view.findViewById(R.id.time_year);
		monthWV = (WheelView) view.findViewById(R.id.time_month);
		dayWV = (WheelView) view.findViewById(R.id.time_day);
		hourWV = (WheelView) view.findViewById(R.id.time_hour);
		minuteWV = (WheelView) view.findViewById(R.id.time_minute);
		// 设置每个滚轮的行数
		yearWV.setVisibleItems(5);
		monthWV.setVisibleItems(5);
		dayWV.setVisibleItems(5);
		hourWV.setVisibleItems(5);
		minuteWV.setVisibleItems(5);

		// 设置滚轮的标签
		yearWV.setLabel("年");
		monthWV.setLabel("月");
		dayWV.setLabel("日");
		hourWV.setLabel("时");
		minuteWV.setLabel("分");
//		// 地区选择
//		shengWV = (WheelView) findViewById(R.id.place_sheng);
//		shiWV = (WheelView) findViewById(R.id.place_shi);
//		placeTV = (TextView) findViewById(R.id.place_chose);
//
//		shengWV.setVisibleItems(5);
//		shiWV.setVisibleItems(5);
//		
		
		yearWV.setCyclic(true);
		monthWV.setCyclic(true);
		dayWV.setCyclic(true);
		hourWV.setCyclic(true);
		minuteWV.setCyclic(true);
//		shengWV.setCyclic(true);
//		shiWV.setCyclic(true);
		setData();
		
	}
	
	/**
	 * 给滚轮提供数据
	 */
	private void setData() {
		// 给滚轮提供数据
		yearWV.setAdapter(new ArrayWheelAdapter<String>(yearArrayString));
		monthWV.setAdapter(new ArrayWheelAdapter<String>(monthArrayString));
		hourWV.setAdapter(new ArrayWheelAdapter<String>(hourArrayString));
		minuteWV.setAdapter(new ArrayWheelAdapter<String>(minuteArrayString));

		yearWV.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// 获取年和月
				year = Integer.parseInt(yearArrayString[yearWV.getCurrentItem()]);
				month = Integer.parseInt(monthArrayString[monthWV
						.getCurrentItem()]);
				// 根据年和月生成天数数组
				dayArrayString = getDayArray(getDay(year, month));
				// 给天数的滚轮设置数据
				dayWV.setAdapter(new ArrayWheelAdapter<String>(dayArrayString));
				// 防止数组越界
				if (dayWV.getCurrentItem() >= dayArrayString.length) {
					dayWV.setCurrentItem(dayArrayString.length - 1);
				}
				// 显示的时间
				showDate();
			}
		});

		// 当月变化时显示的时间
		monthWV.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// 获取年和月
				year = Integer.parseInt(yearArrayString[yearWV.getCurrentItem()]);
				month = Integer.parseInt(monthArrayString[monthWV
						.getCurrentItem()]);
				// 根据年和月生成天数数组
				dayArrayString = getDayArray(getDay(year, month));
				// 给天数的滚轮设置数据
				dayWV.setAdapter(new ArrayWheelAdapter<String>(dayArrayString));
				// 防止数组越界
				if (dayWV.getCurrentItem() >= dayArrayString.length) {
					dayWV.setCurrentItem(dayArrayString.length - 1);
				}
				// 显示的时间
				showDate();
			}
		});

		// 当天变化时，显示的时间
		dayWV.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				showDate();
			}
		});

		// 当小时变化时，显示的时间
		hourWV.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				showDate();
			}
		});

		// 当分钟变化时，显示的时间
		minuteWV.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				showDate();
			}
		});

		// 把当前系统时间显示为滚轮默认时间
		setOriTime();

//		// 点击回到系统时间
//		reset_Btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 把当前系统时间显示为滚轮默认时间
//				c = Calendar.getInstance();
//				setOriTime();
//			}
//		});

//		chosePlce();
	}

	// 设定初始时间
	void setOriTime() {
		yearWV.setCurrentItem(getNumData(c.get(Calendar.YEAR) + "",
				yearArrayString));
		monthWV.setCurrentItem(getNumData(c.get(Calendar.MONTH) + 1 + "",
				monthArrayString) + 0);
		hourWV.setCurrentItem(getNumData(c.get(Calendar.HOUR_OF_DAY) + "",
				hourArrayString));
		minuteWV.setCurrentItem(getNumData(c.get(Calendar.MINUTE) + "",
				minuteArrayString));

		dayArrayString = getDayArray(getDay(year, month));
		dayWV.setAdapter(new ArrayWheelAdapter<String>(dayArrayString));
		dayWV.setCurrentItem(getNumData(c.get(Calendar.DAY_OF_MONTH) + "",
				dayArrayString));

		// 初始化显示的时间
		showDate();
	}

	// 显示时间
	void showDate() {
		createDate(yearArrayString[yearWV.getCurrentItem()],monthArrayString[monthWV.getCurrentItem()],dayArrayString[dayWV.getCurrentItem()],
				hourArrayString[hourWV.getCurrentItem()],minuteArrayString[minuteWV.getCurrentItem()]);
	}

	// 生成时间
	void createDate(String year, String month, String day, String hour,String minute) {
		String dateStr = year + "/" + month + "/" + day + "";// + hour + "时" + minute + "分";
//		time_TV.setText("选择时间为：" + dateStr);
		if(null == changingListener){
			return;
		}
		changingListener.onChanging(dateStr);
	}

	// 在数组Array[]中找出字符串s的位置
	int getNumData(String s, String[] Array) {
		int num = 0;
		for (int i = 0; i < Array.length; i++) {
			if (s.equals(Array[i])) {
				num = i;
				break;
			}
		}
		return num;
	}

	// 根据当前年份和月份判断这个月的天数
	public int getDay(int year, int month) {
		int day;
		if (year % 4 == 0 && year % 100 != 0) { // 闰年
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				day = 31;
			} else if (month == 2) {
				day = 29;
			} else {
				day = 30;
			}
		} else { // 平年
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				day = 31;
			} else if (month == 2) {
				day = 28;
			} else {
				day = 30;
			}
		}
		return day;
	}

	// 根据数字生成一个字符串数组
	public String[] getDayArray(int day) {
		String[] dayArr = new String[day];
		for (int i = 0; i < day; i++) {
			dayArr[i] = i + 1 + "";
		}
		return dayArr;
	}

	// 根据数字生成一个字符串数组
	public String[] getHMArray(int day) {
		String[] dayArr = new String[day];
		for (int i = 0; i < day; i++) {
			dayArr[i] = i + "";
		}
		return dayArr;
	}

	// 根据初始值start和step得到一个字符数组，自start起至start+step-1
	public String[] getYEARArray(int start, int step) {
		String[] dayArr = new String[step];
		for (int i = 0; i < step; i++) {
			dayArr[i] = start + i + "";
		}
		return dayArr;
	}

   public interface OnChangingListener{
	   void onChanging(String changStr);
   }

   private OnChangingListener changingListener;

	public void setChangingListener(OnChangingListener changingListener) {
		this.changingListener = changingListener;
	}

}