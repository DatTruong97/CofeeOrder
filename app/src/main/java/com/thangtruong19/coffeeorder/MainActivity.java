package com.thangtruong19.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffee=2;
    int price =50000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox=findViewById(R.id.check_topping_cream);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();

        CheckBox chocolate=findViewById(R.id.check_topping_chocolate);
        boolean hasChocolate=chocolate.isChecked();

        EditText name=findViewById(R.id.name_edit_text);
        String customerName=name.getText().toString();

        String message=createOrderSummary(price,hasWhippedCream,hasChocolate,customerName);
        displayMessage(message);

        Intent email=new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_SUBJECT,"Coffee order for "+customerName);
        email.putExtra(Intent.EXTRA_TEXT,message);
        if(email.resolveActivity(getPackageManager())!=null){
            startActivity(email);
        }
    }
    public void increment(View view) {
        if(numberOfCoffee==100) {
            Toast.makeText(getApplicationContext(),"You can't have more than 100 cup of coffee",Toast.LENGTH_LONG).show();
            return;
        }
        numberOfCoffee+=1;
        display(numberOfCoffee);

    }
    public void decrement(View view) {
       if(numberOfCoffee==1){
            Toast.makeText(getApplicationContext(),"You can't have less than 1 cup of coffee",Toast.LENGTH_LONG).show();
            return;
        }
        numberOfCoffee-=1;
        display(numberOfCoffee);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView =  findViewById(R.id.quantity_text_view);
        quantityTextView.setText( ""+number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    public int calculatePrice(int price,boolean hasCream,boolean hasChocolate) {
        int totalPrice=price*numberOfCoffee;
        if(hasCream) totalPrice+=20000;
        if(hasChocolate) totalPrice+=10000;
        return totalPrice;
    }
    public String createOrderSummary(int price,boolean hasWhippedCream,boolean hasChocolate,String name){
        String message=getString(R.string.Order_summary_name)+": "+"name\n";
                message+=getString(R.string.hasCream)+hasWhippedCream+"\n";
                message+=getString(R.string.hasChocolate)+hasChocolate+"\n";
                message+=getString(R.string.quantity1)+": "+numberOfCoffee+"\n";
                message+=getString(R.string.Total)+": "+calculatePrice(price,hasChocolate,hasWhippedCream)+"VND\n";
                message+=getString(R.string.thank_you);
        return message;
    }
}
