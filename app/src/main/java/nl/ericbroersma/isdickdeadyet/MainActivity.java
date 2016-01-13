package nl.ericbroersma.isdickdeadyet;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RestCall().execute();
    }

    private class RestCall extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            Client client = ClientBuilder.newClient();
            Invocation.Builder request = client.target("http://www.isdickdeadyet.xyz/api").request("application/json");
            Response response = request.get();

            return response.readEntity(String.class);
        }
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                TextView text = (TextView) findViewById(R.id.text);
                text.setText(json.getBoolean("dick_is_dead") ? "Dick is dead!" : "Dick is still alive...");
            } catch (JSONException e) {
                new  AlertDialog.Builder(getApplicationContext()).setMessage("Uhoh").create();
            }

        }
    }
}
