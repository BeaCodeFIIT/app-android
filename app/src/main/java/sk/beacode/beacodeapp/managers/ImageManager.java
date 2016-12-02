package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import sk.beacode.beacodeapp.models.User;

@Rest(rootUrl = Manager.API_ROOT_URL, converters = {MappingJackson2HttpMessageConverter.class})
@Accept(MediaType.APPLICATION_JSON)
public interface ImageManager extends RestClientHeaders {

    @Post("/logged-in-user/images/new")
    @RequiresHeader(HttpHeaders.CONTENT_TYPE)
    void updateProfileImage(@Body byte[] data);
}
