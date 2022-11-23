package okhttp3;

import config.Provider;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTests {

    @Test
    public void registrationSuccess() throws IOException {
        Random random = new Random();
        int i = random.nextInt(100) + 100;

        AuthRequestDto auth = AuthRequestDto.builder().username("city"+i+"@gmail.com").password("Yy12345$").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDto responseDto = Provider.getInstance().getGson().fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void registrationWrongEmail() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder().username("citygmail.com").password("Yy12345$").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

    }

    @Test
    public void registrationWrongPassword() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder().username("city@gmail.com").password("Yy12345").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

    }

    @Test
    public void registrationDuplicateUser() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder().username("423090@gmail.com").password("Yy12345$").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),409);

    }
}
