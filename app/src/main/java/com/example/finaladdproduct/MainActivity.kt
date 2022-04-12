package com.example.finaladdproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    var listofid= mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listofid.add("Product Id")
        idlist()
        val Fab=findViewById<FloatingActionButton>(R.id.fab);

        Fab.setOnClickListener(){
            //Toast.makeText(this,"Fab", Toast.LENGTH_LONG).show();
            val view= LayoutInflater.from(this).inflate(R.layout.addproductdialouge,null)
            val builder= AlertDialog.Builder(this).setView(view)

            builder.show()
            val id=view.findViewById<Spinner>(R.id.category_id)
            val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listofid)
            id.adapter=adapter

            val submit=view.findViewById<Button>(R.id.submit)

            submit.setOnClickListener(){
                //Toast.makeText(this,"Submit",Toast.LENGTH_LONG).show()
                addproduct(view)
                //val tittle=view
            }



        }

    }

    private fun addproduct(view: View?) {
        val idview= view?.findViewById<Spinner>(R.id.category_id)
        val Tittle= view?.findViewById<EditText>(R.id.Title)
        val Quantity= view?.findViewById<EditText>(R.id.Quantity)
        val Price= view?.findViewById<EditText>(R.id.Price)
        val Desc= view?.findViewById<EditText>(R.id.Desc)

        val id: String = idview?.selectedItem.toString()
        val tittle:String= Tittle?.text.toString()
        val quantity:String= Quantity?.text.toString()
        val price:String= Price?.text.toString()
        val desc:String= Desc?.text.toString()
       // Toast.makeText(this,id+tittle+quantity+price+desc,Toast.LENGTH_LONG).show()
        val url="https://itmagic.app/api/insales/add_product.php"

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response: String? ->
                Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()

            },
            Response.ErrorListener { error: VolleyError? ->
                Toast.makeText(
                    this@MainActivity,
                    "failed",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["shop_name"] = "myshop-bsq158"
                params["email"] = "asatarpk@gmail.com"
                params["password"] = "Sattar_786"
                params["category_id"]=id
                params["title"]=tittle
                params["description"]=desc
                params["sku"]="Random"
                params["quantity"]=quantity
                params["price"]=price
                return params
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add<String>(stringRequest)



    }


    private fun idlist() {

        val url = " https://itmagic.app/api/insales/categories.php"
     val stringRequest: StringRequest = object : StringRequest(
         Method.POST, url,
         Response.Listener { response: String? ->
             try {
                 val respon = JSONObject(response.toString())
                 val categories = respon.getJSONArray("categories")
                 var single: JSONObject
                 for (i in 0 until categories.length() ) {

                     single = categories.getJSONObject(i)
                     listofid.add(single.getString("id"))
                     //Toast.makeText(this,single.getString("id"),Toast.LENGTH_LONG).show()
                     //Toast.makeText(getContext(),single.toString(), Toast.LENGTH_LONG).show();
                 }






                 }catch (e: JSONException){

                 }
         },
         Response.ErrorListener { error: VolleyError? ->
             Toast.makeText(
                 this@MainActivity,
                 "failed",
                 Toast.LENGTH_SHORT
             ).show()
         }) {
         @Throws(AuthFailureError::class)
         override fun getParams(): Map<String, String>? {
             val params: MutableMap<String, String> = HashMap()
             params["shop_name"] = "myshop-bsq158"
             params["email"] = "asatarpk@gmail.com"
             params["password"] = "Sattar_786"
             return params
         }
     }
     val queue = Volley.newRequestQueue(this)
     queue.add<String>(stringRequest)

    }
}