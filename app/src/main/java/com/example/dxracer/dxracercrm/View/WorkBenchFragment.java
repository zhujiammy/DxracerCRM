package com.example.dxracer.dxracercrm.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dxracer.dxracercrm.Adapter.MyAdapter;
import com.example.dxracer.dxracercrm.Interface.WorkBenchInterface;
import com.example.dxracer.dxracercrm.Presenter.WorkBenchPresenter;
import com.example.dxracer.dxracercrm.R;

public class WorkBenchFragment extends Fragment {
    private View view;
    private RecyclerView home_rv;
    private WorkBenchPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.workbenchfragment,container,false);
        initUI();
        return view;
    }

    private void initUI(){



        home_rv = (RecyclerView) view.findViewById(R.id.home_Rv);
        presenter = new WorkBenchPresenter(getContext());
        final MyAdapter adapter = new MyAdapter(presenter.getdata());
        home_rv.setAdapter(adapter);
        adapter.setOnitemClickListener(new MyAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.intentActivity(view,position);
            }
        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        home_rv.setLayoutManager(layoutManager);




    }
}
