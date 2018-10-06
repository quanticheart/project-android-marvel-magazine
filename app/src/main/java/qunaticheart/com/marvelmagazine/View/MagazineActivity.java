package qunaticheart.com.marvelmagazine.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import qunaticheart.com.marvelmagazine.Base.BaseActivity;
import qunaticheart.com.marvelmagazine.Base.WebViewActivity;
import qunaticheart.com.marvelmagazine.BroadCast.SystemUtil;
import qunaticheart.com.marvelmagazine.Conexao.Constants.ConstantsConnect;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Controller.MainController;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.ActivityUtil;
import qunaticheart.com.marvelmagazine.Utils.Adapter.AdapterMagazineList;
import qunaticheart.com.marvelmagazine.Utils.ViewUtil;

import static qunaticheart.com.marvelmagazine.Utils.GlideUtil.initGlide;
import static qunaticheart.com.marvelmagazine.Utils.LoggerUtils.LogE;
import static qunaticheart.com.marvelmagazine.Utils.LoggerUtils.LogW;

public class MagazineActivity extends BaseActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    private MagazineData magazine;

    private ImageView imgMagazine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        Intent i = getIntent();
        magazine = (MagazineData) i.getSerializableExtra(AdapterMagazineList.getMagazineKey());

        initComponent();

    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);

        imgMagazine = findViewById(R.id.magazineCover);

        initGlide(activity, ViewUtil.getFullImageCover(magazine.getThumbnail().getPath(), magazine.getThumbnail().getExtension()), imgMagazine);

        TextView number = findViewById(R.id.numberMagazineActivity);
        number.setText(ViewUtil.getNumberFormated(magazine.getIssueNumber()));

        ((TextView) llBottomSheet.findViewById(R.id.magazineName)).setText(magazine.getTitle());

        if (magazine.getDates().size() > 0) {
            ((TextView) llBottomSheet.findViewById(R.id.dataPosted)).setText(ViewUtil.timestampToStringFormated(magazine.getDates()));
        } else {
            ((TextView) llBottomSheet.findViewById(R.id.dataPosted)).setVisibility(View.GONE);
        }

        if (magazine.getUrls().size() > 0) {
            ((TextView) llBottomSheet.findViewById(R.id.moreDetailsInWeb)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent webview = new Intent(activity, WebViewActivity.class);
                    webview.putExtra(WebViewActivity.webviewKey, magazine.getUrls().get(0).getUrl());
                    ActivityUtil.callActivity(activity, webview, false);

                }
            });
        } else {
            ((TextView) llBottomSheet.findViewById(R.id.moreDetailsInWeb)).setVisibility(View.GONE);
        }

        ((TextView) llBottomSheet.findViewById(R.id.formatMagazine)).setText(magazine.getFormat());
        ((TextView) llBottomSheet.findViewById(R.id.numberPagesMagazine)).setText(ViewUtil.pageCountFormate(magazine.getPageCount()));
        ((TextView) llBottomSheet.findViewById(R.id.textMagazine)).setText(magazine.getDescription());

        final ImageView imageViewLike = llBottomSheet.findViewById(R.id.btn_like);
        ViewUtil.statusLikeView(imageViewLike, magazine);
        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magazine.setLike(ViewUtil.updateMagazineLikeStatus(activity, magazine));
                ViewUtil.statusLikeView(imageViewLike, magazine);
                new MainController(activity).setLike(magazine);
            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                float i = 0.3f;

                if (slideOffset < 0.7) {
                    i = (1 - (slideOffset));
                }

                imgMagazine.setAlpha(i);
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

}
