package sk.beacode.beacodeapp.managers;

import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;

@Rest(rootUrl = Manager.ROOT_URL, converters = {StringHttpMessageConverter.class})
public interface BeaconManager {
}
