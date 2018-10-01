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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import qunaticheart.com.marvelmagazine.Base.BaseFragment;
import qunaticheart.com.marvelmagazine.BroadCast.SystemUtil;
import qunaticheart.com.marvelmagazine.Conexao.Connect;
import qunaticheart.com.marvelmagazine.Conexao.Model.ListMagazine;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Controller.MainController;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.Adapter.AdapterMagazineList;
import qunaticheart.com.marvelmagazine.Utils.RecyclerViewUtil;
import qunaticheart.com.marvelmagazine.View.MainMagazineActivity;
import retrofit2.Response;

public class ListMagazinesFragment extends BaseFragment implements MainMagazineActivity.searchMagazine {

    //Database For Magazines
    private static List<MagazineData> databaseMagazine = new ArrayList<>();
    private static List<MagazineData> databaseMagazineTmp = new ArrayList<>();

    //RecyclerView
    @SuppressLint("StaticFieldLeak")
    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static AdapterMagazineList adapter;

    //SwipeRefreshLayout
    @SuppressLint("StaticFieldLeak")
    private static SwipeRefreshLayout refreshLayout;

    //Connect
    @SuppressLint("StaticFieldLeak")
    private static Connect connect;
    private static int cursor = 0;
    @SuppressLint("StaticFieldLeak")
    private static MainController controller;


    //Inifnity Scroll
    private static boolean loading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listmagazine, container, false);

        initVars(view);
        initActions();
        initRecyclerView(databaseMagazine);

        return view;
    }

    private void initVars(View view) {

        activity = getActivity();
        controller = new MainController(activity);
        connect = new Connect(activity);
        MainMagazineActivity.setSearchMagazine(this);

        recyclerView = view.findViewById(R.id.recycler_view_recycler_view);
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout_recycler_view);
    }

    private void initActions() {
        connect.getDataFrom(Connect.Connection.initialData, false, cursor);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (SystemUtil.connection()) {
                    controller.deleteTableMagazine();
                    databaseMagazine.clear();
                    cursor = 0;
                    initRecyclerView(databaseMagazine);
                    newConection();
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void initRecyclerView(List<MagazineData> list) {

        List<MagazineData> offlineList = controller.getListOffline();
        if (offlineList.size() > 0) {
            list.addAll(offlineList);
            cursor = list.size();
        }

        GridLayoutManager gridLayoutManager = RecyclerViewUtil.gridLayoutManager(getActivity());
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new AdapterMagazineList(
                activity,
                list
        );
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
    }

    private static RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (SystemUtil.connection()) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (!loading && gridLayoutManager.getItemCount() == (gridLayoutManager.findLastVisibleItemPosition() + 1)) {
                    newConection();
                    loading = true;
                }

            }
        }
    };

    //=====================================================

    private static void newConection() {
        connect.getDataFrom(Connect.Connection.initialData, true, cursor);
    }

    public static void initList(Response<ListMagazine> wsResponse) {
        refreshLayout.setRefreshing(false);

        List<MagazineData> list = new ArrayList<>();

        int quant = Objects.requireNonNull(wsResponse.body()).getData().getResults().size();

        if (quant > 0) {
            for (int i = 0; i < quant; i++) {

                MagazineData magazine = Objects.requireNonNull(wsResponse.body()).getData().getResults().get(i);
                list.add(i, magazine);
                databaseMagazine.add(i, magazine);
                controller.addMagazine(magazine);

            }

            adapter.addList(list);
            cursor = cursor + Objects.requireNonNull(wsResponse.body()).getData().getLimit();
            loading = false;
        } else {
            adapter.addEndView();
        }
    }

    @Override
    public void searchMagazine(String textSearsh) {
        searshFilterMagazine(textSearsh);
    }

    public void searshFilterMagazine(String name) {
        name = name.toLowerCase(Locale.getDefault());
        databaseMagazineTmp.clear();

        if (name.length() == 0) {
            initRecyclerView(databaseMagazine);
        } else {
            for (MagazineData magazine : databaseMagazine) {
                if (magazine.getTitle().toLowerCase(Locale.getDefault()).contains(name)) {
                    databaseMagazineTmp.add(magazine);
                }
            }
            initRecyclerView(databaseMagazineTmp);
        }
    }
}

