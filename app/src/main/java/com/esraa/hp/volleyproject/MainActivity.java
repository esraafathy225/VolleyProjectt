package com.esraa.hp.volleyproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
    RecyclerView recyclerView;
    String link="http://pastebin.com/raw/wgkJgazE";
    ArrayList<UserDetails> users;
    int likes;
    String name;
    String profileImage;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= findViewById(R.id.btn);
        recyclerView=findViewById(R.id.recycler);
        users=new ArrayList<>();

          String comment=" فولى هى واحدة من ال libraries المهمة جدا في التعامل مع ال web service لانها " +
                  "اسرع بكتير من الطريقة التقليدية اللى هى HttpUrlConnection و اسهل في التعامل اكتر بكتير.." +
                  "من مميزاتها مثلا انى مش مضطر اتعامل خالص مع threads ولا asynctask هى بت handle الموضوع دة بنفسها " +
                  "دة غير ان فيها caching بمعنى انى لو عملت load للداتا مرة هى بت save ال data دى في الميمورى " +
                  "لفترة مؤقتة كدة بحيث انك لو قفلت الابلكيشن او حتى قفلت النت خالص مدام محمل الداتا " +
                  "دى قبل كدة فهتلاقيها موجودة عادى اول ما تفتح الابلكيشن" +
                  "كمان فولى عندها image loader ممكن احمل بيه الصور من ال url بتاعها زى picasso w glide " +
                  "بس الكود بتاعه اصعب منهم شوية عشان كدة مش بنستخدمه " +
                  "من عيوب فولى انى مضطر اعمل parsing للداتا بنفسي عكس retrofit اللى بتعمل parsing لوحدها " +
                  "و دى ميزة كبيرة ل retrofit عن volley و بتخليها اسرع منها " +
                  "الخطوات عشان نبدأ نشتغل بيها اول حاجة هنزود ال library في ال gradle و انا هنا مستخدمة " +
                  "recyclerview w picasso فهزودهم هما كمان " +
                  " 1.في كلاس اسمه requestQueue هبدأ استخدمه عشان اعمل الريكوست انى اجيب الداتا بتاعتى " +
                  "هو queue يعني صف ..لانى ممكن اكون عاوز ابعت كذا ريكوست بكذا api و كلهم هديهم للكلاس دة ينفذهملى" +
                  "كمان هو المسؤول انه ي handle موضوع ال threads و هو اللى بيبعت الريكوست و بيرجعلى بال response على طول" +
                  "2.حسب ال json بتاعى هبدأ احدد ال request يعني مثلا عندى حاجة اسمها JsonObjectRequest and JsonArrayRequest " +
                  "يعني لو ال json بادئ ب array ممكن استخدم ال JSONArrayRequest على طول واعمل parsing بعدها بال for loop" +
                  " بس هنا انا جايبة ال response ك string الاول " +
                  "و هعمله parsing بعد كدة " +
                  "3.هعمل اوبجكت من StringRequest و دة بياخد اللينك كأول parameter " +
                  "تانى parameter هو Response.Listener و دة جواه method جيبالى ال response على طول " +
                  "انا حددت من الاول انه StringRequest يبقي هيرجعلى ك String جوه ال method دى " +
                  "تالت parameter هو ErrorListener و دة في حالة ان حصل مشكلة فممكن احط فيه كود يتنفذ لو الكونكشن محصلتش" +
                  "4. ال String اللى جايلى هعمله parsing على طول زى ما عملنا قبل كدة بالظبط   ";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                StringRequest request=new StringRequest(link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++) {
                                JSONObject object = array.getJSONObject(i);
                                likes = object.getInt("likes");
                                JSONObject object1=object.getJSONObject("user");
                                name = object1.getString("name");
                                JSONObject object2=object1.getJSONObject("profile_image");
                                profileImage=object2.getString("medium");
                                users.add(new UserDetails(name,likes,profileImage));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter=new UserAdapter(MainActivity.this,users);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley error",error.getLocalizedMessage());
                    }
                });
                requestQueue.add(request);
            }
        });
    }
}
