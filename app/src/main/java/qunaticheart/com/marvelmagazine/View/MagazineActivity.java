package qunaticheart.com.marvelmagazine.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import qunaticheart.com.marvelmagazine.Base.BaseActivity;
import qunaticheart.com.marvelmagazine.Base.WebViewActivity;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Controller.MainController;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.ActivityUtil;
import qunaticheart.com.marvelmagazine.Utils.Adapter.AdapterMagazineList;
import qunaticheart.com.marvelmagazine.Utils.LoggerUtils;
import qunaticheart.com.marvelmagazine.Utils.ViewUtil;

import static qunaticheart.com.marvelmagazine.Utils.GlideUtil.initGlide;

public class MagazineActivity extends BaseActivity {

    //init
    private BottomSheetBehavior bottomSheetBehavior;

    // Magazine Data
    private MagazineData magazine;

    //Magazine Views
    private ImageView MagazineCover;
    private TextView MagazineNumber;
    private LinearLayout MagazineDetailsBSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        magazine = verifyIntentMagazineData();

        initVars();
        initActions();

    }


    private void initVars() {
        MagazineCover = findViewById(R.id.magazineCover);
        MagazineNumber = findViewById(R.id.numberMagazineActivity);
        MagazineDetailsBSheet = findViewById(R.id.bottom_sheet);
    }

    private void initActions() {

        //Cover
        initGlide(activity, ViewUtil.getFullImageCover(magazine.getThumbnail().getPath(),
                magazine.getThumbnail().getExtension()), MagazineCover);

        //Number
        MagazineNumber.setText(ViewUtil.getNumberFormated(magazine.getIssueNumber()));

        //Details
        BSheeatDetails();
    }

    private void BSheeatDetails() {

        //Title
        ((TextView) MagazineDetailsBSheet.findViewById(R.id.magazineName)).setText(magazine.getTitle());

        //Data Posted
        if (magazine.getDates().size() > 0) {
            ((TextView) MagazineDetailsBSheet.findViewById(R.id.dataPosted)).setText(ViewUtil.timestampToStringFormated(magazine.getDates()));
        } else {
            MagazineDetailsBSheet.findViewById(R.id.dataPosted).setVisibility(View.GONE);
        }

        //Web Details
        if (magazine.getUrls().size() > 0) {
            MagazineDetailsBSheet.findViewById(R.id.moreDetailsInWeb).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent webview = new Intent(activity, WebViewActivity.class);
                    webview.putExtra(WebViewActivity.webviewKey, magazine.getUrls().get(0).getUrl());
                    ActivityUtil.callActivity(activity, webview, false);

                }
            });
        } else {
            MagazineDetailsBSheet.findViewById(R.id.moreDetailsInWeb).setVisibility(View.GONE);
        }

        //Format Magazine
        ((TextView) MagazineDetailsBSheet.findViewById(R.id.formatMagazine)).setText(magazine.getFormat());

        //Number Pages
        ((TextView) MagazineDetailsBSheet.findViewById(R.id.numberPagesMagazine)).setText(ViewUtil.pageCountFormate(magazine.getPageCount()));

        //Description
        ((TextView) MagazineDetailsBSheet.findViewById(R.id.textMagazine)).setText(magazine.getDescription());

        //Like Button
        final ImageView imageViewLike = MagazineDetailsBSheet.findViewById(R.id.btn_like);
        ViewUtil.statusLikeView(imageViewLike, magazine);

        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magazine.setLike(ViewUtil.updateMagazineLikeStatus(activity, magazine));
                ViewUtil.statusLikeView(imageViewLike, magazine);
                new MainController(activity).setLike(magazine);
            }
        });

        //BottomSheet Constructor
        bottomSheetBehavior = BottomSheetBehavior.from(MagazineDetailsBSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                MagazineCover.setAlpha(getAlphaStatus(slideOffset));
            }
        });


        //Magazine Number Click
        MagazineNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    //==============================================================================================
    //
    // Utils
    //
    //==============================================================================================

    private float getAlphaStatus(float slideOffset) {
        float i = 0.3f;
        if (slideOffset < 0.7) {
            i = 1 - slideOffset;
        }
        return i;
    }

    private MagazineData verifyIntentMagazineData() {
        Intent i = getIntent();
        if (!Objects.requireNonNull(i.getExtras()).isEmpty()) {
            return (MagazineData) i.getSerializableExtra(AdapterMagazineList.getMagazineKey());
        } else {
            finishActivityOnError();
            return new MagazineData();
        }
    }

    private void finishActivityOnError() {
        LoggerUtils.callToast(activity, getString(R.string.msg_error_magazine_data));
        finish();
    }
}
