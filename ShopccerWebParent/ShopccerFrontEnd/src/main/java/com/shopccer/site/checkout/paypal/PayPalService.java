package com.shopccer.site.checkout.paypal;

import com.shopccer.site.service.AjusteService;
import com.shopccer.site.util.PagoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class PayPalService {

    private static final String GET_ORDER_API = "/v2/checkout/orders/";

    @Autowired
    private AjusteService ajusteService;

    public boolean validateOrder(String orderId) throws PayPalApiException {
        PayPalOrderResponse orderResponse = getOrderDetails(orderId);

        return orderResponse.validate(orderId);
    }

    private PayPalOrderResponse getOrderDetails(String orderId) throws PayPalApiException {
        ResponseEntity<PayPalOrderResponse> response = makeRequest(orderId);

        HttpStatus statusCode = (HttpStatus) response.getStatusCode();

        if (!statusCode.equals(HttpStatus.OK)) {
            throwExceptionForNonOKResponse(statusCode);
        }

        return response.getBody();
    }

    private ResponseEntity<PayPalOrderResponse> makeRequest(String orderId) {
        PagoUtil ajustesPago = ajusteService.getAjustesPago();
        String baseURL = ajustesPago.getURL();
        String requestURL = baseURL + GET_ORDER_API + orderId;
        String clientId = ajustesPago.getClientID();
        String clientSecret = ajustesPago.getClientSecret();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "en_US");
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);
    }

    private void throwExceptionForNonOKResponse(HttpStatus statusCode) throws PayPalApiException {
        String message = null;

        switch (statusCode) {
            case NOT_FOUND:
                message = "No se encontró el ID del pedido";

            case BAD_REQUEST:
                message = "Solicitud incorrecta a la API de PayPal Checkout";

            case INTERNAL_SERVER_ERROR:
                message = "Error del servidor de PayPal";

            default:
                message = "PayPal devolvió un código de estado no válido";
        }

        throw new PayPalApiException(message);
    }
}
