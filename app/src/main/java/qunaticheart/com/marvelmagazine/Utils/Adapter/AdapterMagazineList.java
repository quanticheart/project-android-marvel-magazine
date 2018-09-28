package qunaticheart.com.marvelmagazine.Utils.Adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import qunaticheart.com.marvelmagazine.Conexao.Constants.ConstantsConnect;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.Utils.ActivityUtil;
import qunaticheart.com.marvelmagazine.Utils.DialogUtil;
import qunaticheart.com.marvelmagazine.Utils.ViewUtil;
import qunaticheart.com.marvelmagazine.View.MagazineActivity;

import static qunaticheart.com.marvelmagazine.Utils.GlideUtil.initGlide;

public class AdapterMagazineList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //First
    private Activity activity;
    private List<MagazineData> dataBase;
    private LayoutInflater layoutInflater;
    private int viewPosition;

    private int END_TYPE = 2;

    private String endMagazine = "###EndViewMagazines###";


    /**
     * init Constructor with Magazines's List
     *
     * @param activity activity pai
     * @param dataBase HMAux's list
     */
    public AdapterMagazineList(Activity activity, List<MagazineData> dataBase) {
        this.activity = activity;
        this.dataBase = dataBase;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    /**
     * @param list HMAux's list
     */
    public void addList(List<MagazineData> list) {
        dataBase.addAll(list);
        notifyItemInserted(dataBase.size() - 1);
    }

    /**
     * @param magazine magazine data
     */
    public void addItem(MagazineData magazine) {
        dataBase.add(magazine);
        notifyItemInserted(dataBase.size() - 1);
    }

    /**
     * remove all items at RecyclerView
     */
    public void clear() {
        final int size = dataBase.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                dataBase.remove(i);
                notifyItemRemoved(i);
            }
        }
    }


    public void addEndView() {
        MagazineData end = new MagazineData();
        end.setTitle(endMagazine);
        dataBase.add(end);
        notifyItemInserted(dataBase.size() + 1);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ObjectAnimator animatorUP = ObjectAnimator.ofFloat(view, "translationZ", 20);
                    animatorUP.setDuration(200);
                    animatorUP.setInterpolator(new DecelerateInterpolator());
                    animatorUP.start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ObjectAnimator animatorDOWN = ObjectAnimator.ofFloat(view, "translationZ", 0);
                    animatorDOWN.setDuration(200);
                    animatorDOWN.setInterpolator(new AccelerateInterpolator());
                    animatorDOWN.start();
                    break;
            }
            return false;
        }
    };


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == END_TYPE) {
            View view = layoutInflater.inflate(R.layout.cell_end_magazines, parent, false);
            return new EndViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.cell_magazines, parent, false);
            return new RecyclerViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MagazineData magazine = dataBase.get(position);
        if (holder instanceof EndViewHolder) {
            EndViewHolder viewHolder = (EndViewHolder) holder;
            viewHolder.view.setOnTouchListener(touchListener);
            viewHolder.view.setOnClickListener(END_TYPE_ViewClick);
            viewHolder.view.setTag(position);
            initActions_EndView(viewHolder, magazine);
        } else {
            RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
            viewHolder.view.setOnTouchListener(touchListener);
            viewHolder.view.setTag(position);
            initActions_NormalView(viewHolder, magazine);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param position position adapter
     * @return ViewType for load Resorce
     */
    @Override
    public int getItemViewType(int position) {
        MagazineData magazine = dataBase.get(position);
        String typeView = magazine.getTitle();

        if (typeView.equals(endMagazine)) {
            return END_TYPE;
        } else {
            return 0;
        }
    }

    /**
     * @return HMAux's List size
     */
    @Override
    public int getItemCount() {
        return dataBase.size();
    }


    /**
     * ViewHolder END_VIEW
     */
    private class EndViewHolder extends RecyclerView.ViewHolder {
        private View view;

        EndViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    private void initActions_EndView(EndViewHolder viewHolder, MagazineData magazine) {

    }

    private View.OnClickListener END_TYPE_ViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };


    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * ViewHolder NORMAL_TYPE
     */
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private View view;

        // view
        private ImageView imageView;
        private TextView numberMagazine;
        private TextView custMagazine;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            //findViewById's
            imageView = itemView.findViewById(R.id.imgMagazine);
            numberMagazine = itemView.findViewById(R.id.numberMagazine);
            custMagazine = itemView.findViewById(R.id.custMagazine);

            view.setOnClickListener(NORMAL_TYPE_ViewClick);
            view.setOnLongClickListener(NORMAL_TYPE_LongClick);
        }
    }

    private void initActions_NormalView(final RecyclerViewHolder viewHolder, MagazineData magazine) {

        initGlide(activity, ConstantsConnect.getFantasticImageCover(magazine.getThumbnail().getPath(), magazine.getThumbnail().getExtension()), viewHolder.imageView);

        viewHolder.numberMagazine.setText(ConstantsConnect.getNumberFormated(magazine.getIssueNumber()));
        viewHolder.custMagazine.setText(ViewUtil.moneyFormate(magazine.getPrices().get(0).getPrice()));

    }

    private View.OnClickListener NORMAL_TYPE_ViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            viewPosition = (int) view.getTag();
            MagazineData magazineData = dataBase.get(viewPosition);

            Intent magazine = new Intent(activity, MagazineActivity.class);
            magazine.putExtra(getMagazineKey(), magazineData);

            ActivityUtil.callActivity(activity , magazine , false);

        }
    };

    private View.OnLongClickListener NORMAL_TYPE_LongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            viewPosition = (int) v.getTag();
            MagazineData magazineData = dataBase.get(viewPosition);
            DialogUtil.showDetails(activity, magazineData);
            return true;
        }
    };

    public static String getMagazineKey() {
        return "magazinedata";
    }


}
