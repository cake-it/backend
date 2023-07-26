package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.GetCakeStoreListRequestDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListResponseDto;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface CakeStoreService {

    public List<GetCakeStoreListResponseDto> getCakeStoreListByLatitudeAndLongitude(GetCakeStoreListRequestDto getCakeStoreListRequestDto) throws IOException, JSONException;
}
