package qunaticheart.com.marvelmagazine.Conexao;

import qunaticheart.com.marvelmagazine.Conexao.Model.ListMagazine;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    String test = "comics?ts=2018-09-25&format=magazine&noVariants=false&orderBy=focDate&limit=50&offset=50&apikey=42f63a8d6c0a9760154b9c8e1284a9a1&hash=c303da39005c0dce1a07e16c6d77d6d9";

    @GET("comics?format=magazine&noVariants=false&orderBy=focDate&limit=20")
    Call<ListMagazine> getMagazines(@Query("ts") String ts, @Query("apikey") String key, @Query("hash") String hash, @Query("offset") int offset);

}