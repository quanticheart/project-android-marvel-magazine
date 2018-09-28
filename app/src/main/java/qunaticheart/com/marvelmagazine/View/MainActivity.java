package qunaticheart.com.marvelmagazine.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import qunaticheart.com.marvelmagazine.Base.BaseActivity;
import qunaticheart.com.marvelmagazine.BroadCast.MyReceiver;
import qunaticheart.com.marvelmagazine.BroadCast.SystemUtil;
import qunaticheart.com.marvelmagazine.Conexao.Connect;
import qunaticheart.com.marvelmagazine.Conexao.Model.ListMagazine;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Conexao.Model.Searsh;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.Adapter.AdapterMagazineList;
import qunaticheart.com.marvelmagazine.Utils.LoggerUtils;
import qunaticheart.com.marvelmagazine.Utils.RecyclerViewUtil;
import qunaticheart.com.marvelmagazine.Utils.Searsh.AdapterSearsh;
import qunaticheart.com.marvelmagazine.Utils.Searsh.DB_Busca;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    //Searsh
    private LinearLayout llSearsh;
    private TextView searshBarText;
    //SwipeRefreshLayout
    @SuppressLint("StaticFieldLeak")
    private static SwipeRefreshLayout refreshLayout;

    //RecyclerView
    @SuppressLint("StaticFieldLeak")
    private static RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static AdapterMagazineList adapter;

    //Connect
    @SuppressLint("StaticFieldLeak")
    private static Connect connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = new Connect(activity);
        connect.getDataFrom(1, false, cursor);

        initBroadcast();
        initVars();
        initActions();
        initRecyclerView(databaseMagazine);

    }

    private void initBroadcast() {

        new SystemUtil(activity);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        MyReceiver myReceiver = new MyReceiver();
        registerReceiver(myReceiver, filter);

    }

    private void initVars() {
        llSearsh = findViewById(R.id.ll_include_searchbar);
        searshBarText = findViewById(R.id.tv_include_busca);
        recyclerView = findViewById(R.id.recycler_view_recycler_view);
        refreshLayout = findViewById(R.id.swipe_refresh_layout_recycler_view);
    }

    private void initActions() {

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (SystemUtil.connection()) {
                    databaseMagazine.clear();
                    cursor = 0;
                    initRecyclerView(databaseMagazine);
                    newConection();
                }else{
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        llSearsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearhDialog();
            }
        });
    }

    private static void initRecyclerView(List<MagazineData> list) {
        GridLayoutManager gridLayoutManager = RecyclerViewUtil.gridLayoutManager(activity);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new AdapterMagazineList(
                activity,
                list
        );
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
    }

    private static boolean loading = false;
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
        connect.getDataFrom(1, true, cursor);
    }

    static int cursor = 0;

    private static List<MagazineData> databaseMagazine = new ArrayList<>();

    public static void initList(Response<ListMagazine> wsResponse) {
        refreshLayout.setRefreshing(false);

        List<MagazineData> list = new ArrayList<>();

        int quant = Objects.requireNonNull(wsResponse.body()).getData().getResults().size();

        if (quant > 0) {
            for (int i = 0; i < quant; i++) {
                list.add(i, Objects.requireNonNull(wsResponse.body()).getData().getResults().get(i));
                databaseMagazine.add(i, Objects.requireNonNull(wsResponse.body()).getData().getResults().get(i));
            }

            adapter.addList(list);
            cursor = cursor + Objects.requireNonNull(wsResponse.body()).getData().getLimit();
            loading = false;
        } else {
            adapter.addEndView();
        }

    }

    //=====================================================

    static List<Searsh> database = new ArrayList<>();
    static List<Searsh> databaseTmp = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static ListView listView_busca;
    private static PopupWindow popupWindow;
    private static Dialog dialogSearsh;

    public void openSearhDialog() {

        dialogSearsh = new Dialog(MainActivity.this, R.style.DialogFullscreen);
        dialogSearsh.setContentView(R.layout.dialog_searchbar);
        Objects.requireNonNull(dialogSearsh.getWindow()).getAttributes().windowAnimations = R.style.DialogNoAnimation;

        ImageView img_dialog_fullscreen_close = dialogSearsh.findViewById(R.id.img_dialog_fullscreen_close);
        ImageView img_dialog_fullscreen_p_voz = dialogSearsh.findViewById(R.id.img_dialog_fullscreen_p_voz);
        final ImageView img_dialog_fullscreen_menu = dialogSearsh.findViewById(R.id.img_dialog_fullscreen_menu);

        final EditText editText = dialogSearsh.findViewById(R.id.et_busca);
        final DB_Busca db_busca = new DB_Busca(activity);//conexao

        final RelativeLayout relativeLayout_busca = dialogSearsh.findViewById(R.id.rl_dialog_busca);
        final LinearLayout linearLayout_busca = dialogSearsh.findViewById(R.id.ll_dialog_buscas);

        listView_busca = dialogSearsh.findViewById(R.id.lv_dialog_buscas);
        database = db_busca.Obter_Dados_Historico_Busca();

        if (database.size() > 0) {

            linearLayout_busca.setVisibility(View.VISIBLE);
            relativeLayout_busca.setVisibility(View.GONE);

            AdapterSearsh adapter_dialog_busca = new AdapterSearsh(activity,
                    R.layout.cell_searchbar,
                    database
            );
            listView_busca.setAdapter(adapter_dialog_busca);
            listView_busca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    final Searsh hmAux = (Searsh) parent.getItemAtPosition(position);
                    String texto_celula_pesquisa = hmAux.getTextSearsh();
                    searshBarText.setText(texto_celula_pesquisa);
                    dialogSearsh.dismiss();
                }
            });

        } else {
            linearLayout_busca.setVisibility(View.GONE);
            relativeLayout_busca.setVisibility(View.VISIBLE);
        }


        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") final View popupView = layoutInflater.inflate(R.layout.dialog_serachbar_menu, null);
        popupWindow = new PopupWindow(popupView, 700, ViewGroup.LayoutParams.WRAP_CONTENT);

        img_dialog_fullscreen_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
                else
                    popupWindow.showAsDropDown(img_dialog_fullscreen_menu, 50, 0);
            }
        });

        TextView menu1 = popupView.findViewById(R.id.dialog_searchbar_menu_1);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_busca.DeletaBanco_Historico_Busca();
                popupWindow.dismiss();
                linearLayout_busca.setVisibility(View.GONE);
                relativeLayout_busca.setVisibility(View.VISIBLE);
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    String textSearsh = editText.getText().toString().trim();

                    if (textSearsh.isEmpty() || textSearsh.equals("")) {
                        LoggerUtils.callToast(activity, "Digite Algo Para Pesquisa");
                    } else {
                        DB_Busca db_busca = new DB_Busca(activity);//conexao

                        db_busca.createDataBase();
                        db_busca.insertData(textSearsh);
                        searshBarText.setText(textSearsh);
                        searshFilterMagazine(textSearsh);
                        dialogSearsh.dismiss();
                    }


                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textSearsh = editText.getText().toString().trim();
                searshFilter(textSearsh);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        img_dialog_fullscreen_p_voz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searshSpeech();
            }
        });

        img_dialog_fullscreen_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSearsh.dismiss();
            }
        });

        dialogSearsh.show();
    }

    private void searshSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "O quer pesquisa?");
        try {
            activity.startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException a) {
            LoggerUtils.callToast(activity, "Error ao Pesquisar");
        }
    }

    public void searshFilter(String name) {
        name = name.toLowerCase(Locale.getDefault());
        databaseTmp.clear();

        AdapterSearsh adapter_dialog_busca = new AdapterSearsh(
                activity,
                R.layout.cell_searchbar,
                databaseTmp
        );

        listView_busca.setAdapter(adapter_dialog_busca);

        if (name.length() == 0) {

            AdapterSearsh adapter_dialog_busca1 = new AdapterSearsh(activity,
                    R.layout.cell_searchbar,
                    database
            );

            listView_busca.setAdapter(adapter_dialog_busca1);

        } else {
            for (Searsh dados_pesquisa : database) {
                if (dados_pesquisa.getTextSearsh().toLowerCase(Locale.getDefault()).contains(name)) {
                    databaseTmp.add(dados_pesquisa);
                }
            }
        }
    }

    static List<MagazineData> databaseMagazineTmp = new ArrayList<>();

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

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    DB_Busca db_busca = new DB_Busca(activity);//conexao
                    db_busca.createDataBase();
                    db_busca.insertData(result.get(0));
                    searshBarText.setText((result.get(0)));
                    searshFilterMagazine(result.get(0));
                    dialogSearsh.dismiss();
                }
                break;
            }

        }
    }

}