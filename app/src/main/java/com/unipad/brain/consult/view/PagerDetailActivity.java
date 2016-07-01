package com.unipad.brain.consult.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.unipad.AppContext;
import com.unipad.brain.BasicActivity;
import com.unipad.brain.R;
import com.unipad.brain.consult.entity.CommentBean;
import com.unipad.brain.home.dao.NewsService;
import com.unipad.common.Constant;
import com.unipad.common.ViewHolder;
import com.unipad.common.adapter.CommonAdapter;
import com.unipad.http.HttpConstant;
import com.unipad.observer.IDataObserver;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jiangLu on 2016/6/23.
 */
public class PagerDetailActivity extends BasicActivity implements IDataObserver, View.OnClickListener {

    private NewsService service;

    private List<CommentBean> mCommentDatas =  new ArrayList<CommentBean>();
    private CommentAdapter mCommentAdapter;
    private View mPopupView;
    //资讯的id
    private String articleId;
    private ScaleAnimation sa;
    private PopupWindow mPopupWindows;
    private EditText et_commment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagerdetail);
        articleId = getIntent().getStringExtra("pagerId");

        service = (NewsService) AppContext.instance().getService(Constant.NEWS_SERVICE);
        service.registerObserver(HttpConstant.NOTIFY_GET_COMMENT, this);
        service.registerObserver(HttpConstant.NOTIFY_GET_OPERATE, this);
        //获取数据
        getComment(articleId, 1, 10);

    }

    private void getComment(String pagerId, int page, int size) {
        service.getComment(pagerId, 1, 10);
}


    public void initData() {
        String  id = getIntent().getStringExtra("pagerId");
        String htmlDatas = "http://192.168.0.104:8090/crazybrain-mng" +HttpConstant.GET_NEWS_DETAIL + "?id=" +
                getIntent().getStringExtra("pagerId");
        //新闻部分的 webview
        WebView web_detail = (WebView) findViewById(R.id.pager_detail_webview);
        ListView mListComment = (ListView) findViewById(R.id.newscomment_listview);
        ((TextView)findViewById(R.id.tv_input_comment_text)).setOnClickListener(this);

       //设置返回
        ((TextView)findViewById(R.id.title_back_text)).setOnClickListener(this);
        WebSettings web_set = web_detail.getSettings();
        web_detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        web_set.setSupportZoom(true); // 支持缩放
        web_set.setUseWideViewPort(true);
        web_set.setBlockNetworkImage(false);
        web_set.setJavaScriptEnabled(true);//支持jscrip
        web_set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//内容大小填充
        web_detail.loadUrl(htmlDatas);

        mCommentAdapter = new CommentAdapter(getApplicationContext(), mCommentDatas, R.layout.item_comment_listview);

        mListComment.setAdapter(mCommentAdapter);
    }
    //评论条目的 adapter
    private class CommentAdapter extends CommonAdapter<CommentBean>{

        public CommentAdapter(Context context, List<CommentBean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, CommentBean commentBean) {
            //用户头像；
            ImageView iv_icon = (ImageView)holder.getView(R.id.iv_item_comment_userid);
            String user_photo = AppContext.instance().loginUser.getPhoto();
//    new BitmapUtils(getApplicationContext()).display(iv_icon, commentBean.get);
            //用户名
            ((TextView) holder.getView(R.id.tv_item_username_textview)).setText(commentBean.getUserName());
            //更新时间
            ((TextView)holder.getView(R.id.tv_item_comment_updatetime)).setText(commentBean.getCreateDate());
            // 评论内容
            ((TextView)holder.getView(R.id.tv_item_comment_content)).setText(commentBean.getContent());
        }
    }

    @Override
    public void update(int key, Object o) {
        switch (key) {
            case HttpConstant.NOTIFY_GET_COMMENT:
                mCommentDatas.clear();

                mCommentDatas.addAll((ArrayList<CommentBean>)o);
                mCommentAdapter.notifyDataSetChanged();
                break;
            case HttpConstant.NOTIFY_GET_OPERATE:


            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_text:
                //直接返回
                finish();
                break;
            case R.id.tv_input_comment_text:
                //弹出评论 弹出框； 输入内容 点击提交
                initPopWindows();

                showPopupWindows(v);
                //弹出键盘 输入；
                break;


        }
    }
    private void initPopWindows(){
        mPopupView = View.inflate(getApplicationContext(), R.layout.comment_commit_popup, null);
        //评论内容
        et_commment = (EditText) mPopupView.findViewById(R.id.et_popup_comment_input);
        //提交评论按钮
        Button btn_commit = (Button) mPopupView.findViewById(R.id.btn_comment_commit);

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击提交关闭窗体  用户评论内容

                final String user_comment = et_commment.getText().toString().trim();
                if(TextUtils.isEmpty(user_comment)){
                    Toast.makeText(getApplicationContext(), "内容为空 请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }


                //提交评论内容到服务器
                service.getNewsOperate(articleId, "3", null, user_comment, 0, new Callback.CommonCallback<String>(){

                    @Override
                    public void onSuccess(String s) {
                        //成功之后 直接添加数据到 list集合  然后 刷新数据
                        CommentBean bean = new CommentBean();
                        bean.setId(articleId);
                        bean.setUserId(AppContext.instance().loginUser.getUserId());
                        bean.setUserName(AppContext.instance().loginUser.getUserName());
                        bean.setContent(user_comment);
                        Calendar ca = Calendar.getInstance();
                        int year = ca.get(Calendar.YEAR);//获取年份
                        int month=ca.get(Calendar.MONTH) + 1;//获取月份
                        int day=ca.get(Calendar.DATE);//获取日
                        bean.setCreateDate(year + "-" + month + "-" + day);
                        //最后更新界面
                        mCommentDatas.add(bean);
                        mCommentAdapter.notifyDataSetChanged();
                        bean  = null;
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Toast.makeText(getApplicationContext(), "网络原因 提交失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                //清空输入的内容
                et_commment.setText("");
                //关闭弹出窗体
                closePopup();
            }
        });

        mPopupWindows = new PopupWindow(mPopupView, 600, -2, true);

        mPopupWindows.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //动画效果;
        sa = new ScaleAnimation(0f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(300);
    }

    //关闭弹出窗体
    private void closePopup(){

        if(mPopupWindows != null && mPopupWindows.isShowing()){
            mPopupWindows.dismiss();
        }
    }

    private void showPopupWindows(View parent){
        closePopup();
        //强制弹出软键盘
//        et_commment.requestFocus();
//        InputMethodManager imm = (InputMethodManager) et_commment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        mPopupView.startAnimation(sa);
        mPopupWindows.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear() {
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_COMMENT, this);
        service.unRegisterObserve(HttpConstant.NOTIFY_GET_OPERATE, this);

    }


}
