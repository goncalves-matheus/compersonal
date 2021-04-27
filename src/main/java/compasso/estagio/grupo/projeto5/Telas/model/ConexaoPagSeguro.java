package compasso.estagio.grupo.projeto5.Telas.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ConexaoPagSeguro {

    private String email = "gabriel-jeffersonscs@hotmail.com";
    //private String token = "e8006f5d-b0f1-4822-98b6-945982e14c9ae0dc2cfb4e0aaa9156c492d3c406e0c60806-2dc2-4089-9378-d67ad74ec2c9";
    private String token = "51dea1a1-cb05-45e3-adaf-9d04f073e2e84246b4cf4c86a49e0b3ae8a8b9aeca2b4a44-763a-4dfd-a669-1efd3ba56501";
    //final String URL = "https://ws.sandbox.pagseguro.uol.com.br/v2/checkout";
    //final String URL = "https://pagseguro.uol.com.br/?_ga=2.86277279.667119345.1619439708-1657415295.1618505570/checkout";
    private final String URL = "https://ws.pagseguro.uol.com.br/v2/checkout/";

    private Perfil perfil;
    private Plano plano;
    private String ddd;
    private String telefoneSemDDD;

    public ConexaoPagSeguro(Perfil perfil, Plano plano) {
        this.perfil = perfil;
        this.plano = plano;
        this.ddd = perfil.getTelefone().substring(0, 2);
        this.telefoneSemDDD = perfil.getTelefone().substring(2, 11);
    }
    public ConexaoPagSeguro(){
    }
    //Link de Redirecionamento Checkout PagSeguro:
    //https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code=código de checkout

    public String gerarCodigoDeCompra() {
        String codigoDaCompra = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(URL);
            builder.setParameter("email", email);
            builder.setParameter("token", token);
            HttpPost postRequest = new HttpPost(builder.build());
            postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> formParams = new ArrayList<>();

            //Credenciais:
            formParams.add(new BasicNameValuePair("email", email));
            formParams.add(new BasicNameValuePair("token", token));

            //Dados do item da compra:
            formParams.add(new BasicNameValuePair("currency", "BRL"));
            formParams.add(new BasicNameValuePair("itemId1", String.valueOf(plano.getId())));
            formParams.add(new BasicNameValuePair("itemDescription1", plano.getDescricao()));
            formParams.add(new BasicNameValuePair("itemAmount1", plano.getValor()));
            formParams.add(new BasicNameValuePair("itemQuantity1", "1"));
            formParams.add(new BasicNameValuePair("itemWeight1", "0000"));
            formParams.add(new BasicNameValuePair("reference", "001"));
        
            //Dados do destinatário:
            formParams.add(new BasicNameValuePair("senderName", perfil.getPrimeiroNome()+" "+perfil.getUltimoNome()));
            formParams.add(new BasicNameValuePair("senderAreaCode", ddd));
            formParams.add(new BasicNameValuePair("senderPhone", telefoneSemDDD));
            //formParams.add(new BasicNameValuePair("senderCPF", perfil.getCpf()));
            //formParams.add(new BasicNameValuePair("senderBornDate", perfil.getDataDeNascimento()));
            formParams.add(new BasicNameValuePair("senderEmail", perfil.getEmail()));

            //Configurações da compra:
            formParams.add(new BasicNameValuePair("addressRequired", "false"));
            formParams.add(new BasicNameValuePair("enableRecover", "false"));
            formParams.add(new BasicNameValuePair("redirectURL", "http://localhost:8080/login"));
            formParams.add(new BasicNameValuePair("notificationURL", "http://localhost:8080/pagseguro-notificacao"));
            formParams.add(new BasicNameValuePair("maxUses", "1"));
            formParams.add(new BasicNameValuePair("maxAge", "3600"));

            postRequest.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));

            CloseableHttpResponse response = client.execute(postRequest);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("Resposta: " + result);

            Pattern pattern = Pattern.compile("<code>(.*?)</code>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                codigoDaCompra = matcher.group(1);
                System.out.println("Código: "+codigoDaCompra);    
            }
            
            client.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return codigoDaCompra;
    }
    public String getStatus(String codigo) throws URISyntaxException{
        String result = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder("https://ws.sandbox.pagseguro.uol.com.br/v3/transactions/"+codigo+"");
            builder.setParameter("email", email);
            builder.setParameter("token", token);
            HttpGet getRequest = new HttpGet(builder.build());
            getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            CloseableHttpResponse response = client.execute(getRequest);
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            client.close();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String testar() {
        String email = "gabriel-jeffersonscs@hotmail.com";
        String token = "e8006f5d-b0f1-4822-98b6-945982e14c9ae0dc2cfb4e0aaa9156c492d3c406e0c60806-2dc2-4089-9378-d67ad74ec2c9";
        final String URL = "https://ws.sandbox.pagseguro.uol.com.br/v2/checkout";
        String codigoDaCompra = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(URL);
            builder.setParameter("email", email);
            builder.setParameter("token", token);
            HttpPost postRequest = new HttpPost(builder.build());
            postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> formParams = new ArrayList<>();

            formParams.add(new BasicNameValuePair("email", email));
            formParams.add(new BasicNameValuePair("token", token));
            formParams.add(new BasicNameValuePair("currency", "BRL"));
            formParams.add(new BasicNameValuePair("itemId1", "0001"));
            formParams.add(new BasicNameValuePair("itemDescription1", "Notebook Prata"));
            formParams.add(new BasicNameValuePair("itemAmount1", "000.01"));
            formParams.add(new BasicNameValuePair("itemQuantity1", "2"));
            formParams.add(new BasicNameValuePair("itemWeight1", "1000"));
            formParams.add(new BasicNameValuePair("reference", "REF1234"));
            formParams.add(new BasicNameValuePair("senderName", "Jose Comprador"));
            formParams.add(new BasicNameValuePair("senderAreaCode", "11"));
            formParams.add(new BasicNameValuePair("senderPhone", "56713293"));
            formParams.add(new BasicNameValuePair("senderCPF", "38440987803"));
            formParams.add(new BasicNameValuePair("senderBornDate", "12/03/1990"));
            formParams.add(new BasicNameValuePair("senderEmail", "email@sandbox.pagseguro.com.br"));
            formParams.add(new BasicNameValuePair("shippingType", "1"));
            formParams.add(new BasicNameValuePair("shippingAddressStreet", "Av.Brig.Faria Lima"));
            formParams.add(new BasicNameValuePair("shippingAddressNumber", "1384"));
            formParams.add(new BasicNameValuePair("shippingAddressComplement", "2o andar"));
            formParams.add(new BasicNameValuePair("shippingAddressDistrict", "Jardim Paulistano"));
            formParams.add(new BasicNameValuePair("shippingAddressPostalCode", "01452002"));
            formParams.add(new BasicNameValuePair("shippingAddressCity", "Sao Paulo"));
            formParams.add(new BasicNameValuePair("shippingAddressState", "SP"));
            formParams.add(new BasicNameValuePair("shippingAddressCountry", "BRA"));
            formParams.add(new BasicNameValuePair("extraAmount", "-0.01"));
            formParams.add(new BasicNameValuePair("redirectURL", "localhost:8080"));
            formParams.add(new BasicNameValuePair("notificationURL", "localhost:8080/pagseguro-notificacao"));
            formParams.add(new BasicNameValuePair("maxUses", "1"));
            formParams.add(new BasicNameValuePair("maxAge", "3000"));
            formParams.add(new BasicNameValuePair("shippingCost", "1.00"));

            postRequest.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
            CloseableHttpResponse response = client.execute(postRequest);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("Resposta: " + result);

            Pattern pattern = Pattern.compile("<code>(.*?)</code>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                codigoDaCompra = matcher.group(1);
                System.out.println("Código: "+codigoDaCompra);    
            }
            
            client.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return codigoDaCompra;
    }
}