package qunaticheart.com.marvelmagazine.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import qunaticheart.com.marvelmagazine.Conexao.Model.Searsh;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.LoggerUtils;
import qunaticheart.com.marvelmagazine.Utils.Searsh.AdapterSearsh;
import qunaticheart.com.marvelmagazine.Utils.Searsh.DB_Busca;
import qunaticheart.com.marvelmagazine.View.Fragments.ListMagazinesFragment;
import qunaticheart.com.marvelmagazine.View.Fragments.LikeMagazineFragment;
import qunaticheart.com.marvelmagazine.View.Fragments.Adapter.ViewPagerAdapter;

public class MainMagazineActivity extends BaseActivity {

    public static void setLoadData(LoadData mLoadData) {
        loadData = mLoadData;
    }

    private static LoadData loadData;

    public static void setSearchMagazine(MainMagazineActivity.searchMagazine searchMagazine) {
        MainMagazineActivity.searchMagazine = searchMagazine;
    }

    private static searchMagazine searchMagazine;

    //Searsh
    private LinearLayout llSearsh;
    private TextView searshBarText;
    static List<Searsh> database = new ArrayList<>();
    static List<Searsh> databaseTmp = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private static ListView listView_busca;
    private static PopupWindow popupWindow;
    private static Dialog dialogSearsh;

    //ViewPager
    private ViewPager viewPager;

    //Fragments
    LikeMagazineFragment likeMagazineFragment;
    ListMagazinesFragment listMagazinesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        initActions();

    }

    public static boolean getStatusNetwork(){
        return connected;
    }

    private void initVars() {

        llSearsh = findViewById(R.id.ll_include_searchbar);
        searshBarText = findViewById(R.id.tv_include_busca);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initActions() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);
                if (position == 1) {
                    loadData.load();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        llSearsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearhDialog();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        listMagazinesFragment = new ListMagazinesFragment();
        likeMagazineFragment = new LikeMagazineFragment();
        adapter.addFragment(listMagazinesFragment, getString(R.string.comix_title));
        adapter.addFragment(likeMagazineFragment, getString(R.string.like_title));
        viewPager.setAdapter(adapter);
    }

    public void openSearhDialog() {

        dialogSearsh = new Dialog(activity, R.style.DialogFullscreen);
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
                    String textCell = hmAux.getTextSearsh();
                    searshBarText.setText(textCell);
                    viewPager.setCurrentItem(0);
                    searchMagazine.searchMagazineList(textCell);
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
                        LoggerUtils.callToast(activity, getString(R.string.search_something));
                    } else {
                        DB_Busca db_busca = new DB_Busca(activity);//conexao

                        db_busca.createDataBase();
                        db_busca.insertData(textSearsh);
                        searshBarText.setText(textSearsh);
//                        searshFilterMagazine(textSearsh);
                        viewPager.setCurrentItem(0);
                        searchMagazine.searchMagazineList(textSearsh);
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
                searchFilter(textSearsh);
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.text_search_magazine));
        try {
            activity.startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException a) {
            LoggerUtils.callToast(activity, "Error");
        }
    }

    public void searchFilter(String name) {
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
//                    searshFilterMagazine(result.get(0));
                    viewPager.setCurrentItem(0);
                    searchMagazine.searchMagazineList(result.get(0));
                    dialogSearsh.dismiss();
                }
                break;
            }
        }
    }

    public interface LoadData {
        void load();
    }

    public interface searchMagazine {
        void searchMagazineList(String textSearsh);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
