
package si.fri.rso.upravljanjedogodkov;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.GenericType;

import static javax.swing.UIManager.get;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("upravljanjeDogodkov")
@RequestScoped
public class UpravljanjeProfilovResources {

    private Logger log = LogManager.getLogger(UpravljanjeProfilovResources.class.getName());
    private Client httpClient;

    @Inject
    @DiscoverService("katalogDogodkov")
    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    @GET
    public Response getAllDogodeks() {
        return Response.ok("test").build();
        /*try {
            WebTarget wt = httpClient.target(baseUrl + "/v1/katalogProfilov/");
            Invocation.Builder b = wt.request();
            List<Dogodek> resp = b.get(new GenericType<Dogodek>(){});
            Dogodek response = b.get(new GenericType<Dogodek>() {
            });
            System.out.println("response je: " + response.toString());

            return Response.ok(response).build();
        }
        catch (Exception e) {
            //log.error(e);
            throw e;
        }*/
    }

    @GET
    @Path("{dogodekId}")
    public Dogodek getDogodek(@PathParam("dogodekId") String profilId) {
        log.debug(baseUrl + "/v1/katalogDogodkov?" + profilId);

       try {
           WebTarget wt = httpClient.target(baseUrl + "/v1/katalogDogodkov/" + profilId);
           Invocation.Builder b = wt.request();
           Dogodek response = b.get(new GenericType<Dogodek>() {
           });
           System.out.println("response je: " + response.toString());

            return response;
        } catch (Exception e) {
            //log.error(e);
            throw e;
        }}/*
        try {
            String response = sendGet(profilId);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("exception").build();
        }*/


    private String sendGet(String profilId) throws Exception {



        URL obj = new URL(baseUrl+ "/v1/katalogDogodkov?" + profilId);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + baseUrl);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return response.toString();

    }

    /*@GET
    public Response getAllProfils() {
        List<Dogodek> profils = Database.getDogodeks();
        return Response.ok(profils).build();
    }

    @GET
    @Path("{profilId}")
    public Response getProfil(@PathParam("profilId") String profilId) {
        Dogodek profil = Database.getProfil(profilId);
        return profil != null
                ? Response.ok(profil).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }*/

    @POST
    public Response addNewDogodek(Dogodek dogodek) {
        Database.addDogodek(dogodek);
        return Response.ok(dogodek).build();
    }

    @DELETE
    @Path("{dogodekId}")
    public Response deleteProfil(@PathParam("dogodekId") String profilId) {
        Database.deleteDogodek(profilId);
        return Response.ok(Response.Status.OK).build();
    }
}
