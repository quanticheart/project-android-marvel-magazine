package qunaticheart.com.marvelmagazine.Utils.Searsh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import qunaticheart.com.marvelmagazine.Conexao.Model.Searsh;
import qunaticheart.com.marvelmagazine.R;

public class AdapterSearsh extends BaseAdapter {

    private Context context;
    private int resource;
    private List<Searsh> dados;

    private LayoutInflater mInflater;

    public interface IAdapterPosts {
        void onStatusChanged(String id, boolean status);
    }

    private IAdapterPosts delegate;

    public void setOnStatusChangedListener(IAdapterPosts delegate) {
        this.delegate = delegate;
    }

    public AdapterSearsh(Context context, int resource, List<Searsh> dados) {
        this.context = context;
        this.resource = resource;
        this.dados = dados;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dados.size();
    }

    @Override
    public Object getItem(int position) {
        return dados.get(position);
    }

    @Override
    public long getItemId(int position) {
        Searsh hmAux = dados.get(position);

        return Long.parseLong(hmAux.getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
        }

        final Searsh hmAux = dados.get(position);

        TextView tv_data = (TextView) convertView.findViewById(R.id.tv_nome_celula_searchview);

        tv_data.setText(hmAux.getTextSearsh());

        return convertView;
    }

}
