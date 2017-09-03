package com.shubham16598.volleyexample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    Button button,button2;
    String server_url = "http://192.168.43.191/webapp/init.php";
    String img_url = "http://192.168.43.191/webapp/photo.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        imageView = (ImageView)findViewById(R.id.img);
        //without singleton class method
        /* (2.) Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());

        final RequestQueue requestQueue = new RequestQueue(cache,network);
        requestQueue.start();*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //(1.without caching) final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                textView.setText(response);
                                //requestQueue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error Occured");
                        error.printStackTrace();
                        //requestQueue.stop();
                    }
                });

                //requestQueue.add(stringRequest);

                MySingleton.getInstance(getApplicationContext()).addToRequest(stringRequest);

            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ImageRequest imageRequest = new ImageRequest(img_url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                imageView.setImageBitmap(response);
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                    }
                });

                MySingleton.getInstance(getApplicationContext()).addToRequest(imageRequest);

            }
        });
    }
}
