package qunaticheart.com.marvelmagazine.View.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import qunaticheart.com.marvelmagazine.Base.BaseFragment;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Controller.MainController;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.Adapter.AdapterMagazineList;
import qunaticheart.com.marvelmagazine.Utils.RecyclerViewUtil;
import qunaticheart.com.marvelmagazine.View.MainMagazineActivity;

public class LikeMagazineFragment extends BaseFragment implements MainMagazineActivity.LoadData {

    //Database For Magazines
    private static List<MagazineData> databaseMagazine = new ArrayList<>();

    //RecyclerView
    @SuppressLint("StaticFieldLeak")
    private static RecyclerView recyclerView;

    //SwipeRefreshLayout
    @SuppressLint("StaticFieldLeak")
    private static SwipeRefreshLayout refreshLayout;

    @SuppressLint("StaticFieldLeak")
    private static MainController controller;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked, container, false);

        initVars(view);
        initActions();
        initRecyclerView(databaseMagazine);

        return view;
    }

    private void initVars(View view) {
        activity = getActivity();
        controller = new MainController(activity);
        MainMagazineActivity.setLoadData(this);
        recyclerView = view.findViewById(R.id.recycler_view_recycler_view);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout_recycler_view);
    }

    private void initActions() {

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initListLike();
            }
        });

    }

    private void initListLike() {
        databaseMagazine = controller.getListLike();
        initRecyclerView(databaseMagazine);
        refreshLayout.setRefreshing(false);
    }

    private void initRecyclerView(List<MagazineData> list) {

        GridLayoutManager gridLayoutManager = RecyclerViewUtil.gridLayoutManager(getActivity());
        recyclerView.setLayoutManager(gridLayoutManager);
        AdapterMagazineList adapter = new AdapterMagazineList(
                activity,
                list
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void load() {
        initListLike();
    }

}
