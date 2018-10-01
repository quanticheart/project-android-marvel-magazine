package qunaticheart.com.marvelmagazine.Conexao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import qunaticheart.com.marvelmagazine.Conexao.Helpers.ApiClient;
import qunaticheart.com.marvelmagazine.Conexao.Model.ListMagazine;
import qunaticheart.com.marvelmagazine.Conexao.Utils.ConnectUtils;
import qunaticheart.com.marvelmagazine.View.Fragments.ListMagazinesFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static qunaticheart.com.marvelmagazine.Conexao.Utils.ConnectUtils.logFunction;

public class Connect {

    //Log Help
    private String connectDescription = "";

    //Contructor
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;
    private static Connection connectionID;
    private static int offsetConnection;

    //Retrofit
    private ApiInterface apiInterface;
    private Call<ListMagazine> call = null;

    /**
     * @param activity call WS
     */
    public Connect(Activity activity) {
        Connect.activity = activity;
        apiInterface = ApiClient.getClient(activity).create(ApiInterface.class);
    }

   public enum Connection {
        initialData,
        getMoreData
    }

    /**
     * @param connectionID Connection Enum
     * @param showLoading Show Loading?
     */
    public void getDataFrom(Connection connectionID, Boolean showLoading, int offset) {
        if (showLoading) {
            ConnectUtils.showSimpleLoadingDialog(activity);
        }
        offsetConnection = offset;
        initWsConection(connectionID);
    }

    private void initWsConection(Connection ID) {
        connectionID = ID;
        switch (ID) {
            case initialData:
            connectDescription = "Initial Magarine Loads";

            String ts = Long.toString(System.currentTimeMillis() / 1000);
            String publicK = "42f63a8d6c0a9760154b9c8e1284a9a1";
            String privateK = "11f10099031b272383d15c345a1bb1df798a2c7d";

            call = apiInterface.getMagazines(ts, publicK, ConnectUtils.getHashForConection(ts + privateK + publicK), offsetConnection);
            break;
        }

        call.enqueue(new Callback<ListMagazine>() {
            @Override
            public void onResponse(@NonNull Call<ListMagazine> call, @NonNull Response<ListMagazine> response) {
                logFunction(response, connectDescription);

                if (response.isSuccessful()) {// Return in 200 at 300 http status
                    setWsResponse(response);
                }
                ConnectUtils.hideSimpleLoadingDialog();

            }

            @Override
            public void onFailure(@NonNull Call<ListMagazine> call, @NonNull Throwable t) {
                logFunction(t, connectDescription);
                ConnectUtils.hideSimpleLoadingDialog();
            }
        });

    }

    private void setWsResponse(Response<ListMagazine> wsResponse) {
        switch (connectionID) {
            case initialData:
            case getMoreData:
                ListMagazinesFragment.initList(wsResponse);
                break;

        }
    }

}
