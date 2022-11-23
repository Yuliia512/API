package okhttp3;

import com.google.gson.Gson;
import config.Provider;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("423090@gmail.com").password("Yy12345$").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDto responseDto = Provider.getInstance().getGson().fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void loginNegativeWrongEmail() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("423090gmail.com").password("Yy12345$").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

        ErrorDto errorDto = Provider.getInstance().getGson().fromJson(response.body().string(),ErrorDto.class);
        Object message = errorDto.getMessage();
        Assert.assertEquals(message,"Login or Password incorrect");
        Assert.assertEquals(errorDto.getStatus(),401);
    }

    //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiNDIzMDkwQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjY5NjU0NDM1LCJpYXQiOjE2NjkwNTQ0MzV9.aKpl8noWKwwkqERE1EZGiipcmCw4VScVQ3EPK2fSIl4

}
