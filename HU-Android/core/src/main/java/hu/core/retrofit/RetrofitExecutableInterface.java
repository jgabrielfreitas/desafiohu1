package hu.core.retrofit;

import retrofit.Call;

/**
 * Created by JGabrielFreitas on 16/03/16.
 */
public interface RetrofitExecutableInterface<T> {

    void executable(Call<T> api);
}
