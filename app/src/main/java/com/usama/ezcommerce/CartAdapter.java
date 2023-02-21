package com.usama.ezcommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public ArrayList<CartData> cartdata;
    private Context context;


    public CartAdapter(Context context, ArrayList<CartData> cartdata) {
        this.context = context;
        this.cartdata = cartdata;
    }


    //// generating layout for every single item and initializing its views ///////
   @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
       return viewHolder;
    }


    //// putting values into that views (binding data with its view) //////
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CartData mycartData = cartdata.get(position);
        holder.getImageView().setImageResource(mycartData.getImgId());
        holder.getProductNameView().setText(mycartData.getProductname());
        holder.getPriceView().setText(mycartData.getPrice());
        holder.getQuantityView().setText(String.valueOf((mycartData.getQuantity())));

    }

    @Override
    public int getItemCount() {
        return cartdata.size();
    }


    ////// class which hold all views of single cart item ////////
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView productNameView, priceView, quantityView;
        Button addButtonView, subButtonView, deleteView;
        public CardView cardView;
        CartDB doa;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView3);
            this.productNameView = (TextView) itemView.findViewById(R.id.textView5);
            this.priceView = (TextView) itemView.findViewById(R.id.textView6);
            this.quantityView = (TextView) itemView.findViewById(R.id.textView7);
            this.addButtonView = (Button) itemView.findViewById(R.id.button3);
            this.subButtonView = (Button) itemView.findViewById(R.id.button4);
            this.deleteView = (Button) itemView.findViewById(R.id.button2);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            doa = new CartDB(context.getApplicationContext());

            //// defining action-listeners on buttons ////
            addButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //// 1): Update quantity on screen  ////
                    String srequantity = quantityView.getText().toString();
                    int quantity = Integer.parseInt(srequantity);
                    quantity++;
                    srequantity = String.valueOf(quantity);
                    quantityView.setText(srequantity);


                    //// 2): Update quantity in database  ////
                    int index = getAdapterPosition();                //// item index for which '+' button is clicked ////
                    CartData item = cartdata.get(index);
                    int imgID = item.getImgId();
                    String itemName = item.getProductname();
                    String price = item.getPrice();
                    CartData newObj = new CartData(imgID, itemName, price, quantity);
                    doa.IncreaseQuantity(newObj);

//                  //// 3): Update total price ////
                    int total_amount = doa.getPrice();
                    Page3.textView.setText(String.valueOf(total_amount));
                }
            });


            ///// clicking on '-' button to decrease quantity /////
            subButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String srequantity = quantityView.getText().toString();
                    int quantity = Integer.parseInt(srequantity);

                    if(quantity > 1) {

                        //// 1): Update quantity on screen  ////
                        quantity--;
                        srequantity = String.valueOf(quantity);
                        quantityView.setText(srequantity);


                        //// 2): Update quantity in database  ////
                        int index = getAdapterPosition();           //// item index for which '-' button is clicked ////
                        CartData item = cartdata.get(index);
                        int imgID = item.getImgId();
                        String itemName = item.getProductname();
                        String price = item.getPrice();
                        CartData newObj = new CartData(imgID, itemName, price, quantity);
                        doa.DecreaseQuantity(newObj);

                        //// 3): Update total price ////
                        int total_amount = doa.getPrice();
                        Page3.textView.setText(String.valueOf(total_amount));
                    }

                }
            });


            //// clicking on 'Delete' button to delete item ////
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int index = getAdapterPosition();           //// item index for which button is clicked ////
                    CartData item = cartdata.get(index);
                    String itemName = item.getProductname();
                    doa.deleteCartItem(itemName);              /// remove item from database ///

                    cartdata.remove(index);                   /// remove that item from arrayList ///
                    notifyItemRemoved(index);                 /// also update recyclerView ////
                    notifyItemRangeChanged(index, doa.countCartItems());


                    //// 3): Update total price ////
                    int total_amount = doa.getPrice();
                    Page3.textView.setText(String.valueOf(total_amount));
                }
            });
        }


        public ImageView getImageView() {
            return imageView;
        }

        public TextView getProductNameView() {
            return productNameView;
        }

        public TextView getPriceView() {
            return priceView;
        }

        public TextView getQuantityView() {
            return quantityView;
        }
    }
}
