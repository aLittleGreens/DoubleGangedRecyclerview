package cyk.com.doublegangedrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.List;

import cyk.com.doublegangedrecyclerview.adapter.LeftAdapter;
import cyk.com.doublegangedrecyclerview.base.RvListener;
import cyk.com.doublegangedrecyclerview.itemdecoration.StickyItemDescoration;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<String> list;
    private RecyclerView leftRecycleView;
    private FrameLayout rightContain;
    private Context mContext;
    private LeftAdapter leftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initData();
        initView();
    }

    private void initData() {
        String[] stringArray = getResources().getStringArray(R.array.letter);
        list = Arrays.asList(stringArray);
        leftAdapter = new LeftAdapter(mContext, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                Log.e(TAG,"id;"+id+",position:"+position);
                leftAdapter.setCheckedPosition(position);
            }
        });
    }

    private void initView() {
        leftRecycleView = (RecyclerView) findViewById(R.id.leftRecycleView);
        rightContain = (FrameLayout) findViewById(R.id.rightContain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        leftRecycleView.setLayoutManager(linearLayoutManager);
//        leftRecycleView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
//        leftRecycleView.addItemDecoration(new LeftAndRightTagDecoration(mContext));

        leftRecycleView.addItemDecoration(new StickyItemDescoration(mContext, new StickyItemDescoration.DecorationCallback() {

            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(list.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return list.get(position).substring(0, 1).toUpperCase();
            }
        }));
        leftRecycleView.setAdapter(leftAdapter);
        initFragment();
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.commit();
    }
}
