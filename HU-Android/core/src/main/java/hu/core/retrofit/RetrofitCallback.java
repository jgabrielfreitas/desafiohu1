package hu.core.retrofit;

import retrofit.Response;

/**
 * Created by JGabrielFreitas on 16/03/16.
 */
public interface RetrofitCallback<T> {

    // response code 200, 201..
    void onSuccess(T response);

    // 400, 404
    void clientError(T object, Response<T> response);

    // 500..
    void internalServerError();

    void onError(Throwable t);

}
