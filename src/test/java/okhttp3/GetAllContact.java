package okhttp3;

import config.Provider;
import dto.ContactDto;
import dto.GetAllContactsDto;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContact {
    String token ="Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiNDIzMDkwQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjY5NjU0NDM1LCJpYXQiOjE2NjkwNTQ0MzV9.aKpl8noWKwwkqERE1EZGiipcmCw4VScVQ3EPK2fSIl4";


    @Test
    public void getAllContactsSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .get()
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);


        GetAllContactsDto contactsDto = Provider.getInstance().getGson().fromJson(response.body().string(),GetAllContactsDto.class);

        List <ContactDto>  list = contactsDto.getContacts();
        for (ContactDto contactDto:list) {
            System.out.println(contactDto.getId());
            System.out.println("**************");
        }
    }

    @Test
    public void getAllContactsUnauthorized() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization","oken")
                .get()
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
    }

}
