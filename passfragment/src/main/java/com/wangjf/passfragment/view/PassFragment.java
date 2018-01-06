package com.wangjf.passfragment.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhon.suspensionfab.FabAttributes;
import com.azhon.suspensionfab.OnFabClickListener;
import com.azhon.suspensionfab.SuspensionFab;
import com.wangjf.passfragment.R;
import com.wangjf.passfragment.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjf on 18-1-6.
 */

public class PassFragment extends Fragment implements OnFabClickListener {

    private List<Bean.UserBean> mData;
    private RecyclerView mRecyclerView;
    private InfoAdapter mAdapter;
    private SuspensionFab fabMenu;
    private IntfPassFragment mCallBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_passfragment, container, false);

        initRvList(v);
        initMenu(v);

        return v;
    }

    public void initRvList(View v) {
        //微博列表
        mRecyclerView = (RecyclerView) v.findViewById(R.id.id_recycler_view);
        mAdapter = new InfoAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mData = new ArrayList<>();
        for(int i=0;i<50;i++) {
            Bean.UserBean mUserBean = new Bean.UserBean();
            mUserBean.setUserPurpose("Purpose " + i);
            mUserBean.setUserName("Name " + i);
            mUserBean.setUserPass("Pass " + i);
            mUserBean.setUserMore("More " + i);

            mData.add(mUserBean);
        }

        mAdapter.setData(mData);
    }

    public void initMenu(View v){

        fabMenu = (SuspensionFab) v.findViewById(R.id.fab_menu);

        FabAttributes menu_add = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.drawable.menu_add))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();

        FabAttributes menu_locked = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.drawable.menu_locked))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();

        FabAttributes menu_setting = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.drawable.menu_setting))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
                .setPressedTranslationZ(10)
                .setTag(3)
                .build();

        fabMenu.addFab(menu_add,menu_locked,menu_setting);

        fabMenu.setFabClickListener(this);
    }

    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if(tag.equals(1)) {

        } else if(tag.equals(2)) {
            mCallBack.onFinish(false);
        } else if(tag.equals(3)) {

        }
    }

    public static PassFragment newInstance() {
        PassFragment mPassFragment = new PassFragment();
        //添加参数？
        //Bundle args = new Bundle();
        //args.putBoolean("comeFromAccoutActivity", comeFromAccoutActivity);
        //mPassFragment.setArguments(args);
        return mPassFragment;
    }

    public void setCallBack(IntfPassFragment callback) {
        mCallBack = callback;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCallBack.onFinish(true);
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {

        TextView mTvUserInfo;
        LinearLayout mItemBackground;

        public InfoViewHolder(View itemView) {
            super(itemView);
            mTvUserInfo = (TextView) itemView.findViewById(R.id.id_user_info);
            mItemBackground = (LinearLayout) itemView.findViewById(R.id.id_bg_data_item);

        }
    }

    public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder> {

        Context mContext;

        public void setData(List<Bean.UserBean> data) {
            mData = data;
            notifyDataSetChanged();
        }

        public InfoAdapter(Context context) {
            mContext = context;
        }

        @Override
        public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(mContext).inflate(R.layout.layout_data_item, parent, false);

            return new InfoViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final InfoViewHolder holder, int position) {
            //设置数据
            holder.mTvUserInfo.setText(mData.get(position).getUserPurpose());
            if(position % 2 == 0)
                holder.mItemBackground.setBackgroundColor(Color.rgb(0xbb,0xbb,0xbb));
            else
                holder.mItemBackground.setBackgroundColor(Color.rgb(0xee,0xee,0xee));
        }

        @Override
        public int getItemCount() {
            if(mData == null)
                return 0;
            else
                return mData.size();
        }
    }

}
