package compasso.estagio.grupo.projeto5.Telas.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ConexaoPagSeguro {

    private Perfil perfil;
    private Plano plano;
    private String ddd;
    private String telefoneSemDDD;

    public ConexaoPagSeguro (Perfil perfil,Plano plano){
        this.perfil = perfil;
        this.plano = plano;
        this.ddd = perfil.getTelefone().substring(0, 3);
        this.telefoneSemDDD = perfil.getTelefone().substring(3, 10);
    } 

    public boolean pagar() {
        final String URL = "https://ws.sandbox.pagseguro.uol.com.br/v2/checkout";

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(URL);
            builder.setParameter("email", "{{email}}");
            builder.setParameter("token", "{{token}}");
            HttpPost postRequest = new HttpPost(builder.build());
            postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> formParams = new ArrayList<>();

            formParams.add(new BasicNameValuePair("email", "{{email}}"));
            formParams.add(new BasicNameValuePair("token", "{{token}}"));
            formParams.add(new BasicNameValuePair("currency", "BRL"));

            /* formParams.add(new BasicNameValuePair("itemId1", String.valueOf(plano.getId())));
            formParams.add(new BasicNameValuePair("itemDescription1", plano.getDescricao())); 
            formParams.add(new BasicNameValuePair("itemAmount1", plano.getValor()));      */

            formParams.add(new BasicNameValuePair("itemQuantity1", "1"));
            formParams.add(new BasicNameValuePair("itemWeight1", "0000"));              
            formParams.add(new BasicNameValuePair("reference", "001"));
            formParams.add(new BasicNameValuePair("senderName", this.perfil.getPrimeiroNome()));
            formParams.add(new BasicNameValuePair("senderAreaCode", "75"));
            formParams.add(new BasicNameValuePair("senderPhone", "991578652"));
            formParams.add(new BasicNameValuePair("senderCPF", this.perfil.getCpf()));
            formParams.add(new BasicNameValuePair("senderBornDate", this.perfil.getDataDeNascimento())); 
            formParams.add(new BasicNameValuePair("senderEmail", this.perfil.getEmail()));
            // formParams.add(new BasicNameValuePair("shippingType", "1")); 
            formParams.add(new BasicNameValuePair("shippingAddressStreet", this.perfil.getEndereco().getRua()));  // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressNumber", this.perfil.getEndereco().getNumero()));                // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressComplement", this.perfil.getEndereco().getComplemento()));        // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressDistrict", this.perfil.getEndereco().getBairro())); // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressPostalCode", this.perfil.getEndereco().getCep()));        // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressCity", this.perfil.getEndereco().getCidade()));             // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressState", this.perfil.getEndereco().getSiglaEstado()));                   // Endereco
            formParams.add(new BasicNameValuePair("shippingAddressCountry", "BRA"));
            // formParams.add(new BasicNameValuePair("extraAmount", "-0.01"));
            formParams.add(new BasicNameValuePair("redirectURL", "http://localhost:8080"));
            formParams.add(new BasicNameValuePair("notificationURL",
                    "https://yourserver.com/nas_ecommerce/277be731-3b7c-4dac-8c4e-4c3f4a1fdc46/")); //verificar
            formParams.add(new BasicNameValuePair("maxUses", "1"));
            formParams.add(new BasicNameValuePair("maxAge", "3000"));
            // formParams.add(new BasicNameValuePair("shippingCost", "0.00"));

            postRequest.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
            CloseableHttpResponse response = client.execute(postRequest);
            String result = EntityUtils.toString(response.getEntity());

            System.out.println(result);
            client.close();
            return true;

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
}