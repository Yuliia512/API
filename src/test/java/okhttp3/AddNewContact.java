package okhttp3;

import config.Provider;
import dto.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddNewContact {

    String token ="Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiNDIzMDkwQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjY5NjU0NDM1LCJpYXQiOjE2NjkwNTQ0MzV9.aKpl8noWKwwkqERE1EZGiipcmCw4VScVQ3EPK2fSIl4";

    @Test
    public void addNewContactSuccess() throws IOException {

        Random random = new Random();
        int i = random.nextInt(100) + 100;

        ContactDto contact = ContactDto.builder().name("Sara" + i).lastName("Moon")
                .email("fox" + i + "@gmail.com").phone("12345678" + i).address("NY")
                .description("Best friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(contact), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .header("Authorization", token)
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        MessageDto contactMessage = Provider.getInstance().getGson().fromJson(response.body().string(), MessageDto.class);
        System.out.println(contactMessage.getMessage());

    }

    @Test
    public void addNewContactWithoutName() throws IOException {


        ContactDto contact = ContactDto.builder().name("  ").lastName("Moon")
                .email("fox@gmail.com").phone("1234567889").address("NY")
                .description("Best friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(contact), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .header("Authorization", token)
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDto error = Provider.getInstance().getGson().fromJson(response.body().string(), ErrorDto.class);
        System.out.println(error.getMessage());

    }

    @Test
    public void addContactUnauthorized() throws IOException {


        ContactDto contact = ContactDto.builder().name("Sara").lastName("Moon")
                .email("fox@gmail.com").phone("1234567889").address("NY")
                .description("Best friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(contact), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .header("Authorization", "oken")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

        ErrorDto message = Provider.getInstance().getGson().fromJson(response.body().string(), ErrorDto.class);
        System.out.println(message.getMessage());

    }

    @Test
    public void addDuplicateContactFields() throws IOException {


        ContactDto contact = ContactDto.builder().name("Lee").lastName("Lee")
                .email("nk@mail.ru").phone("2222222222").address("NY")
                .description("Best friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(contact), Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .header("Authorization", token)
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 409);

        ErrorDto error = Provider.getInstance().getGson().fromJson(response.body().string(), ErrorDto.class);
        System.out.println(error.getMessage());

    }
}
