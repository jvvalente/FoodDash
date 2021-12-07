package com.example.fooddash;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;

import com.example.fooddash.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.appcompat.app.AppCompatActivity;

public class AddItem extends AppCompatActivity {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference food = database.getReference("Food");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.addfood);

            //These onclicklisteners make sure edittext is not empty
            final EditText deliveryCharges = (EditText) findViewById(R.id.deliveryChargeInput);
            deliveryCharges.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    deliveryCharges.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final EditText deliveryTime = (EditText) findViewById(R.id.deliveryTimeInput);
            deliveryTime.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    deliveryTime.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final EditText imageUrl = (EditText) findViewById(R.id.imageUrlEdit);
            imageUrl.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    imageUrl.setText("", TextView.BufferType.EDITABLE);

                }
            });

            final EditText foodName = (EditText) findViewById(R.id.foodNameInput);
            foodName.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    foodName.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final EditText foodNote = (EditText) findViewById(R.id.foodNoteInput);
            foodNote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    foodNote.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final EditText foodPrice = (EditText) findViewById(R.id.foodPriceInput);
            foodPrice.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    foodPrice.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final EditText foodRating = (EditText) findViewById(R.id.foodRatingInput);
            foodRating.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    foodRating.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final EditText foodType = (EditText) findViewById(R.id.foodTypeInput);
            foodType.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    foodType.setText("", TextView.BufferType.EDITABLE);

                }
            });
            final Button submit = (Button) findViewById(R.id.submitbutton);
            submit.setOnClickListener(new View.OnClickListener() {
                //This handles any errors caused by empty edittexts
                public void onClick(View v) {
                    // your handler code here
                    if(!confirmInput(deliveryCharges,deliveryTime,imageUrl,foodName,foodNote,foodPrice,foodRating,foodType))
                        Toast.makeText(getBaseContext(), "There are Errors or Empty Fields", Toast.LENGTH_LONG).show();
                    else
                    {
                        //Submits to database
                        Toast.makeText(getBaseContext(), "Submitting to the DataBase", Toast.LENGTH_LONG).show();
                        int deliverytime = Integer.parseInt(deliveryTime.getText().toString());
                        double deliverycharge = Double.parseDouble(deliveryCharges.getText().toString());
                        double foodprice = Double.parseDouble(foodPrice.getText().toString());
                        double foodrating = Double.parseDouble(foodRating.getText().toString());

                        Food f = new Food(foodName.getText().toString(), foodprice, foodrating,foodType.getText().toString(), deliverytime, deliverycharge, foodNote.getText().toString(), imageUrl.getText().toString());

                        food.child(f.getFoodName()).setValue(f);

                    }

                }
            });

            //Resets all fields
            final Button reset = (Button) findViewById(R.id.resetbutton);
            reset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    deliveryCharges.setText("", TextView.BufferType.EDITABLE);
                    deliveryTime.setText("", TextView.BufferType.EDITABLE);
                    imageUrl.setText("", TextView.BufferType.EDITABLE);
                    foodName.setText("", TextView.BufferType.EDITABLE);
                    foodNote.setText("", TextView.BufferType.EDITABLE);
                    foodPrice.setText("", TextView.BufferType.EDITABLE);
                    foodRating.setText("", TextView.BufferType.EDITABLE);
                    foodType.setText("", TextView.BufferType.EDITABLE);

                    deliveryCharges.setError(null);
                    deliveryTime.setError(null);
                    imageUrl.setError(null);
                    foodName.setError(null);
                    foodNote.setError(null);
                    foodPrice.setError(null);
                    foodRating.setError(null);
                    foodType.setError(null);

                }
            });


        }

        //Makes sure input is not empty or correct input
        public boolean confirmInput(EditText deliveryCharges,EditText deliveryTime,EditText imageUrl,EditText foodName,EditText foodNote,EditText foodPrice,EditText foodRating,EditText foodType)
        {
            boolean charges,time,url,name,note,price,rating,type;
            charges = time = url = name = note  = price = rating = type = true;

            if(deliveryCharges.getText().toString().equals(""))
            {
                charges = false;
                deliveryCharges.setError("Input is Empty");
            }
                try {
                    Double.parseDouble(deliveryCharges.getText().toString());
                    }
                catch (NumberFormatException e)
                {
                    charges = false;
                    deliveryCharges.setError("Input isn't a number");
                }


            if(deliveryTime.getText().toString().equals(""))
            {
                time = false;
                deliveryTime.setError("Input is Empty");


            }
            if(imageUrl.getText().toString().equals(""))
            {
                url = false;
                imageUrl.setError("Input is Empty");
            }
            if(!imageUrl.getText().toString().contains("https:"))
            {
                url = false;
                imageUrl.setError("Input is not in correct Url format");
            }
            if(foodName.getText().toString().equals(""))
            {
                name = false;
                foodName.setError("Input is Empty");


            }
            if(foodNote.getText().toString().equals(""))
            {
                note = false;
                foodNote.setError("Input is Empty");

            }
            if(foodPrice.getText().toString().equals(""))
            {
                price = false;
                foodPrice.setError("Input is Empty");
            }
            try {
                Double.parseDouble(foodPrice.getText().toString());
            }
            catch (NumberFormatException e)
            {
                price = false;
                foodPrice.setError("Input isn't a number");
            }
            if(foodRating.getText().toString().equals(""))
            {
                rating = false;
                foodRating.setError("Input is Empty");
            }
            try {
                Double.parseDouble(foodRating.getText().toString());
            }
            catch (NumberFormatException e)
            {
                rating = false;
                foodRating.setError("Input isn't a number");
            }
            if(foodType.getText().toString().equals(""))
            {
                type = false;
                foodType.setError("Input is Empty");
            }
            return charges && time && url && name && note && price && rating && type;
        }

    }

